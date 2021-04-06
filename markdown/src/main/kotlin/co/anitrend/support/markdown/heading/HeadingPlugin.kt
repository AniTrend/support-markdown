package co.anitrend.support.markdown.heading

import co.anitrend.support.markdown.common.IMarkdownPlugin
import io.noties.markwon.AbstractMarkwonPlugin

/**
 * Alternatively, you can use == or -- instead of # text or ## text:
 * ```
 * Hello
 * ==
 * ```
 * or
 *  ```
 * World
 * --
 * ```
 *
 * Use as many = or - characters as you like, but there must be at least two.
 *
 * @since 0.12.0
 */
class HeadingPlugin private constructor(): IMarkdownPlugin, AbstractMarkwonPlugin() {

    /**
     * Regular expression that should be used for the implementing classing
     */
    override val regex = Regex(
        pattern = PATTERN_HEADING,
        option = RegexOption.MULTILINE
    )

    override fun processMarkdown(markdown: String): String {
        var replacement = markdown
        val matches = regex.findAll(markdown)
        matches.forEach { matchResult ->
            val content = matchResult.groupValues.last()

            replacement = replacement.replace(
                matchResult.value,
                "<h1>$content</h1>"
            )
        }
        return replacement
    }

    companion object {
        // TODO: only match when the line before is not a blank line
        private const val PATTERN_HEADING = "(.*\\b\\n)(-{2,}|={2,})"

        fun create() = HeadingPlugin()
    }
}