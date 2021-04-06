package co.anitrend.support.markdown.common

/**
 * Plugin contract
 *
 * @since 0.1.0
 */
interface IMarkdownPlugin {

    /**
     * Regular expression that should be used for the implementing classing
     */
    val regex: Regex

    companion object {
        const val VIDEO_THUMBNAIL_URL = "https://via.placeholder.com/1280x720?text=Click+to+watch+video"
        const val SITE_URL = "https://anilist.co"
    }
}