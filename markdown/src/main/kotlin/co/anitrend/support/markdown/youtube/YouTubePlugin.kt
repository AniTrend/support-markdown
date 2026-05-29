package co.anitrend.support.markdown.youtube

import androidx.annotation.VisibleForTesting
import io.noties.markwon.AbstractMarkwonPlugin
import java.lang.reflect.Modifier

/**
 * Handles AniList's `youtube(ID-or-URL)` custom syntax for YouTube embeds.
 *
 * During [processMarkdown] the syntax is replaced with an `<a>` tag linking to the
 * video, displaying a YouTube thumbnail image. Relies on HtmlPlugin for rendering.
 */
class YouTubePlugin private constructor() : AbstractMarkwonPlugin() {

    private val regex = Regex(
        pattern = PATTERN_YOUTUBE,
        option = RegexOption.IGNORE_CASE
    )

    @VisibleForTesting(otherwise = Modifier.PRIVATE)
    fun buildYoutubeThumbnail(link: String): String {
        val mediaIdMatchResult = PATTERN_YOUTUBE_ID.toRegex().find(link)
        if (mediaIdMatchResult != null) {
            val matcherValueGroups = mediaIdMatchResult.groupValues
            val mediaId = matcherValueGroups.last()

            return String.format(THUMBNAIL, mediaId)
        }
        return VIDEO_THUMBNAIL_URL
    }

    @VisibleForTesting(otherwise = Modifier.PRIVATE)
    fun buildYoutubeFullLink(markdown: String): String {
        return if (!markdown.contains(TRIGGER)) {
            if (markdown.contains(SHORT_LINK))
                FULL_LINK + markdown.replace(SHORT_LINK, "")
            else
                FULL_LINK + markdown
        } else markdown
    }

    override fun processMarkdown(markdown: String): String {
        var replacement = markdown
        val matches = regex.findAll(markdown)
        matches.forEach { matchResult ->
            val matchGroups = matchResult.groupValues
            val resourceUrl = buildYoutubeFullLink(matchGroups.last())
            val thumbnailUrl = buildYoutubeThumbnail(resourceUrl)

            replacement = replacement.replace(
                matchResult.value,
                """<a href="$resourceUrl"><img src="$thumbnailUrl" width="100%"/></a>"""
            )
        }
        return replacement
    }

    companion object {

        internal const val VIDEO_THUMBNAIL_URL = "https://via.placeholder.com/1280x720?text=Click+to+watch+video"

        private const val TRIGGER = "youtube"

        private const val FULL_LINK = "https://www.youtube.com/watch?v="
        private const val SHORT_LINK = "https://youtu.be/"
        private const val THUMBNAIL = "https://img.youtube.com/vi/%s/hqdefault.jpg"

        private const val PATTERN_YOUTUBE = "(youtube\\d*|\\d*px|\\d*%)\\((.+)\\)"

        private const val PATTERN_YOUTUBE_ID = "(?:https?:\\/\\/)?(?:www\\.)?youtu(?:\\.be\\/|be.com\\/\\S*(?:watch|embed)(?:(?:(?=\\/[^&\\s\\?]+(?!\\S))\\/)|(?:\\S*v=|v\\/)))([^&\\s\\?]+)"

        fun create() = YouTubePlugin()
    }
}
