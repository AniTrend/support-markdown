package co.anitrend.support.markdown.core.plugin.store

import android.annotation.SuppressLint
import co.anitrend.support.markdown.sample.R
import coil.request.Disposable
import coil.request.ImageRequest
import coil.size.Size
import io.noties.markwon.image.AsyncDrawable

/**
 * Taken from anitrend
 */
class CoilStorePlugin private constructor(
    private val requestBuilder: ImageRequest.Builder
) : CoilStore {

    @SuppressLint("NewApi")
    override fun load(drawable: AsyncDrawable): ImageRequest {
        val builder = requestBuilder
            .crossfade(true)
            .data(drawable.destination)
            .error(R.drawable.ic_emoji_cry)
            .placeholder(R.drawable.ic_emoji_sweat)

        val width = drawable.imageSize?.width
        val height = drawable.imageSize?.height

        val size = if (width?.value != null && height?.value != null) {
            Size(
                width = width.value.toInt(),
                height = height.value.toInt()
            )
        } else Size.ORIGINAL

        builder.size(size)

        return builder.build()
    }

    companion object {
        fun create(
            requestBuilder: ImageRequest.Builder
        ) = CoilStorePlugin(requestBuilder)
    }
}