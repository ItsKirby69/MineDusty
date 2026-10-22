pluginManagement{
    repositories{
        gradlePluginPortal()
        mavenLocal()
        maven("https://raw.githubusercontent.com/GglLfr/EntityAnnoMaven/main")
        maven("https://raw.githubusercontent.com/GglLfr/MindustryClientMaven/main")
    }

    plugins{
        val entVersion = providers.gradleProperty("entVersion")
        val clientVersion = providers.gradleProperty("clientVersion")

        id("com.github.GglLfr.EntityAnno") version(entVersion)
        id("com.github.GglLfr.MindustryClient") version(clientVersion)
    }
}

