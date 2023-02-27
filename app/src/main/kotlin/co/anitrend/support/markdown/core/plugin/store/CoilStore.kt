package co.anitrend.support.markdown.core.plugin.store

import coil.request.Disposable
import coil.request.ImageRequest
import io.noties.markwon.image.AsyncDrawable

interface CoilStore {
    fun load(drawable: AsyncDrawable): ImageRequest
    fun cancel(disposable: Disposable) {
        if (!disposable.isDisposed)
            disposable.dispose()
    }
}