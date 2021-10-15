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
    override val regex = Regex(PATTERN_HEADING)

    override fun processMarkdown(markdown: String): String {
        val replacement = mutableListOf<String>()
        val lines = markdown.split("\n")
        var skip = false

        for (i in lines.indices) {
            val current = lines[i]

            if (i < lines.size - 1 && current.isNotEmpty() && regex.matches(lines[i + 1])) {
                replacement.add("<h1>$current</h1>")
                skip = true
            } else if (!skip) {
                replacement.add(current)
            } else {
                skip = false
            }
        }

        return replacement.joinToString("\n")
    }

    companion object {
        private const val PATTERN_HEADING = "^-{2,}|={2,}$"

        fun create() = HeadingPlugin()
    }
}