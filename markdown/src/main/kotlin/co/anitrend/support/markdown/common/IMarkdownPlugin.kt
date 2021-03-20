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
        const val VIDEO_THUMBNAIL_URL = "http://placehold.it/1280x720?text=No+Preview+Available"
        const val SITE_URL = "https://anilist.co"
    }
}