package co.anitrend.support.markdown.html

import android.text.Layout
import android.text.style.AlignmentSpan
import io.noties.markwon.MarkwonConfiguration
import io.noties.markwon.MarkwonVisitor
import io.noties.markwon.RenderProps
import io.noties.markwon.SpannableBuilder
import io.noties.markwon.html.HtmlTag
import io.noties.markwon.html.MarkwonHtmlRenderer
import io.noties.markwon.html.TagHandler
import io.noties.markwon.html.tag.SimpleTagHandler

class CenterTagHandler : TagHandler() {

    override fun handle(
        visitor: MarkwonVisitor,
        renderer: MarkwonHtmlRenderer,
        tag: HtmlTag
    ) {
        if (tag.isBlock)
            visitChildren(visitor, renderer, tag.asBlock)

        SpannableBuilder.setSpans(
            visitor.builder(),
            AlignmentSpan.Standard(
                Layout.Alignment.ALIGN_CENTER
            ),
            tag.start(),
            tag.end()
        )
    }

    override fun supportedTags(): List<String> = listOf("center")
}