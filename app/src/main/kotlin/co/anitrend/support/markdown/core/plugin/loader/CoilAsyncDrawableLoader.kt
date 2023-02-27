package co.anitrend.support.markdown.core.plugin.loader

import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import co.anitrend.support.markdown.core.plugin.store.CoilStore
import co.anitrend.support.markdown.core.plugin.target.AsyncDrawableTarget
import coil.ImageLoader
import coil.request.Disposable
import coil.request.ImageRequest
import coil.target.Target
import io.noties.markwon.image.AsyncDrawable
import io.noties.markwon.image.AsyncDrawableLoader
import java.util.concurrent.atomic.AtomicBoolean

class CoilAsyncDrawableLoader(
    private val coilStore: CoilStore,
    private val imageLoader: ImageLoader,
    initialCache: Int = 8,
) : AsyncDrawableLoader() {

    private val cache: MutableMap<AsyncDrawable, Disposable> = HashMap(initialCache)

    private fun AsyncDrawable.detach() {
        with (result) {
            if (this is AnimationDrawable)
                stop()
        }

        cache.remove(this)?.also(coilStore::cancel)
    }

    private fun AsyncDrawable.attach(request: ImageRequest, loaded: AtomicBoolean) {
        // @since 4.5.1 execute can return result _before_ disposable is created,
        //  thus `execute` would finish before we put disposable in cache (and thus result is
        //  not delivered)
        // @since 4.5.1 execute can return result _before_ disposable is created,
        //  thus `execute` would finish before we put disposable in cache (and thus result is
        //  not delivered)
        val disposable = imageLoader.enqueue(request)

        if (!loaded.get()) {
            // mark flag
            loaded.set(true)
            cache[this] = disposable
        }
    }

    override fun load(drawable: AsyncDrawable) {

        val loaded = AtomicBoolean(false)
        val target: Target = AsyncDrawableTarget(drawable, loaded, cache::remove)
        val request = coilStore.load(drawable).newBuilder()
            .target(target)
            .build()
        // if flag was not set, then job is running (else - finished before we got here)
        // if flag was not set, then job is running (else - finished before we got here)
        drawable.attach(request, loaded)
    }

    override fun cancel(drawable: AsyncDrawable) {
        drawable.detach()
    }

    override fun placeholder(drawable: AsyncDrawable): Drawable? {
        return null
    }
}