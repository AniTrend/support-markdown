package co.anitrend.support.markdown.core

import ru.noties.markwon.AbstractMarkwonPlugin

/**
 *
 * @since 0.1.0
 */
class MarkdownPlugin private constructor(): AbstractMarkwonPlugin() {

    companion object {
        fun create() =
            MarkdownPlugin()
    }
}