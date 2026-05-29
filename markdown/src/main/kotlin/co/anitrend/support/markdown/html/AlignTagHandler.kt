package co.anitrend.support.markdown.html

import android.text.Layout
import android.text.style.AlignmentSpan
import io.noties.markwon.MarkwonConfiguration
import io.noties.markwon.RenderProps
import io.noties.markwon.html.HtmlTag
import io.noties.markwon.html.tag.SimpleTagHandler

/**
 * Handles the custom `<align>` HTML tag for block-alignment in markdown output.
 *
 * Supports three alignment attributes:
 * - `center` → [Layout.Alignment.ALIGN_CENTER]
 * - `end` → [Layout.Alignment.ALIGN_OPPOSITE]
 * - default (no recognized attribute) → [Layout.Alignment.ALIGN_NORMAL]
 */
class AlignTagHandler : SimpleTagHandler() {

    override fun getSpans(
        configuration: MarkwonConfiguration,
        renderProps: RenderProps,
        tag: HtmlTag
    ): Any {
        val alignment: Layout.Alignment = when {
            tag.attributes().containsKey("center") -> Layout.Alignment.ALIGN_CENTER
            tag.attributes().containsKey("end") -> Layout.Alignment.ALIGN_OPPOSITE
            else -> Layout.Alignment.ALIGN_NORMAL
        }
        return AlignmentSpan.Standard(alignment)
    }

    override fun supportedTags() = listOf("align")
}