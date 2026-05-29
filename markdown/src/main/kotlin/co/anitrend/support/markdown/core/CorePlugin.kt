package co.anitrend.support.markdown.core

import co.anitrend.support.markdown.common.TildeDelimiterProcessor
import co.anitrend.support.markdown.html.AlignTagHandler
import co.anitrend.support.markdown.html.CenterTagHandler
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.MarkwonPlugin
import io.noties.markwon.MarkwonVisitor
import io.noties.markwon.core.MarkwonTheme
import io.noties.markwon.html.HtmlPlugin
import org.commonmark.node.SoftLineBreak
import org.commonmark.parser.Parser

class CorePlugin private constructor(
    private val autoCloseTags: Boolean
): AbstractMarkwonPlugin() {

    override fun configureTheme(builder: MarkwonTheme.Builder) {
        super.configureTheme(builder)
        builder.headingBreakHeight(0)
    }

    override fun configureParser(builder: Parser.Builder) {
        builder.customDelimiterProcessor(TildeDelimiterProcessor())
    }

    override fun configureVisitor(builder: MarkwonVisitor.Builder) {
        builder.on(SoftLineBreak::class.java) { visitor, _ ->
            visitor.ensureNewLine()
        }
    }

    override fun configure(registry: MarkwonPlugin.Registry) {
        registry.require(HtmlPlugin::class.java) { html ->
            html.addHandler(AlignTagHandler())
            html.addHandler(CenterTagHandler())
            html.allowNonClosedTags(autoCloseTags)
        }
    }

    companion object {
        fun create(
            autoCloseTags: Boolean = true,
        ) = CorePlugin(autoCloseTags)
    }
}
