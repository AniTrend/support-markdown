package co.anitrend.support.markdown

import co.anitrend.support.markdown.core.IMarkdownPlugin

interface ICoreRegexTest {
    val plugin: IMarkdownPlugin

    fun `defined regex pattern detect elements`()
}