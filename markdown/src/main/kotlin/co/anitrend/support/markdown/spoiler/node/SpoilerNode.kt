package co.anitrend.support.markdown.spoiler.node

import co.anitrend.support.markdown.spoiler.span.SpoilerClickableSpan
import io.noties.markwon.MarkwonVisitor
import org.commonmark.node.Text

class SpoilerNode(
    private val regex: Regex,
    private val spoilerSpan: SpoilerClickableSpan
) : MarkwonVisitor.NodeVisitor<Text> {

    private fun isSpoiler(text: String): Boolean {
        return regex.containsMatchIn(text)
    }

    override fun visit(visitor: MarkwonVisitor, text: Text) {
        if (isSpoiler(text.literal))
            visitor.setSpans(0, spoilerSpan)

    }
}