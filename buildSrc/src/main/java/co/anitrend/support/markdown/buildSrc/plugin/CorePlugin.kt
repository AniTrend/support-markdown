package co.anitrend.support.markdown.buildSrc.plugin

import co.anitrend.support.markdown.buildSrc.plugin.components.configureAndroid
import co.anitrend.support.markdown.buildSrc.plugin.components.configureDependencies
import co.anitrend.support.markdown.buildSrc.plugin.components.configureOptions
import co.anitrend.support.markdown.buildSrc.plugin.components.configurePlugins
import org.gradle.api.Plugin
import org.gradle.api.Project

open class CorePlugin : Plugin<Project> {

    /**
     * Inspecting available extensions
     */
    private fun Project.availableExtensions() {
        extensions.extensionsSchema.forEach {
            logger.lifecycle("Available extension for module ${project.path}: ${it.name} -> ${it.publicType}")
        }
    }

    /**
     * Inspecting available components
     */
    private fun Project.availableComponents() {
        components.forEach {
            logger.lifecycle("Available component for module ${project.path}: ${it.name} -> ${it}")
        }
    }

    override fun apply(project: Project) {
        project.configurePlugins()
        project.availableExtensions()
        project.availableComponents()
        project.configureAndroid()
        project.configureOptions()
        project.configureDependencies()
    }
}