package co.anitrend.support.markdown.webm

import co.anitrend.support.markdown.common.IMarkdownPlugin
import co.anitrend.support.markdown.common.IMarkdownPlugin.Companion.VIDEO_THUMBNAIL_URL
import io.noties.markwon.AbstractMarkwonPlugin

/**
 * Surround the URL with __webm(https://files.kiniro.uk/video/sonic.webm)__
 *
 * Note that, despite the name, _any_ audio or video file will work - but may not actually be supported by all browsers.
 *
 * PS: Does not interact well with code blocks.
 *
 * @since 0.1.0
 */
class WebMPlugin private constructor(): IMarkdownPlugin, AbstractMarkwonPlugin() {

    /**
     * Regular expression that should be used for the implementing classing
     */
    override val regex = Regex(
        pattern = PATTERN_WEB_M,
        option = RegexOption.IGNORE_CASE
    )

    override fun processMarkdown(markdown: String): String {
        var replacement = markdown
        val matches = regex.findAll(markdown)
        matches.forEach { matchResult ->
            val matchGroups = matchResult.groupValues
            val resourceUrl = matchGroups.last()

            replacement = replacement.replace(
                matchResult.value,
                """<a href="$resourceUrl"><img src="$VIDEO_THUMBNAIL_URL" /></a>"""
            )
        }
        return replacement
    }

    companion object {

        private const val PATTERN_WEB_M = "(webm\\d*|\\d*px|\\d*%)\\((.+)\\)"

        fun create() =
            WebMPlugin()
    }
}