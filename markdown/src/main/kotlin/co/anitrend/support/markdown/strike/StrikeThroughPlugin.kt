package co.anitrend.support.markdown.strike

import co.anitrend.support.markdown.common.IMarkdownPlugin
import io.noties.markwon.AbstractMarkwonPlugin

class StrikeThroughPlugin private constructor() : IMarkdownPlugin, AbstractMarkwonPlugin() {

    /**
     * Regular expression that should be used for the implementing classing
     */
    override val regex by lazy(LazyThreadSafetyMode.NONE) {
        Regex(
            pattern = PATTERN_STRIKE_THROUGH,
            option = RegexOption.MULTILINE
        )
    }

    override fun processMarkdown(markdown: String): String {
        var replacement = markdown
        val matches = regex.findAll(markdown)
        matches.forEach { matchResult ->
            val contentMatch = matchResult.groupValues.last()

            replacement = replacement.replace(
                matchResult.value,
                "<s>$contentMatch</s>"
            )
        }
        return replacement
    }

    companion object {
        private const val PATTERN_STRIKE_THROUGH= "~{2}([^\\\\]*?)~{2}"
        fun create() = StrikeThroughPlugin()
    }
}