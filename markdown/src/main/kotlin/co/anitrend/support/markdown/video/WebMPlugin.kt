package co.anitrend.support.markdown.video

import androidx.annotation.VisibleForTesting
import co.anitrend.support.markdown.core.IMarkdownPlugin
import io.noties.markwon.AbstractMarkwonPlugin
import java.lang.reflect.Modifier

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
    @VisibleForTesting(otherwise = Modifier.PRIVATE)
    override val regex = Regex(
        pattern = PATTERN_WEB_M,
        option = RegexOption.IGNORE_CASE
    )

    override fun processMarkdown(markdown: String): String {
        val pattern = regex.toPattern()
        return markdown.replace(regex, "")
    }

    companion object {

        @VisibleForTesting(otherwise = Modifier.PRIVATE)
        const val PATTERN_WEB_M = "(webm).*?(\\([^)]+\\))"

        fun create() =
            WebMPlugin()
    }
}