package co.anitrend.support.markdown.style

import androidx.annotation.VisibleForTesting
import co.anitrend.support.markdown.core.IMarkdownPlugin
import io.noties.markwon.AbstractMarkwonPlugin
import java.lang.reflect.Modifier

/**
 * To center-align text, surround it with either __~~~...~~~__ or __`<center>...</center>`__
 *
 * @since 0.1.0
 */
class CenterPlugin private constructor(): IMarkdownPlugin, AbstractMarkwonPlugin() {

    /**
     * Regular expression that should be used for the implementing classing
     */
    @VisibleForTesting(otherwise = Modifier.PRIVATE)
    override val regex = Regex(
        pattern = PATTERN_CENTER,
        option = RegexOption.IGNORE_CASE
    )

    override fun processMarkdown(markdown: String): String {
        val pattern = regex.toPattern()
        return markdown.replace(regex, "<center></center>")
    }

    companion object {

        @VisibleForTesting(otherwise = Modifier.PRIVATE)
        const val PATTERN_CENTER = "~~~(.*?)~~~"

        fun create() =
            CenterPlugin()
    }
}