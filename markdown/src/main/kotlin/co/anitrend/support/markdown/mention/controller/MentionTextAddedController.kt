package co.anitrend.support.markdown.mention.controller

import co.anitrend.support.markdown.common.IMarkdownPlugin
import co.anitrend.support.markdown.common.controller.MarkdownPluginController

internal class MentionTextAddedController(
    override val regex: Regex = Regex(
        PATTERN_MENTION,
        RegexOption.MULTILINE
    )
) : MarkdownPluginController() {

    fun asUserUrl(value: String): String {
        return "${IMarkdownPlugin.SITE_URL}/user/$value"
    }

    fun getContent(matchResult: MatchResult): String {
        return matchResult.groupValues.last()
    }

    companion object {
        const val PATTERN_MENTION = "(^|>| )@([A-Za-z0-9]+)"
    }
}