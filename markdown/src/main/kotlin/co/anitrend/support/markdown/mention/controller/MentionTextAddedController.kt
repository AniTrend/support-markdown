package co.anitrend.support.markdown.mention.controller

import co.anitrend.support.markdown.common.IMarkdownPlugin
import co.anitrend.support.markdown.common.controller.MarkdownPluginController

internal class MentionTextAddedController(
    override val regex: Regex = Regex(
        PATTERN_MENTION,
        RegexOption.IGNORE_CASE
    )
) : MarkdownPluginController() {

    fun asUserUrl(value: String): String {
        return "${IMarkdownPlugin.SITE_URL}/user/$value"
    }

    fun getContent(matchResult: MatchResult): String {
        return matchResult.groupValues.first()
    }

    fun getMention(content: String): String {
        return content.substring(VALUE_START_INDEX)
    }

    companion object {
        const val PATTERN_MENTION = "\\B@\\w+"

        private const val VALUE_START_INDEX = 1
    }
}