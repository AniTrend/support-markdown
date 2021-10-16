import co.anitrend.support.markdown.buildSrc.Libraries
import org.gradle.api.artifacts.Configuration

plugins {
    id("co.anitrend.support.markdown")
    id("koin")
}

apollo {
    // instruct the compiler to generate Kotlin models
    generateKotlinModels.set(true)
}

configurations {
    fun  excludeDefault(configuration: Configuration) {
        configuration.exclude(
            mapOf(
                "group" to "org.jetbrains",
                "module" to "annotations"
            )
        )
    }
    println("Configurations available [${configurations.joinToString()}]")
    getByName("implementation") {
        excludeDefault(this)
    }
    getByName("androidTestImplementation") {
        excludeDefault(this)
    }
}

dependencies {
    implementation(project(":markdown"))

    implementation(Libraries.Google.Material.material)

    implementation(Libraries.AndroidX.Core.coreKtx)
    implementation(Libraries.AndroidX.Fragment.fragmentKtx)
    implementation(Libraries.AndroidX.Activity.activityKtx)
    implementation(Libraries.AndroidX.ConstraintLayout.constraintLayout)
    implementation(Libraries.AndroidX.SwipeRefresh.swipeRefreshLayout)
    implementation(Libraries.AndroidX.Preference.preferenceKtx)
    implementation(Libraries.AndroidX.Recycler.recyclerView)

    implementation(Libraries.AndroidX.Paging.common)
    implementation(Libraries.AndroidX.Paging.runtime)
    implementation(Libraries.AndroidX.Paging.runtimeKtx)

    implementation(Libraries.Apollo.runtime)
    implementation(Libraries.Apollo.android)
    implementation(Libraries.Apollo.coroutines)

    implementation(Libraries.Markwon.core)
    implementation(Libraries.Markwon.html)
    implementation(Libraries.Markwon.image)
    implementation(Libraries.Markwon.coil)
    implementation(Libraries.Markwon.linkify)
    implementation(Libraries.Markwon.simpleExt)
    implementation(Libraries.Markwon.Extension.taskList)
    implementation(Libraries.Markwon.Extension.strikeThrough)

    implementation(Libraries.JetBrains.Markdown.markdown)

    implementation(Libraries.Coil.coil)
    implementation(Libraries.Coil.video)
    implementation(Libraries.Coil.svg)
    implementation(Libraries.Coil.gif)

    implementation(Libraries.betterLinkMovement)
}
