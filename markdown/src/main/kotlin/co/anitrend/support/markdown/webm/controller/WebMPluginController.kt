package co.anitrend.support.markdown.webm.controller

import co.anitrend.support.markdown.common.controller.MarkdownPluginController

internal class WebMPluginController(
    override val regex: Regex = Regex(
        PATTERN,
        RegexOption.IGNORE_CASE
    )
) : MarkdownPluginController() {

    companion object {
        const val PATTERN = "webm(?=\\()"
    }
}