package co.anitrend.support.markdown.core

import android.util.Log
import co.anitrend.support.markdown.html.AlignTagHandler
import co.anitrend.support.markdown.html.CenterTagHandler
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.MarkwonPlugin
import io.noties.markwon.MarkwonVisitor
import io.noties.markwon.core.MarkwonTheme
import io.noties.markwon.html.HtmlEmptyTagReplacement
import io.noties.markwon.html.HtmlPlugin
import io.noties.markwon.html.HtmlTag
import org.commonmark.node.SoftLineBreak

/**
 * Provides basic plugin configuration
 *
 * @since 0.1.0
 */
class CorePlugin private constructor(): AbstractMarkwonPlugin() {

    private val regex by lazy(LazyThreadSafetyMode.NONE) {
        Regex(
            "\\[(.*?)\\]\\((.*?)\\)",
            options = setOf(
                RegexOption.IGNORE_CASE,
                RegexOption.MULTILINE
            )
        )
    }

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
            html.allowNonClosedTags(true)
            html.emptyTagReplacement(
                object : HtmlEmptyTagReplacement() {
                    /**
                     * @return replacement for supplied startTag or null if no replacement should occur (which will
                     * lead to `Inline` tag have start &amp; end the same value, thus not applicable for applying a Span)
                     */
                    override fun replace(tag: HtmlTag): String? {
                        Log.i("CorePlugin","Empty tag $tag")
                        when {
                            tag.isBlock -> {
                                val block = tag.asBlock
                                // edge case where a user may use multiple <center> tags without closing them
                                if (block.parent()?.isBlock == true && block.parent()?.isClosed == false) {
                                    val parent = block.parent()
                                }
                            }
                        }
                        return super.replace(tag)
                    }
                }
            )
        }
    }

    override fun processMarkdown(markdown: String): String {
        var replacement = markdown
        val matches = regex.findAll(markdown)
        matches.forEach { matchResult ->
            val value = matchResult.value
            val tag = matchResult.groupValues[1]
            val src = matchResult.groupValues.last()

            replacement = replacement.replace(
                value,
                """<a href="$src">${tag}</a>"""
            )
        }
        return replacement
    }

    companion object {
        fun create() = CorePlugin()
    }
}