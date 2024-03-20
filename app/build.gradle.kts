plugins {
    id("co.anitrend.support.markdown")
    id("com.apollographql.apollo3").version("3.8.2")
}

android {
    namespace = "co.anitrend.support.markdown.sample"
}

apollo {
    packageName.set("co.anitrend.support.markdown.domain.entities")
}


configurations {
    fun Configuration.excludeDefault() {
        exclude(
            mapOf(
                "group" to "org.jetbrains",
                "module" to "annotations"
            )
        )
    }
    logger.lifecycle("Configurations available [${configurations.joinToString()}]")
    getByName("implementation") { excludeDefault() }
    getByName("androidTestImplementation") { excludeDefault() }
}

dependencies {
    implementation(project(":markdown"))

    implementation("com.apollographql.apollo3:apollo-runtime:3.8.3")

    implementation(libs.google.android.material)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.constraintlayout)

    implementation(libs.androidx.paging.common)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.runtime.ktx)

    implementation(libs.androidx.swipeRefreshLayout)

    implementation(libs.androidx.recyclerView)

    implementation(libs.markwon.core)
    implementation(libs.markwon.editor)
    implementation(libs.markwon.html)
    implementation(libs.markwon.coil)
    implementation(libs.markwon.parser)
    implementation(libs.markwon.linkify)
    implementation(libs.markwon.simple.ext)
    implementation(libs.markwon.syntax.highlight)
    implementation(libs.markwon.ext.tasklist)
    implementation(libs.markwon.ext.strikethrough)
    implementation(libs.markwon.ext.tables)

    implementation(libs.coil)
    implementation(libs.coil.video)
    implementation(libs.coil.svg)
    implementation(libs.coil.gif)

    implementation(libs.saket.betterlinkmovementmethod)
}
