package co.anitrend.support.markdown.core.plugin.target

import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import coil.request.Disposable
import coil.target.Target
import io.noties.markwon.image.AsyncDrawable
import io.noties.markwon.image.DrawableUtils
import java.util.concurrent.atomic.AtomicBoolean

class AsyncDrawableTarget(
    private val drawable: AsyncDrawable,
    private val loaded: AtomicBoolean,
    private val onRemove: (Drawable) -> Disposable?
) : Target {

    override fun onSuccess(result: Drawable) {
        // @since 4.5.1 check finished flag (result can be delivered _before_ disposable is created)
        if (onRemove(drawable) != null || !loaded.get()) {
            // mark
            loaded.set(true)
            if (drawable.isAttached) {
                DrawableUtils.applyIntrinsicBoundsIfEmpty(result)
                drawable.result = result
                with (drawable.result) {
                    if (this is AnimationDrawable)
                        start()
                }
            }
        }
    }

    override fun onStart(placeholder: Drawable?) {
        if (placeholder != null && drawable.isAttached) {
            DrawableUtils.applyIntrinsicBoundsIfEmpty(placeholder)
            drawable.result = placeholder
        }
    }

    override fun onError(error: Drawable?) {
        if (onRemove(drawable) != null) {
            if (error != null && drawable.isAttached) {
                DrawableUtils.applyIntrinsicBoundsIfEmpty(error)
                drawable.result = error
            }
        }
    }
}