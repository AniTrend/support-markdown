package co.anitrend.support.markdown.horizontal

import co.anitrend.support.markdown.common.IMarkdownPlugin
import co.anitrend.support.markdown.heading.HeadingPlugin
import io.noties.markwon.AbstractMarkwonPlugin

/**
 *  Enter three or more - or * characters, optionally with spaces:
 *
 * `---`
 *
 *  `***`
 *
 *  `- - -`
 *
 *  `* * *`
 *
 * Make sure to have a blank line either side to avoid ambiguity:
 * ```
 * this is a header
 * ---
 * ```
 *
 * ```
 *  this is text followed by a horizontal line
 *  ---
 * ```
 *
 * Alternatively, you can use the <hr> HTML tag.
 */
class HorizontalLinePlugin private constructor() : IMarkdownPlugin, AbstractMarkwonPlugin() {
    /**
     * Regular expression that should be used for the implementing classing
     */
    override val regex: Regex = Regex(PATTERN_HORIZONTAL_LINE, RegexOption.MULTILINE)

    override fun processMarkdown(markdown: String): String {
        return regex.replace(markdown, "<hr>")
    }

    companion object {
        // TODO need to cater for these which include a space in between
        //private const val PATTERN_HORIZONTAL_LINE = "^-{3,}[\\r\\n]+\$|^\\*{3,}[\\r\\n]+\$|^_{3,}[\\r\\n]+\$"
        private const val PATTERN_HORIZONTAL_LINE = "^-{3,}[\\r\\n]+\$|^\\*{3,}[\\r\\n]+\$|^_{3,}\$"

        fun create() = HorizontalLinePlugin()
    }
}