package co.anitrend.support.markdown.image

import com.bumptech.glide.Glide
import com.bumptech.glide.GlideContext
import com.bumptech.glide.RequestManager
import ru.noties.markwon.AbstractMarkwonPlugin
import ru.noties.markwon.image.AsyncDrawableLoader
import ru.noties.markwon.image.ImagesPlugin
import ru.noties.markwon.image.network.NetworkSchemeHandler
import ru.noties.markwon.priority.Priority

/**
 *
 * @since 0.1.0
 */
class GlideImagePlugin private constructor(
    private val requestManager: RequestManager
) : AbstractMarkwonPlugin() {

    override fun configureImages(builder: AsyncDrawableLoader.Builder) {
        builder.addSchemeHandler(
            listOf(
                NetworkSchemeHandler.SCHEME_HTTP,
                NetworkSchemeHandler.SCHEME_HTTPS
            ),
            GlideSchemaHandler(requestManager)
        )
    }

    override fun priority() =
         Priority.after(ImagesPlugin::class.java)

    companion object {
        fun create(glideContext: GlideContext) =
            GlideImagePlugin(
                Glide.with(glideContext)
            )
    }
}