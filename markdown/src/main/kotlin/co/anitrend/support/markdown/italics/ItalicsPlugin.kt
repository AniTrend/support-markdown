package co.anitrend.support.markdown.italics

import android.text.style.StyleSpan
import android.graphics.Typeface
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.MarkwonVisitor
import org.commonmark.node.Emphasis

/**
 * Renders commonmark-java [Emphasis] nodes (from `*text*` or `_text_`) as italic text.
 *
 * This replaces the old regex-based italic handling by hooking into the native
 * commonmark-java `Emphasis` AST node and applying a [StyleSpan] with [Typeface.ITALIC].
 */
class ItalicsPlugin private constructor() : AbstractMarkwonPlugin() {

    override fun configureVisitor(builder: MarkwonVisitor.Builder) {
        builder.on(Emphasis::class.java) { visitor, node ->
            val start = visitor.length()
            visitor.visitChildren(node)
            visitor.builder().setSpan(StyleSpan(Typeface.ITALIC), start, visitor.length())
        }
    }

    companion object {
        fun create() = ItalicsPlugin()
    }
}
