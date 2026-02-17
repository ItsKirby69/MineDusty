import arc.files.*
import arc.util.*
import arc.util.serialization.*
import ent.*
import java.io.*
import java.net.*

buildscript{
    val arcVersion: String by project
    val useJitpack = property("mindustryBE").toString().toBooleanStrict()

    dependencies{
        classpath("com.github.Anuken.Arc:arc-core:$arcVersion")
    }

    repositories{
        if(!useJitpack) maven("https://maven.xpdustry.com/mindustry")
        maven("https://jitpack.io")
    }
}

plugins{
    java
    id("com.github.GglLfr.EntityAnno") apply false
}

val arcVersion: String by project
val mindustryVersion: String by project
val mindustryBEVersion: String by project
val entVersion: String by project

val modName: String by project
val modArtifact: String by project
val modFetch: String by project
val modGenSrc: String by project
val modGen: String by project

val androidSdkVersion: String by project
val androidBuildVersion: String by project
val androidMinVersion: String by project

val useJitpack = property("mindustryBE").toString().toBooleanStrict()

fun arc(module: String): String{
    return "com.github.Anuken.Arc$module:$arcVersion"
}

fun mindustry(module: String): String{
    return "com.github.Anuken.Mindustry$module:$mindustryVersion"
}

fun entity(module: String): String{
    return "com.github.GglLfr.EntityAnno$module:$entVersion"
}

allprojects{
    apply(plugin = "java")
    sourceSets["main"].java.setSrcDirs(listOf(layout.projectDirectory.dir("src")))

    configurations.configureEach{
        // Resolve the correct Mindustry dependency, and force Arc version.
        resolutionStrategy.eachDependency{
            if(useJitpack && requested.group == "com.github.Anuken.Mindustry"){
                useTarget("com.github.Anuken.MindustryJitpack:${requested.module.name}:$mindustryBEVersion")
            }else if(requested.group == "com.github.Anuken.Arc"){
                useVersion(arcVersion)
            }
        }
    }

    dependencies{
        // Downgrade Java 9+ syntax into being available in Java 8.
        annotationProcessor(entity(":downgrader"))
    }

    repositories{
        // Necessary Maven repositories to pull dependencies from.
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/repositories/releases/")
        maven("https://raw.githubusercontent.com/GglLfr/EntityAnnoMaven/main")

        // Use xpdustry's non-buggy repository for release Mindustry and Arc builds.
        if(!useJitpack) maven("https://maven.xpdustry.com/mindustry")
        maven("https://jitpack.io")
    }

    tasks.withType<JavaCompile>().configureEach{
        // Use Java 17+ syntax, but target Java 8 bytecode version.
        sourceCompatibility = "17"
        options.apply{
            release = 8
            compilerArgs.add("-Xlint:-options")

            isIncremental = true
            encoding = "UTF-8"
        }
    }
}

project(":"){
    apply(plugin = "com.github.GglLfr.EntityAnno")
    configure<EntityAnnoExtension>{
        modName = project.properties["modName"].toString()
        mindustryVersion = project.properties[if(useJitpack) "mindustryBEVersion" else "mindustryVersion"].toString()
        isJitpack = useJitpack
        revisionDir = layout.projectDirectory.dir("revisions").asFile
        fetchPackage = modFetch
        genSrcPackage = modGenSrc
        genPackage = modGen
    }

    dependencies{
        // Use the entity generation annotation processor.
        compileOnly(entity(":entity"))
        add("kapt", entity(":entity"))

        compileOnly(mindustry(":core"))
        // compileOnly(mindustry(":core:sources"))
        compileOnly(arc(":arc-core"))
        // compileOnly(arc(":arc-core:sources"))
    }

    val jar = tasks.named<Jar>("jar"){
        archiveFileName = "${modArtifact}Desktop.jar"

        val meta = layout.projectDirectory.file("$temporaryDir/mod.json")
        from(
            sourceSets["main"].output,
            layout.projectDirectory.dir("assets"),
            layout.projectDirectory.file("icon.png"),
            meta
        )

        metaInf.from(layout.projectDirectory.file("LICENSE"))
        doFirst{
            // Deliberately check if the mod meta is actually written in HJSON, since, well, some people actually use
            // it. But this is also not mentioned in the `README.md`, for the mischievous reason of driving beginners
            // into using JSON instead.
            val metaJson = layout.projectDirectory.file("mod.json")
            val metaHjson = layout.projectDirectory.file("mod.hjson")

            if(metaJson.asFile.exists() && metaHjson.asFile.exists()){
                throw IllegalStateException("Ambiguous mod meta: both `mod.json` and `mod.hjson` exist.")
            }else if(!metaJson.asFile.exists() && !metaHjson.asFile.exists()){
                throw IllegalStateException("Missing mod meta: neither `mod.json` nor `mod.hjson` exist.")
            }

            val isJson = metaJson.asFile.exists()
            val map = (if(isJson) metaJson else metaHjson).asFile
                .reader(Charsets.UTF_8)
                .use{Jval.read(it)}

            map.put("name", modName)
            meta.asFile.writer(Charsets.UTF_8).use{file -> BufferedWriter(file).use{map.writeTo(it, Jval.Jformat.formatted)}}
        }
    }

    val dex = tasks.register<Jar>("dex"){
        inputs.files(jar)
        archiveFileName = "$modArtifact.jar"

        val desktopJar = jar.flatMap{it.archiveFile}
        val dexJar = File(temporaryDir, "Dex.jar")

        from(zipTree(desktopJar), zipTree(dexJar))
        doFirst{
            logger.lifecycle("Running `d8`.")
            providers.exec{
                // Find Android SDK root.
                val sdkRoot = File(
                    OS.env("ANDROID_SDK_ROOT") ?: OS.env("ANDROID_HOME") ?:
                    throw IllegalStateException("Neither `ANDROID_SDK_ROOT` nor `ANDROID_HOME` is set.")
                )
    
                // Find `d8`.
                val d8 = File(sdkRoot, "build-tools/$androidBuildVersion/${if(OS.isWindows) "d8.bat" else "d8"}")
                if(!d8.exists()) throw IllegalStateException("Android SDK `build-tools;$androidBuildVersion` isn't installed or is corrupted")
    
                // Initialize a release build.
                val input = desktopJar.get().asFile
                val command = arrayListOf("$d8", "--release", "--min-api", androidMinVersion, "--output", "$dexJar", "$input")
    
                // Include all compile and runtime classpath.
                (configurations.compileClasspath.get().toList() + configurations.runtimeClasspath.get().toList()).forEach{
                    if(it.exists()) command.addAll(arrayOf("--classpath", it.path))
                }
    
                // Include Android platform as library.
                val androidJar = File(sdkRoot, "platforms/android-$androidSdkVersion/android.jar")
                if(!androidJar.exists()) throw IllegalStateException("Android SDK `platforms;android-$androidSdkVersion` isn't installed or is corrupted")
    
                command.addAll(arrayOf("--lib", "$androidJar"))
                if(OS.isWindows) command.addAll(0, arrayOf("cmd", "/c").toList())
    
                // Run `d8`.
                commandLine(command)
            }.result.get().rethrowFailure()
        }
    }

    tasks.register<DefaultTask>("install"){
        inputs.files(jar)

        val desktopJar = jar.flatMap{it.archiveFile}
        val dexJar = dex.flatMap{it.archiveFileName}
        doLast{
            val folder = Fi.get(OS.getAppDataDirectoryString("Mindustry")).child("mods")
            folder.mkdirs()

            val input = desktopJar.get().asFile
            val modName = input.nameWithoutExtension

            folder.child(input.name).delete()
            folder.child("${modName}Desktop.zip").delete()
            folder.child(dexJar.get()).delete()
            folder.child("${dexJar.get()}Desktop.zip").delete()
            Fi(input).copyTo(folder)

            logger.lifecycle("Copied :jar output to $folder.")
        }
    }

    tasks.register("runGame"){
        dependsOn("install")
        group = "modding"
        description = "Downloads (if missing) .jar file of Mindustry with version specified in gradle.properties"

        doLast {
            val applicationVersion: String by project
            val mindustryDirectory: String by project

            // Gets directory to check for game jar
            val dir = File(System.getProperty("user.home"), mindustryDirectory)
            if (!dir.exists()) dir.mkdirs()

            val mindustryJar = File(dir, "Mindustry$applicationVersion.jar")
            val url = "https://github.com/Anuken/Mindustry/releases/download/$applicationVersion/Mindustry.jar"
            if(!mindustryJar.exists()) {
                val conn = URL(url).openConnection() as HttpURLConnection
                conn.requestMethod = "HEAD"
                conn.connectTimeout = 5000
                conn.readTimeout = 5000

                if (conn.responseCode != 200) {
                    throw IllegalStateException(
                        "You're downloading a non-existing mindustry version: ($applicationVersion). Make sure that `applicationVersion` is a valid version!"
                    )
                }

                logger.lifecycle("Downloading $applicationVersion from $url ...")
                URL(url).openStream().use { input ->
                    mindustryJar.outputStream().use { output -> input.copyTo(output) }
                }
                logger.lifecycle("Downloaded to ${mindustryJar.absolutePath}.\n")
            }

            logger.lifecycle("Launching ${mindustryJar.name}...\n")
            
            exec {
                commandLine("java", "-jar", mindustryJar.absolutePath)
            }
        }
    }
}