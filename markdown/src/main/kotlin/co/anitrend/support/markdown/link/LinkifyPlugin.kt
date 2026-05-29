package co.anitrend.support.markdown.link

import io.noties.markwon.AbstractMarkwonPlugin

/**
 * Auto-links bare URLs in markdown text by wrapping them in `<a href="...">...</a>` tags
 * during [processMarkdown].
 *
 * Handles `http`, `https`, and `ftp` protocols. Used internally as a string-level
 * bridge within the HtmlPlugin pipeline.
 */
internal class LinkifyPlugin private constructor() : AbstractMarkwonPlugin() {

    private val regex = Regex(PATTERN_LINK)

    override fun processMarkdown(markdown: String): String {
        var replacement = markdown
        val matches = regex.findAll(markdown)
        matches.forEach { matchResult ->
            val value = matchResult.value
            replacement = replacement.replace(
                value,
                """<a href="$value">${value}</a>"""
            )
        }
        return replacement
    }

    companion object {
        private const val PATTERN_LINK = "((?<=\\s)|^)(http|ftp|https):\\/\\/([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:\\/~+#-]*[\\w@?^=%&\\/~+#-])?"

        fun create() = LinkifyPlugin()
    }
}
