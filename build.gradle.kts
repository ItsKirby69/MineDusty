import arc.files.Fi
import arc.util.OS
import arc.util.serialization.Jval
import ent.EntityAnnoExtension
import java.io.*
import java.util.jar.*
import java.net.*

buildscript{
    val mindustryVersion = providers.gradleProperty("mindustryVersion").get()
    val mindustry = if(mindustryVersion == "be") "MindustryBuilds" else "Mindustry"

    dependencies{
        classpath("Anuken:$mindustry:$mindustryVersion")
    }

    configurations.configureEach{
        // Resolve the correct Mindustry dependency.
        resolutionStrategy.eachDependency{
            if(requested.group == "Anuken" && requested.name.startsWith("Mindustry")){
                useTarget("Anuken:$mindustry:$mindustryVersion")
            }
        }
    }

    repositories{
        ivy{
            url = uri("https://github.com")
            patternLayout{
                artifact(when(mindustryVersion){
                    "latest" -> "Anuken/Mindustry/releases/latest/download/dependencies.jar"
                    "be" -> "Anuken/MindustryBuilds/releases/download/master/latest.jar"
                    else -> "Anuken/Mindustry/releases/download/[revision]/dependencies.jar"
                })
                metadataSources{artifact()}
            }
            content{
                includeVersion("Anuken", mindustry, mindustryVersion)
            }
        }
    }
}

plugins{
    java
    id("com.github.GglLfr.EntityAnno") apply false
}

val mindustryVersion = providers.gradleProperty("mindustryVersion").get()
val mindustry = if(mindustryVersion == "be") "MindustryBuilds" else "Mindustry"
val entVersion = providers.gradleProperty("entVersion").get()

val modName = providers.gradleProperty("modName").get()
val modArtifact = providers.gradleProperty("modArtifact").get()
val modFetch = providers.gradleProperty("modFetch").get()
val modGenSrc = providers.gradleProperty("modGenSrc").get()
val modGen = providers.gradleProperty("modGen").get()

fun mindustry(): String{
    return "Anuken:$mindustry:$mindustryVersion"
}

fun entity(module: String): String{
    return "com.github.GglLfr.EntityAnno$module:$entVersion"
}

allprojects{
    apply(plugin = "java")
    sourceSets["main"].java.setSrcDirs(listOf(layout.projectDirectory.dir("src")))
    dependencies{
        abstract class TrimSources : TransformAction<TransformParameters.None>{
            @get:InputArtifact
            abstract val file: Provider<FileSystemLocation>

            override fun transform(outputs: TransformOutputs){
                val input = file.get().asFile
                val classes = outputs.file(input.name)

                JarFile(input).use{jar ->
                    val entries = jar.entries()
                    JarOutputStream(FileOutputStream(classes)).use{classes ->
                        for(entry in entries){
                            if(entry.name.endsWith(".java")) continue

                            classes.putNextEntry(JarEntry(entry.name))
                            jar.getInputStream(entry).use{it.copyTo(classes)}
                            classes.closeEntry()
                        }
                    }
                }
            }
        }

        registerTransform(TrimSources::class){
            from.attribute(ArtifactTypeDefinition.ARTIFACT_TYPE_ATTRIBUTE, ArtifactTypeDefinition.JAR_TYPE)
            to.attribute(ArtifactTypeDefinition.ARTIFACT_TYPE_ATTRIBUTE, "jar-stripped")
        }
    }

    configurations.configureEach{
        // Resolve the correct Mindustry dependency.
        resolutionStrategy.eachDependency{
            if(requested.group == "Anuken" && requested.name.startsWith("Mindustry")){
                useTarget("Anuken:$mindustry:$mindustryVersion")
            }
        }
    }

    configurations.matching{it.isCanBeResolved}.configureEach{
        attributes{
            attribute(ArtifactTypeDefinition.ARTIFACT_TYPE_ATTRIBUTE, "jar-stripped")
        }
    }

    repositories{

        // Use Ivy repository for Mindustry builds.
        ivy{
            url = uri("https://github.com")
            patternLayout{
                artifact(when(mindustryVersion){
                    "latest" -> "Anuken/Mindustry/releases/latest/download/dependencies.jar"
                    "be" -> "Anuken/MindustryBuilds/releases/download/master/latest.jar"
                    else -> "Anuken/Mindustry/releases/download/[revision]/dependencies.jar"
                })
                metadataSources{artifact()}
            }
            content{
                includeVersion("Anuken", mindustry, mindustryVersion)
            }
        }

        // Necessary Maven repositories to pull dependencies from.
        mavenLocal()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/repositories/releases/")
        maven("https://raw.githubusercontent.com/GglLfr/EntityAnnoMaven/main")
    }

    tasks.withType<JavaCompile>().configureEach{
        options.apply{
            compilerArgs.add("-Xlint:-options")
            compilerArgs.add("-implicit:none")
            compilerArgs.addAll(providers.gradleProperty("org.gradle.jvmargs").get()
                .split(Regex("\\s+"))
                .filter{it.startsWith("--add-opens")}
                .map{"--add-exports=${it.substring("--add-opens=".length)}"}
            )

            isIncremental = true
            isFork = false
            encoding = "UTF-8"
        }

        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
}

project(":"){
    apply(plugin = "com.github.GglLfr.EntityAnno")

    val localModName = modName
    val localMindustryVersion = mindustryVersion
    configure<EntityAnnoExtension>{
        modName = localModName
        mindustryVersion = localMindustryVersion
        revisionDir = layout.projectDirectory.dir("revisions").asFile
        fetchPackage = modFetch
        genSrcPackage = modGenSrc
        genPackage = modGen
    }

    dependencies{
        // Use the entity generation annotation processor.
        compileOnly(entity(":entity"))
        add("kapt", entity(":entity"))

        compileOnly(mindustry())
    }

    val jar = tasks.named<Jar>("jar"){
        archiveFileName = "${modArtifact}Desktop.jar"

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
        val usedMeta = if(isJson) metaJson else metaHjson

        from(
            files(sourceSets["main"].output.classesDirs),
            files(sourceSets["main"].output.resourcesDir),
            configurations.runtimeClasspath.map{conf -> conf.map{if(it.isDirectory) it else zipTree(it)}},

            files(layout.projectDirectory.dir("assets")),
            layout.projectDirectory.file("icon.png"),
            usedMeta
        )

        metaInf.from(layout.projectDirectory.file("LICENSE"))
        val localModName = modName
        doFirst{
            if(usedMeta.asFile.reader(Charsets.UTF_8).use{Jval.read(it)}.getString("name") != localModName) {
                throw GradleException("Mod name mismatch in `${usedMeta.asFile.name}`; please synchronize with `gradle.properties`")
            }
        }
    }

    val dex = tasks.register<Jar>("dex"){
        inputs.files(jar)
        archiveFileName = "$modArtifact.jar"

        val desktopJar = jar.flatMap{it.archiveFile}
        val dexJar = File(temporaryDir, "Dex.jar")

        val androidSdkVersion = providers.gradleProperty("androidSdkVersion").get()
        val androidBuildVersion = providers.gradleProperty("androidBuildVersion").get()
        val androidMinVersion = providers.gradleProperty("androidMinVersion").get()

        val classpaths = configurations.compileClasspath.get().toList() + configurations.runtimeClasspath.get().toList()
        val providers = project.providers

        from(zipTree(desktopJar), zipTree(dexJar))
        doFirst{
            // Find Android SDK root.
            val sdkRoot = File(
                OS.env("ANDROID_SDK_ROOT") ?: OS.env("ANDROID_HOME")
                ?: throw IllegalStateException("Neither `ANDROID_SDK_ROOT` nor `ANDROID_HOME` is set.")
            )

            // Find `d8`.
            val d8 = File(sdkRoot, "build-tools/$androidBuildVersion/${if(OS.isWindows) "d8.bat" else "d8"}")
            if(!d8.exists()) throw IllegalStateException("Android SDK `build-tools;$androidBuildVersion` isn't installed or is corrupted")

            // Initialize a release build.
            val input = desktopJar.get().asFile
            val command = arrayListOf("$d8", "--release", "--min-api", androidMinVersion, "--output", "$dexJar", "$input")

            // Include all compile and runtime classpath.
            classpaths.forEach{
                if(it.exists()) command.addAll(arrayOf("--classpath", it.path))
            }

            // Include Android platform as library.
            val androidJar = File(sdkRoot, "platforms/android-$androidSdkVersion/android.jar")
            if(!androidJar.exists()) throw IllegalStateException("Android SDK `platforms;android-$androidSdkVersion` isn't installed or is corrupted")

            command.addAll(arrayOf("--lib", "$androidJar"))
            if(OS.isWindows) command.addAll(0, arrayOf("cmd", "/c").toList())

            // Run `d8`.
            providers.exec{commandLine(command)}.result.get().rethrowFailure()
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
        description = "Downloads (if missing) and runs .jar file of Mindustry with installed mod on version specified in gradle.properties"

        val providers = project.providers
        val applicationVersion = providers.gradleProperty("applicationVersion").get()
        val mindustryDirectory = providers.gradleProperty("mindustryDirectory").get()
        
        doLast {
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
            
            providers.exec{commandLine("java", "-jar", mindustryJar.absolutePath)}
            .result.get().rethrowFailure()
        }
    }
    // Pain
    // tasks.register<JavaExec>("genBundles"){
    //     dependsOn("runGame")
    //     group = "modding"
    //     description = "Generates a bundle file with the latest content"

    //     classpath = sourceSets["main"].runtimeClasspath + configurations.compileClasspath.get()
    //     mainClass.set("minedusty.utils.GenBundles")
    // }
}