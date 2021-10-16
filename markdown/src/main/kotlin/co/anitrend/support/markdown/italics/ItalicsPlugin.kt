package co.anitrend.support.markdown.italics

import co.anitrend.support.markdown.common.IMarkdownPlugin
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.editor.MarkwonEditorUtils


/**
 * Italics plugin
 */
class ItalicsPlugin private constructor(): IMarkdownPlugin, AbstractMarkwonPlugin() {

    /**
     * Regular expression that should be used for the implementing classing
     */
    override val regex by lazy(LazyThreadSafetyMode.NONE) {
        Regex("")
    }

    override fun processMarkdown(markdown: String): String {
        val replacement = mutableListOf<String>()

        markdown.split("\n").forEach {
            var start = 0
            var line = it

            while (true) {
                val match = MarkwonEditorUtils.findDelimited(line, start, "*") ?: break
                if (match.end() - match.start() == 2) {
                    start = match.end()
                    continue
                }

                var temporary = line.substring(0, match.start())
                temporary += "<i>${line.substring(match.start() + 1, match.end() - 1)}</i>"
                temporary += line.substring(match.end())

                line = temporary
            }

            replacement.add(line)
        }

        return replacement.joinToString("\n")
    }

    companion object {
        fun create() = ItalicsPlugin()
    }
}