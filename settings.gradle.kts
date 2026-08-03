pluginManagement{
    repositories{
        gradlePluginPortal()
        mavenLocal()
        maven("https://raw.githubusercontent.com/GglLfr/EntityAnnoMaven/main")
    }

    plugins{
        val entVersion = providers.gradleProperty("entVersion").get()
        id("com.github.GglLfr.EntityAnno") version(entVersion)
    }
}

if(JavaVersion.current().ordinal < JavaVersion.VERSION_17.ordinal){
    throw IllegalStateException("JDK 17 is a required minimum version. Yours: ${System.getProperty("java.version")}")
}

val modName = providers.gradleProperty("modName").get()
rootProject.name = modName
