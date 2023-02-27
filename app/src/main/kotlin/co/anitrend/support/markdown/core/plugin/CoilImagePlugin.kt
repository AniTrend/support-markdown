package co.anitrend.support.markdown.core.plugin

import android.text.Spanned
import android.widget.TextView
import co.anitrend.support.markdown.core.plugin.loader.CoilAsyncDrawableLoader
import co.anitrend.support.markdown.core.plugin.store.CoilStore
import coil.ImageLoader
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.MarkwonConfiguration
import io.noties.markwon.MarkwonSpansFactory
import io.noties.markwon.image.AsyncDrawableScheduler
import io.noties.markwon.image.ImageSpanFactory
import org.commonmark.node.Image

class CoilImagePlugin(coilStore: CoilStore, imageLoader: ImageLoader) : AbstractMarkwonPlugin() {

    private val coilAsyncDrawableLoader =
        CoilAsyncDrawableLoader(coilStore, imageLoader)

    override fun configureSpansFactory(builder: MarkwonSpansFactory.Builder) {
        builder.setFactory(Image::class.java, ImageSpanFactory())
    }

    override fun configureConfiguration(builder: MarkwonConfiguration.Builder) {
        builder.asyncDrawableLoader(coilAsyncDrawableLoader)
    }

    override fun beforeSetText(textView: TextView, markdown: Spanned) {
        AsyncDrawableScheduler.unschedule(textView)
    }

    override fun afterSetText(textView: TextView) {
        AsyncDrawableScheduler.schedule(textView)
    }

    companion object {
        fun create(
            coilStore: CoilStore,
            imageLoader: ImageLoader
        ) = CoilImagePlugin(coilStore, imageLoader)
    }
}