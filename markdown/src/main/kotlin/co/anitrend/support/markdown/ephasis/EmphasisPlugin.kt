package co.anitrend.support.markdown.ephasis

import android.text.style.StyleSpan
import android.graphics.Typeface
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.MarkwonVisitor
import org.commonmark.node.StrongEmphasis

class EmphasisPlugin : AbstractMarkwonPlugin() {

    override fun configureVisitor(builder: MarkwonVisitor.Builder) {
        builder.on(StrongEmphasis::class.java) { visitor, node ->
            val start = visitor.length()
            visitor.visitChildren(node)
            visitor.builder().setSpan(StyleSpan(Typeface.BOLD), start, visitor.length())
        }
    }

    companion object {
        fun create() = EmphasisPlugin()
    }
}
