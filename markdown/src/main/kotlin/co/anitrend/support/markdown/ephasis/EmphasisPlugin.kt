package co.anitrend.support.markdown.ephasis

import co.anitrend.support.markdown.common.IMarkdownPlugin
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.editor.MarkwonEditorUtils

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
    override val regex by lazy(LazyThreadSafetyMode.NONE) {
        Regex("")
    }

    override fun processMarkdown(markdown: String): String {
        val replacement = mutableListOf<String>()

        markdown.split("\n").forEach {
            var line = it

            while (true) {
                val match = MarkwonEditorUtils.findDelimited(line, 0, "**", "__") ?: break

                var temporary = line.substring(0, match.start())
                temporary += "<b>${line.substring(match.start() + 2, match.end() - 2)}</b>"
                temporary += line.substring(match.end())

                line = temporary
            }

            replacement.add(line)
        }

        return replacement.joinToString("\n")
    }

    companion object {
        fun create() = EmphasisPlugin()
    }
}