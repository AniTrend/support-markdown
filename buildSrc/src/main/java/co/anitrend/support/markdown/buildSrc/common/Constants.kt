package co.anitrend.support.markdown.buildSrc.common

import org.gradle.api.Project

internal const val sample = "app"
internal const val markdown = "markdown"

fun Project.isSampleModule() = name == sample
fun Project.isLibraryModule() = name == markdown