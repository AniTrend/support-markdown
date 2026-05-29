package co.anitrend.support.markdown.mention.controller

internal class MentionTextAddedController {

    private val regex = Regex(
        PATTERN_MENTION,
        RegexOption.MULTILINE
    )

    fun findAllMatches(text: String, startIndex: Int = 0): Sequence<MatchResult> =
        regex.findAll(text, startIndex)

    fun asUserUrl(value: String): String {
        return "${SITE_URL}/user/$value"
    }

    fun getContent(matchResult: MatchResult): String {
        return matchResult.groupValues.last()
    }

    companion object {
        private const val SITE_URL = "https://anilist.co"
        const val GROUP_MENTION = 2
        const val PATTERN_MENTION = "(^|>| )@([A-Za-z0-9]+)"
    }
}
