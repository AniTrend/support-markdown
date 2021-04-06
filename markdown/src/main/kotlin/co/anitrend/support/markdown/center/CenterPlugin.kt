package co.anitrend.support.markdown.center

import androidx.annotation.VisibleForTesting
import co.anitrend.support.markdown.common.IMarkdownPlugin
import co.anitrend.support.markdown.link.LinkifyPlugin
import io.noties.markwon.AbstractMarkwonPlugin
import java.lang.reflect.Modifier

/**
 * To center-align text, surround it with either __~~~...~~~__ or __+++...+++__
 * you can also use simple html center block __`<center>...</center>`__
 *
 * @since 0.1.0
 */
class CenterPlugin private constructor(): IMarkdownPlugin, AbstractMarkwonPlugin() {

    /**
     * Regular expression that should be used for the implementing classing
     */
    override val regex = Regex(
        pattern = PATTERN_CENTER,
        option = RegexOption.MULTILINE
    )

    private val linkifyPlugin = LinkifyPlugin.create()

    override fun processMarkdown(markdown: String): String {
        var replacement = markdown
        val matches = regex.findAll(markdown)
        matches.forEach { matchResult ->
            val contentMatch = matchResult.groupValues.last()
            val linked = linkifyPlugin.processMarkdown(contentMatch)
            replacement = replacement.replace(
                matchResult.value,
                "<align center>$linked</align>"
            )
        }
        return replacement
    }

    companion object {

        @VisibleForTesting(otherwise = Modifier.PRIVATE)
        const val PATTERN_CENTER = "(?:~{3}|\\+{3})([^\\\\]*?)(?:~{3}|\\+{3})"

        fun create() =
            CenterPlugin()
    }
}