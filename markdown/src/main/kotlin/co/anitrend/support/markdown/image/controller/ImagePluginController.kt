package co.anitrend.support.markdown.image.controller

import co.anitrend.support.markdown.common.controller.MarkdownPluginController

internal class ImagePluginController(
    override val regex: Regex = Regex(
        PATTERN,
        RegexOption.IGNORE_CASE
    )
) : MarkdownPluginController() {

    companion object {
        const val PATTERN = "[iI]mg([0-9%]+)?\\(.+?\\)"
    }
}