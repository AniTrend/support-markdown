package co.anitrend.support.markdown.strike

import android.text.style.StrikethroughSpan
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.MarkwonVisitor

class StrikeThroughPlugin private constructor() : AbstractMarkwonPlugin() {

    override fun configureVisitor(builder: MarkwonVisitor.Builder) {
        builder.on(StrikeThroughNode::class.java) { visitor, node ->
            val start = visitor.length()
            visitor.visitChildren(node)
            visitor.builder().setSpan(StrikethroughSpan(), start, visitor.length())
        }
    }

    companion object {
        fun create() = StrikeThroughPlugin()
    }
}
