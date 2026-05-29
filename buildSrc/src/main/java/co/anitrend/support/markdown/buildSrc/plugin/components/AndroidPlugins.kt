package co.anitrend.support.markdown.buildSrc.plugin.components

import co.anitrend.support.markdown.buildSrc.common.isLibraryModule
import co.anitrend.support.markdown.buildSrc.common.isSampleModule
import org.gradle.api.Project
import org.jetbrains.dokka.gradle.DokkaExtension

private fun Project.applyModulePlugin() {
    plugins.apply("com.diffplug.spotless")
    if (isLibraryModule()) {
        plugins.apply("com.android.library")
        plugins.apply("org.jetbrains.dokka")
        plugins.apply("maven-publish")
        configureDokka()
    }
    else
        plugins.apply("com.android.application")
}

private fun Project.configureDokka() {
    plugins.withId("org.jetbrains.dokka") {
        afterEvaluate {
            extensions.configure(DokkaExtension::class.java) {
                dokkaSourceSets.all {
                    if (name == "main") {
                        suppress.set(true)
                    }
                }
            }
        }
    }
}

internal fun Project.configurePlugins() {
    applyModulePlugin()
    plugins.apply("kotlin-android")
    if (isSampleModule()) {
        plugins.apply("kotlin-kapt")
    }
}