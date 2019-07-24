package co.anitrend.support.markdown.core

import io.noties.markwon.AbstractMarkwonPlugin

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