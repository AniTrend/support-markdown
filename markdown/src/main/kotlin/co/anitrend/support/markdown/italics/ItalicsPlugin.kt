package co.anitrend.support.markdown.italics

import co.anitrend.support.markdown.common.IMarkdownPlugin
import co.anitrend.support.markdown.italics.delimiter.ItalicsDelimiter
import io.noties.markwon.AbstractMarkwonPlugin
import org.commonmark.parser.Parser

/**
 * Italics plugin
 */
class ItalicsPlugin: IMarkdownPlugin, AbstractMarkwonPlugin() {

    /**
     * Regular expression that should be used for the implementing classing
     */
    override val regex by lazy(LazyThreadSafetyMode.NONE) {
        Regex(
            pattern = PATTERN_EMPHASIS,
            options = setOf(RegexOption.IGNORE_CASE, RegexOption.MULTILINE)
        )
    }

    override fun configureParser(builder: Parser.Builder) {
        //builder.customDelimiterProcessor(ItalicsDelimiter())
    }

    override fun processMarkdown(markdown: String): String {
        var replacement = markdown
        val matches = regex.findAll(markdown)
        matches.forEach { matchResult ->
            val content = matchResult.groupValues.last()

            replacement = replacement.replace(
                matchResult.value,
                "<i>$content</i>"
            )
        }
        return replacement
    }

    companion object {
        private const val PATTERN_EMPHASIS = "\\*(.*)\\*"

        fun create() = ItalicsPlugin()
    }
}