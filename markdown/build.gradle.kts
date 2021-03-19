import co.anitrend.support.markdown.buildSrc.Libraries

plugins {
	id("co.anitrend.support.markdown")
}

dependencies {
    implementation(Libraries.Markwon.core)
    implementation(Libraries.Markwon.html)
    implementation(Libraries.Markwon.coil)
    implementation(Libraries.Markwon.parser)
    implementation(Libraries.Markwon.linkify)
    implementation(Libraries.Markwon.simpleExt)
    implementation(Libraries.Markwon.syntaxHighlight)
    implementation(Libraries.Markwon.Extension.taskList)
    implementation(Libraries.Markwon.Extension.strikeThrough)
    implementation(Libraries.Markwon.Extension.tables)

    testImplementation(Libraries.AndroidX.Test.coreKtx)
    testImplementation(Libraries.AndroidX.Test.Extension.junit)
    testImplementation(Libraries.AndroidX.Test.Extension.junitKtx)
}
