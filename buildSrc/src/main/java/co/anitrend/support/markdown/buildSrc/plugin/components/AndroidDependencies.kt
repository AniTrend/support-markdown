package co.anitrend.support.markdown.buildSrc.plugin.components

import co.anitrend.support.markdown.buildSrc.plugin.strategy.DependencyStrategy
import co.anitrend.support.markdown.buildSrc.plugin.extensions.implementation
import org.gradle.api.Project

internal fun Project.configureDependencies() {
    val dependencyStrategy = DependencyStrategy(project)
    dependencies.implementation(
        fileTree("libs") {
            include("*.jar")
        }
    )
    dependencyStrategy.applyDependenciesOn(dependencies)
}