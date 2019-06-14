package co.anitrend.support.markdown.image

import android.net.Uri
import android.webkit.MimeTypeMap
import com.bumptech.glide.RequestManager
import ru.noties.markwon.image.ImageItem
import ru.noties.markwon.image.SchemeHandler
import timber.log.Timber
import java.util.concurrent.CancellationException
import java.util.concurrent.ExecutionException

internal class GlideSchemaHandler(
    private val requestManager: RequestManager
) : SchemeHandler() {

    override fun handle(raw: String, uri: Uri): ImageItem? {
        val resource = requestManager.download(raw)
        val futureTarget =resource.submit()
        return try {
            val file = futureTarget.get()
            return ImageItem(
                raw.getMimeType(),
                file.inputStream()
            )
        } catch (e: CancellationException) {
            Timber.tag(TAG).e(e)
            null
        } catch (e: ExecutionException) {
            Timber.tag(TAG).e(e)
            null
        } catch (e: InterruptedException) {
            Timber.tag(TAG).e(e)
            null
        }
    }

    private fun String.getMimeType() : String? {
        val extension = MimeTypeMap.getFileExtensionFromUrl(this)
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
    }

    companion object {
        private const val TAG = "GlideSchemaHandler"
    }
}