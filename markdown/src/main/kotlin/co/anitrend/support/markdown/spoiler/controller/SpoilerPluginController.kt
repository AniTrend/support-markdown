package co.anitrend.support.markdown.spoiler.controller

import co.anitrend.support.markdown.common.controller.MarkdownPluginController

internal class SpoilerPluginController(
    override val regex: Regex = Regex(
        PATTERN,
        RegexOption.IGNORE_CASE
    )
) : MarkdownPluginController() {

    fun replaceWithIdentifier(builder: StringBuilder): String {
        return builder.replace(regex, IDENTIFIER)
    }

    companion object {
        const val PATTERN = "(~!)[\\s\\S]+?(!~)"
        const val IDENTIFIER = "SPOILER_CONTENT"
        const val REPLACEMENT_TAG_START = "<spoiler>"
        const val REPLACEMENT_TAG_END = "</spoiler>"
    }
}