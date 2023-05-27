plugins {
	id("co.anitrend.support.markdown")
}

android {
    namespace = "io.wax911.support.markdown"
}

dependencies {
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
}
