package co.anitrend.support.markdown.youtube

import androidx.annotation.VisibleForTesting
import co.anitrend.support.markdown.common.IMarkdownPlugin
import io.noties.markwon.AbstractMarkwonPlugin
import java.lang.reflect.Modifier

/**
 * Surround the URL with __youtube(https://www.youtube.com/watch?v=D0q0QeQbw9U)__:
 *
 * Note that only the __D0q0QeQbw9U__ part is actually required:
 *
 * youtube(D0q0QeQbw9U)
 *
 * Again, this is _always_ converted - even in code blocks.
 *
 * @since 0.1.0
 */
class YouTubePlugin private constructor(): AbstractMarkwonPlugin(), IMarkdownPlugin {

    /**
     * Regular expression that should be used for the implementing classing
     */
    override val regex = Regex(
        pattern = PATTERN_YOUTUBE,
        option = RegexOption.IGNORE_CASE
    )

    /**
     * Creates a youtube thumbnail from a given youtube url
     *
     * @param link full youtube link derived from [FULL_LINK]
     */
    @VisibleForTesting(otherwise = Modifier.PRIVATE)
    fun buildYoutubeThumbnail(link: String): String {
        val mediaIdMatchResult = PATTERN_YOUTUBE_ID.toRegex().find(link)
        if (mediaIdMatchResult != null) {
            val matcherValueGroups = mediaIdMatchResult.groupValues
            val mediaId = matcherValueGroups.last()

            return String.format(THUMBNAIL, mediaId)
        }
        return IMarkdownPlugin.NO_THUMBNAIL
    }

    /**
     * Creates a standard youtube URL from the flavored markdown or short link
     *
     * @param markdown flavored markdown selector for youtube
     * @see PATTERN_YOUTUBE
     */
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

        private const val TRIGGER = "youtube"

        private const val FULL_LINK = "https://www.youtube.com/watch?v="
        private const val SHORT_LINK = "https://youtu.be/"
        private const val THUMBNAIL = "https://img.youtube.com/vi/%s/hqdefault.jpg"

        private const val PATTERN_YOUTUBE = "(youtube\\d*|\\d*px|\\d*%)\\((.+)\\)"

        private const val PATTERN_YOUTUBE_ID = "(?:https?:\\/\\/)?(?:www\\.)?youtu(?:\\.be\\/|be.com\\/\\S*(?:watch|embed)(?:(?:(?=\\/[^&\\s\\?]+(?!\\S))\\/)|(?:\\S*v=|v\\/)))([^&\\s\\?]+)"

        fun create() =
            YouTubePlugin()
    }
}