package co.anitrend.support.markdown.buildSrc.plugin.strategy

import co.anitrend.support.markdown.buildSrc.Libraries
import co.anitrend.support.markdown.buildSrc.common.sample
import org.gradle.api.artifacts.dsl.DependencyHandler

internal class DependencyStrategy(
    private val module: String
) {
    private fun DependencyHandler.applyDefaultDependencies() {
        add("implementation", Libraries.JetBrains.Kotlin.stdlib)

        if (module == sample) {
            add("implementation", Libraries.Koin.AndroidX.fragment)
            add("implementation", Libraries.Koin.AndroidX.viewmodel)
            add("implementation", Libraries.Koin.AndroidX.scope)
            add("implementation", Libraries.Koin.extension)
            add("implementation", Libraries.Koin.core)
        }

        // Testing libraries
        add("testImplementation", Libraries.junit)
        add("testImplementation", Libraries.mockk)
    }

    private fun DependencyHandler.applyTestDependencies() {
        if (module == sample)
            add("testImplementation", Libraries.Koin.test)
        add("androidTestImplementation", Libraries.AndroidX.Test.core)
        add("androidTestImplementation", Libraries.AndroidX.Test.runner)
        add("androidTestImplementation", Libraries.AndroidX.Test.Espresso.core)
    }

    private fun DependencyHandler.applyLifeCycleDependencies() {
        add("implementation", Libraries.AndroidX.Lifecycle.liveDataCoreKtx)
        add("implementation", Libraries.AndroidX.Lifecycle.viewModelKtx)
        add("implementation", Libraries.AndroidX.Lifecycle.liveDataKtx)
        add("implementation", Libraries.AndroidX.Lifecycle.runTimeKtx)
        add("implementation", Libraries.AndroidX.Lifecycle.extensions)
    }

    fun applyDependenciesOn(handler: DependencyHandler) {
        handler.applyDefaultDependencies()
        handler.applyTestDependencies()
        if (module == sample)
            handler.applyLifeCycleDependencies()
    }
}