package co.anitrend.support.markdown.spoiler

import android.graphics.Color
import androidx.annotation.ColorInt
import co.anitrend.support.markdown.spoiler.node.SpoilerNode
import co.anitrend.support.markdown.spoiler.span.SpoilerClickableSpan
import co.anitrend.support.markdown.spoiler.span.SpoilerHideSpan
import co.anitrend.support.markdown.spoiler.span.SpoilerSpan
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.MarkwonVisitor

class SpoilerPlugin private constructor(
    @ColorInt private val textColor: Int,
    @ColorInt private val backgroundColor: Int
) : AbstractMarkwonPlugin() {

    override fun configureVisitor(builder: MarkwonVisitor.Builder) {
        builder.on(SpoilerNode::class.java) { visitor, node ->
            val start = visitor.length()
            visitor.visitChildren(node)
            val end = visitor.length()

            val spoilerSpan = SpoilerSpan(textColor, backgroundColor)
            val clickableSpan = SpoilerClickableSpan(spoilerSpan)
            val hideSpan = SpoilerHideSpan()

            visitor.builder().setSpan(spoilerSpan, start, end)
            visitor.builder().setSpan(clickableSpan, start, end)
            visitor.builder().setSpan(hideSpan, start, end)
        }
    }

    companion object {
        fun create(
            @ColorInt textColor: Int = Color.BLACK,
            @ColorInt backgroundColor: Int = Color.WHITE
        ) = SpoilerPlugin(textColor, backgroundColor)
    }
}
