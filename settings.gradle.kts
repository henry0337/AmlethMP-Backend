rootProject.name = "AmlethMP-Backend"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://central.sonatype.com/repository/maven-snapshots") }
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")