package co.anitrend.support.markdown.ephasis

import androidx.annotation.VisibleForTesting
import co.anitrend.support.markdown.common.IMarkdownPlugin
import io.noties.markwon.AbstractMarkwonPlugin
import java.lang.reflect.Modifier

/**
 *  Put two * or _ characters either side of the text:
 * `__hello__` becomes **hello**
 *  `**world**` becomes **world**
 *
 * You can also use the HTML `<b>...</b>` or `<strong>...</strong>` tags:
 * `<b>hello</b>` becomes **hello**
 * `<strong>hello</strong>` becomes **hello**
 *
 * This can be combined with italics like so: `_**hello world**_`
 *
 * @since 0.12.0
 */
class EmphasisPlugin : IMarkdownPlugin, AbstractMarkwonPlugin() {

    /**
     * Regular expression that should be used for the implementing classing
     */
    override val regex = Regex(
        pattern = PATTERN_EMPHASIS,
        option = RegexOption.IGNORE_CASE
    )

    override fun processMarkdown(markdown: String): String {
        var replacement = markdown
        val matches = regex.findAll(markdown)
        matches.forEach { matchResult ->
            val content = matchResult.groupValues.first()

            replacement = replacement.replace(
                matchResult.value,
                "<b>$content</b>"
            )
        }
        return replacement
    }

    companion object {
        private const val PATTERN_EMPHASIS = "__([\\s\\S]*?)__"

        fun create() = EmphasisPlugin()
    }
}