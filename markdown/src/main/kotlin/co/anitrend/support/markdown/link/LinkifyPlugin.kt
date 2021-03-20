package co.anitrend.support.markdown.link

import co.anitrend.support.markdown.common.IMarkdownPlugin
import io.noties.markwon.AbstractMarkwonPlugin

/**
 * Since none of the better movement methods work we'll use this to change our normal links to hyper link text
 *
 * @since 0.12.0
 */
class LinkifyPlugin private constructor(): IMarkdownPlugin, AbstractMarkwonPlugin() {

    /**
     * Regular expression that should be used for the implementing classing
     */
    override val regex: Regex = Regex(PATTERN_LINK)

    override fun processMarkdown(markdown: String): String {
        var replacement = markdown
        val matches = regex.findAll(markdown)
        matches.forEach { matchResult ->
            val value = matchResult.value
            replacement = replacement.replace(
                value, "[$value](${value})"
            )
        }
        return replacement
    }

    companion object {
        private const val PATTERN_LINK = "((?<=\\s)|^)(http|ftp|https):\\/\\/([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:\\/~+#-]*[\\w@?^=%&\\/~+#-])?"

        fun create() = LinkifyPlugin()
    }
}