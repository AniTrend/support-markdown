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
        val extensionSchema = project.extensions.extensionsSchema
        extensionSchema.forEach {
            println("Available extension for module ${project.path}: ${it.name} -> ${it.publicType}")
        }
    }

    /**
     * Inspecting available components
     */
    private fun Project.availableComponents() {
        val collectionSchema = project.components.asMap
        collectionSchema.forEach {
            println("Available component for module ${project.path}: ${it.key} -> ${it.value}")
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