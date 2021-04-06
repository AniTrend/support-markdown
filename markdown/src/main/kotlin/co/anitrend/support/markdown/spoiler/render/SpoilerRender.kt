package co.anitrend.support.markdown.spoiler.render

import androidx.annotation.ColorInt
import co.anitrend.support.markdown.spoiler.span.SpoilerSpan
import io.noties.markwon.MarkwonConfiguration
import io.noties.markwon.RenderProps
import io.noties.markwon.SpanFactory
import org.commonmark.node.Text

class SpoilerRender(
    @ColorInt private val textColor: Int,
    @ColorInt private val backgroundColor: Int
) : SpanFactory {
    override fun getSpans(configuration: MarkwonConfiguration, props: RenderProps): Any {
        configuration.spansFactory().require(Text::class.java)
        return SpoilerSpan(textColor, backgroundColor)
    }
}