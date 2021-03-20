package co.anitrend.support.markdown.center.controller

import co.anitrend.support.markdown.common.controller.MarkdownPluginController

internal class CenterPluginController(
    override val regex: Regex = Regex(
        PATTERN,
        RegexOption.IGNORE_CASE
    )
) : MarkdownPluginController() {

    companion object {
        const val PATTERN = "(~{3})[\\s\\S]+?(~{3})"
    }
}