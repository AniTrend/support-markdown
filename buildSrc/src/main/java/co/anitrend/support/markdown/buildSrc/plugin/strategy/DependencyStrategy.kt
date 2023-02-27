package co.anitrend.support.markdown.buildSrc.plugin.strategy

import co.anitrend.support.markdown.buildSrc.common.isSampleModule
import co.anitrend.support.markdown.buildSrc.common.sample
import co.anitrend.support.markdown.buildSrc.plugin.extensions.libs
import co.anitrend.support.markdown.buildSrc.plugin.extensions.test
import co.anitrend.support.markdown.buildSrc.plugin.extensions.androidTest
import co.anitrend.support.markdown.buildSrc.plugin.extensions.implementation
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler

internal class DependencyStrategy(private val project: Project) {
    private fun DependencyHandler.applyDefaultDependencies() {
        implementation(project.libs.jetbrains.kotlin.stdlib.jdk8)
        implementation(project.libs.jetbrains.kotlin.reflect)

        if (project.isSampleModule()) {
            implementation(project.libs.koin.android)
            implementation(project.libs.koin.core)
        }

        // Testing libs
        test(project.libs.junit)
        test(project.libs.mockk)
    }

    private fun DependencyHandler.applyTestDependencies() {
        if (project.isSampleModule()) {
            test(project.libs.koin.test)
            test(project.libs.koin.test.junit4)
        }
        androidTest(project.libs.androidx.test.ext.junit.ktx)
        androidTest(project.libs.androidx.test.core.ktx)
        androidTest(project.libs.androidx.test.runner)
        androidTest(project.libs.mockk.android)
    }

    private fun DependencyHandler.applyLifeCycleDependencies() {
        implementation(project.libs.androidx.lifecycle.livedata.core.ktx)
        implementation(project.libs.androidx.lifecycle.viewmodel.ktx)
        implementation(project.libs.androidx.lifecycle.livedata.ktx)
        implementation(project.libs.androidx.lifecycle.runtime.ktx)
        implementation(project.libs.androidx.lifecycle.extensions)
    }

    fun applyDependenciesOn(handler: DependencyHandler) {
        handler.applyDefaultDependencies()
        handler.applyTestDependencies()
        if (project.isSampleModule())
            handler.applyLifeCycleDependencies()
    }
}