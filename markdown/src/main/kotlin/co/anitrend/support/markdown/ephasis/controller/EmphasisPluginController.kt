package co.anitrend.support.markdown.ephasis.controller

import co.anitrend.support.markdown.common.controller.MarkdownPluginController

internal class EmphasisPluginController(
    override val regex: Regex = Regex(
        PATTERN,
        RegexOption.IGNORE_CASE
    )
) : MarkdownPluginController() {

    companion object {
        const val PATTERN = "__([\\s\\S]*?)__"
    }
}