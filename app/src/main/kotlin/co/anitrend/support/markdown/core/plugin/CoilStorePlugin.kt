package co.anitrend.support.markdown.core.plugin

import co.anitrend.support.markdown.sample.R
import coil.request.Disposable
import coil.request.ImageRequest
import coil.size.OriginalSize
import coil.size.PixelSize
import io.noties.markwon.image.AsyncDrawable
import io.noties.markwon.image.coil.CoilImagesPlugin

/**
 * Taken from anitrend
 */
internal class CoilStorePlugin private constructor(
    private val requestBuilder: ImageRequest.Builder
) : CoilImagesPlugin.CoilStore {

    override fun load(drawable: AsyncDrawable): ImageRequest {
        val builder = requestBuilder
            .crossfade(true)
            .data(drawable.destination)
            .error(R.drawable.ic_emoji_cry)
            .placeholder(R.drawable.ic_emoji_sweat)

        val width = drawable.imageSize?.width
        val height = drawable.imageSize?.height

        val size = if (width?.value != null && height?.value != null) {
            PixelSize(
                width = width.value.toInt(),
                height = height.value.toInt()
            )
        } else OriginalSize

        builder.size(size)

        return builder.build()
    }

    override fun cancel(disposable: Disposable) {
        if (!disposable.isDisposed)
            disposable.dispose()
    }

    companion object {
        fun create(
            requestBuilder: ImageRequest.Builder
        ) = CoilStorePlugin(requestBuilder)
    }
}