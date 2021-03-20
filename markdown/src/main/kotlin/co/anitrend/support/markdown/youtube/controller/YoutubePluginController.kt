package co.anitrend.support.markdown.youtube.controller

import co.anitrend.support.markdown.common.controller.MarkdownPluginController

internal class YoutubePluginController(
    override val regex: Regex = Regex(
        PATTERN,
        RegexOption.IGNORE_CASE
    )
) : MarkdownPluginController() {

    companion object {
        const val PATTERN = "youtube(?=\\()"
    }
}