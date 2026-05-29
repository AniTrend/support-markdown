package co.anitrend.support.markdown.center

import android.text.Layout
import android.text.style.AlignmentSpan
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.MarkwonVisitor
import org.commonmark.parser.Parser

/**
 * Markwon plugin for AniList's centered text syntax.
 *
 * Bridges legacy `~~~...~~~` syntax to `+++...+++` in [processMarkdown], then uses
 * [PlusDelimiterProcessor] to parse `+++...+++` into [CenterNode] AST nodes.
 * The visitor renders them with [AlignmentSpan.Standard] centered alignment.
 *
 * Note: `~~~` is consumed by commonmark-java's fenced-code block parser when it starts
 * a line, so [processMarkdown] rewrites only inline paired occurrences to `+++`
 * to avoid clobbering code fences.
 */
class CenterPlugin private constructor() : AbstractMarkwonPlugin() {

    override fun processMarkdown(markdown: String): String {
        return TILDE_CENTER_PATTERN.replace(markdown) { matchResult ->
            "+++${matchResult.groupValues[1]}+++"
        }
    }

    override fun configureParser(builder: Parser.Builder) {
        builder.customDelimiterProcessor(PlusDelimiterProcessor())
    }

    override fun configureVisitor(builder: MarkwonVisitor.Builder) {
        builder.on(CenterNode::class.java) { visitor, node ->
            val start = visitor.length()
            visitor.visitChildren(node)
            visitor.builder().setSpan(
                AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                start,
                visitor.length()
            )
        }
    }

    companion object {
        private val TILDE_CENTER_PATTERN = Regex("~~~([^\\n]+?)~~~")

        fun create() = CenterPlugin()
    }
}
