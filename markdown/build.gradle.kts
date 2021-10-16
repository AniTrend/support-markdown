import co.anitrend.support.markdown.buildSrc.Libraries

plugins {
	id("co.anitrend.support.markdown")
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
    implementation(Libraries.Markwon.core)
    implementation(Libraries.Markwon.editor)
    implementation(Libraries.Markwon.html)
    implementation(Libraries.Markwon.coil)
    implementation(Libraries.Markwon.parser)
    implementation(Libraries.Markwon.linkify)
    implementation(Libraries.Markwon.simpleExt)
    implementation(Libraries.Markwon.syntaxHighlight)
    implementation(Libraries.Markwon.Extension.taskList)
    implementation(Libraries.Markwon.Extension.strikeThrough)
    implementation(Libraries.Markwon.Extension.tables)

    implementation(Libraries.JetBrains.Markdown.markdown)

    testImplementation(Libraries.AndroidX.Test.coreKtx)
    testImplementation(Libraries.AndroidX.Test.Extension.junit)
    testImplementation(Libraries.AndroidX.Test.Extension.junitKtx)
}
