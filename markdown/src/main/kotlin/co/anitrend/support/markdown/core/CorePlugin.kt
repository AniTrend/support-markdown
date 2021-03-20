package co.anitrend.support.markdown.core

import co.anitrend.support.markdown.common.IMarkdownPlugin
import co.anitrend.support.markdown.html.AlignTagHandler
import co.anitrend.support.markdown.html.CenterTagHandler
import co.anitrend.support.markdown.mention.OnMentionTextAddedListener
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.MarkwonPlugin
import io.noties.markwon.MarkwonVisitor
import io.noties.markwon.core.CorePlugin
import io.noties.markwon.core.MarkwonTheme
import io.noties.markwon.html.HtmlPlugin
import org.commonmark.node.SoftLineBreak

/**
 * Provides basic plugin configuration
 *
 * @since 0.1.0
 */
class CorePlugin private constructor(): IMarkdownPlugin, AbstractMarkwonPlugin() {

    /**
     * Regular expression that should be used for the implementing classing
     */
    override val regex: Regex = Regex("-{3}|\\*{3}|_{3,}")

    override fun configureTheme(builder: MarkwonTheme.Builder) {
        super.configureTheme(builder)
        builder.headingBreakHeight(0)
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
        }
        registry.require(CorePlugin::class.java) { core ->
            core.addOnTextAddedListener(
                OnMentionTextAddedListener.create()
            )
        }
    }

    override fun processMarkdown(markdown: String): String {
        return regex.replace(markdown, "<hr />")
    }

    companion object {
        fun create() = CorePlugin()
    }
}