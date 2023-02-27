package co.anitrend.support.markdown.buildSrc.plugin.components

import co.anitrend.support.markdown.buildSrc.common.Configuration
import co.anitrend.support.markdown.buildSrc.common.isSampleModule
import co.anitrend.support.markdown.buildSrc.plugin.extensions.baseAppExtension
import co.anitrend.support.markdown.buildSrc.plugin.extensions.baseExtension
import co.anitrend.support.markdown.buildSrc.plugin.extensions.libraryExtension
import co.anitrend.support.markdown.buildSrc.plugin.extensions.libs
import co.anitrend.support.markdown.buildSrc.plugin.extensions.releaseProperties
import co.anitrend.support.markdown.buildSrc.plugin.extensions.spotlessExtension
import com.android.build.gradle.internal.dsl.DefaultConfig
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import java.io.File

private fun Project.configureLint() = libraryExtension().run {
    lint {
        abortOnError = false
        ignoreWarnings = false
        ignoreTestSources = true
    }
}

internal fun Project.configureSpotless(): Unit = spotlessExtension().run {
    kotlin {
        target("**/*.kt")
        targetExclude(
            "$buildDir/**/*.kt",
            "**/androidTest/**/*.kt",
            "**/test/**/*.kt",
            "bin/**/*.kt"
        )
        ktlint(libs.versions.ktlint.get()).userData(
            mapOf("android" to "true")
        )
    }
}

@Suppress("UnstableApiUsage")
private fun DefaultConfig.applyAdditionalConfiguration(project: Project) {
    if (project.isSampleModule()) {
        applicationId = "co.anitrend.support.markdown.sample"
        project.baseAppExtension().buildFeatures {
            viewBinding = true
        }
    }
    else
        consumerProguardFiles.add(File("consumer-rules.pro"))

    println("Applying vector drawables configuration for module -> ${project.path}")
    vectorDrawables.useSupportLibrary = true
}

internal fun Project.configureAndroid(): Unit = baseExtension().run {
    compileSdkVersion(Configuration.compileSdk)
    defaultConfig {
        minSdk = Configuration.minSdk
        targetSdk = Configuration.targetSdk
        versionCode = releaseProperties["code"] as? Int
        versionName = releaseProperties["version"] as? String
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        applyAdditionalConfiguration(project)
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isTestCoverageEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        getByName("debug") {
            isMinifyEnabled = false
            isTestCoverageEnabled = true
        }
    }

    if (!project.isSampleModule()) {
        configureLint()
    }

    sourceSets {
        map { androidSourceSet ->
            androidSourceSet.java.srcDir(
                "src/${androidSourceSet.name}/kotlin"
            )
        }
        if (!project.isSampleModule()) {
            getByName("test") {
                resources.srcDirs(file("src/test/resources"))
            }
        }
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks.withType(KotlinCompile::class.java) {
        kotlinOptions {
            allWarningsAsErrors = false
            kotlinOptions {
                allWarningsAsErrors = false
                // Filter out modules that won't be using coroutines
                freeCompilerArgs = listOf("-Xopt-in=kotlin.Experimental")
            }
        }
    }

    tasks.withType(KotlinJvmCompile::class.java) {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}