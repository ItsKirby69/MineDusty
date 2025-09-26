pluginManagement{
    repositories{
        gradlePluginPortal()
        maven("https://jitpack.io")
        //maven("https://raw.githubusercontent.com/ItsKirby69/EntityAnnoMaven/main")
    }

    plugins{
        val entVersion: String by settings
        id("com.github.ItsKirby69.EntityAnno") version(entVersion)
    }
}

if(JavaVersion.current().ordinal < JavaVersion.VERSION_17.ordinal){
    throw IllegalStateException("JDK 17 is a required minimum version. Yours: ${System.getProperty("java.version")}")
}

val modName: String by settings
rootProject.name = modName
