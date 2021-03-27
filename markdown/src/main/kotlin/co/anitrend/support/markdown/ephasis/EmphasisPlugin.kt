package co.anitrend.support.markdown.ephasis

import co.anitrend.support.markdown.common.IMarkdownPlugin
import co.anitrend.support.markdown.ephasis.delimiter.EmphasisDelimiter
import io.noties.markwon.AbstractMarkwonPlugin
import org.commonmark.parser.Parser

/**
 *  Put two * or _ characters either side of the text:
 * `__hello__` becomes **hello**
 *  `**world**` becomes **world**
 *
 * You can also use the HTML `<b>...</b>` or `<strong>...</strong>` tags:
 * `<b>hello</b>` becomes **hello**
 * `<strong>hello</strong>` becomes **hello**
 *
 * This can be combined with italics like so: `_**hello world**_`
 *
 * @since 0.12.0
 */
class EmphasisPlugin : IMarkdownPlugin, AbstractMarkwonPlugin() {

    /**
     * Regular expression that should be used for the implementing classing
     */
    override val regex by lazy(LazyThreadSafetyMode.NONE) {
        Regex(
            pattern = PATTERN_EMPHASIS,
            option = RegexOption.IGNORE_CASE
        )
    }

    override fun configureParser(builder: Parser.Builder) {
        //builder.customDelimiterProcessor(EmphasisDelimiter())
    }

    override fun processMarkdown(markdown: String): String {
        //return super.processMarkdown(markdown)
        var replacement = markdown
        val matches = regex.findAll(markdown)
        matches.forEach { matchResult ->
            val matchValues = matchResult.groupValues.filterNot(String::isNullOrEmpty)
            //val content = matchResult.groupValues.firstOrNull() ?: matchResult.groupValues.last()
            val content = matchValues.last()

            replacement = replacement.replace(
                matchResult.value,
                "<b>$content</b>"
            )
        }
        return replacement
    }

    companion object {
        // TODO: Assure that match is not blanks, e.g. ____ is not a valid match
        private const val PATTERN_EMPHASIS = "_{2}(.*?)_{2}|\\*{2}(.*?)\\*{2}"

        fun create() = EmphasisPlugin()
    }
}