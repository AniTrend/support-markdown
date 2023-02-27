package co.anitrend.support.markdown.buildSrc.plugin.components

import co.anitrend.support.markdown.buildSrc.common.isLibraryModule
import co.anitrend.support.markdown.buildSrc.plugin.extensions.baseExtension
import co.anitrend.support.markdown.buildSrc.plugin.extensions.publishingExtension
import co.anitrend.support.markdown.buildSrc.plugin.extensions.releaseProperties
import com.android.build.gradle.internal.dsl.BuildType
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getValue
import java.util.*

private fun Properties.applyToBuildConfigForBuild(buildType: BuildType) {
    forEach { propEntry ->
        val key = propEntry.key as String
        val value = propEntry.value as String
        println("Adding build config field property -> key: $key value: $value")
        buildType.buildConfigField("String", key, value)
    }
}

@Suppress("UnstableApiUsage")
internal fun Project.configureOptions() {
    if (isLibraryModule()) {
        val mainSourceSet = baseExtension().sourceSets["main"].java.srcDirs

        val sourcesJar by tasks.register("sourcesJar", Jar::class.java) {
            archiveClassifier.set("sources")
            from(mainSourceSet)
        }

        val classesJar by tasks.register("classesJar", Jar::class.java) {
            from("${project.buildDir}/intermediates/classes/release")
        }

        artifacts {
            add("archives", classesJar)
            add("archives", sourcesJar)
        }

        publishingExtension().publications {
            val component = components.findByName("android")

            println("Configuring maven publication options for ${project.path}:maven with component -> ${component?.name}")
            create("maven", MavenPublication::class.java) {
                groupId = "co.anitrend.markdown"
                artifactId = project.name
                version = releaseProperties["version"] as? String

                artifact(sourcesJar)
                artifact("${project.buildDir}/outputs/aar/${project.name}-release.aar")
                from(component)

                pom {
                    name.set("Support Markdown")
                    description.set("A markdown parse for android which uses the AniList")
                    url.set("https://github.com/anitrend/support-markdown")
                    licenses {
                        license {
                            name.set("Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("wax911")
                            name.set("Maxwell Mapako")
                            organizationUrl.set("https://github.com/anitrend")
                        }
                    }
                }
            }
        }
    }
}