import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id("com.github.ben-manes.versions")
}

buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath(co.anitrend.support.markdown.buildSrc.Libraries.Android.Tools.buildGradle)
        classpath(co.anitrend.support.markdown.buildSrc.Libraries.JetBrains.Kotlin.Gradle.plugin)
        classpath(co.anitrend.support.markdown.buildSrc.Libraries.Apollo.Gradle.plugin)
        classpath(co.anitrend.support.markdown.buildSrc.Libraries.Koin.Gradle.plugin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven {
            url = java.net.URI(co.anitrend.support.markdown.buildSrc.Libraries.Repositories.jitPack)
        }
        maven {
            url = java.net.URI(co.anitrend.support.markdown.buildSrc.Libraries.Repositories.dependencyUpdates)
        }
    }
}

tasks {
    val clean by registering(Delete::class) {
        delete(rootProject.buildDir)
    }
}

tasks.named(
    "dependencyUpdates",
    DependencyUpdatesTask::class.java
).configure {
    checkForGradleUpdate = false
    outputFormatter = "json"
    outputDir = "build/dependencyUpdates"
    reportfileName = "report"
}
