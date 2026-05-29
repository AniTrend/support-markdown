package co.anitrend.support.markdown.webm

import io.noties.markwon.AbstractMarkwonPlugin

/**
 * Handles AniList's `webm(URL)` custom syntax for video embeds.
 *
 * Converts the syntax to an `<a>` tag linking to the video with a poster thumbnail
 * during [processMarkdown], relying on HtmlPlugin for rendering.
 */
class WebMPlugin private constructor() : AbstractMarkwonPlugin() {

    private val regex = Regex(
        pattern = PATTERN_WEB_M,
        option = RegexOption.IGNORE_CASE
    )

    override fun processMarkdown(markdown: String): String {
        var replacement = markdown
        val matches = regex.findAll(markdown)
        matches.forEach { matchResult ->
            val matchGroups = matchResult.groupValues
            val resourceUrl = matchGroups.last()

            replacement = replacement.replace(
                matchResult.value,
                """<a href="$resourceUrl"><img src="$resourceUrl" /></a>"""
            )
        }
        return replacement
    }

    companion object {

        private const val PATTERN_WEB_M = "(webm\\d*|\\d*px|\\d*%)\\((.+)\\)"

        fun create() = WebMPlugin()
    }
}
