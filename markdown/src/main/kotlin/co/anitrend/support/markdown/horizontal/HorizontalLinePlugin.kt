package co.anitrend.support.markdown.horizontal

import io.noties.markwon.AbstractMarkwonPlugin

/**
 * Placeholder plugin for horizontal rules — `---`, `***`, `___` thematic breaks
 * are already handled by commonmark-java's native parser and Markwon's core rendering.
 *
 * Retained as a no-op wrapper for backward compatibility in the plugin chain.
 */
class HorizontalLinePlugin private constructor() : AbstractMarkwonPlugin() {

    companion object {
        fun create() = HorizontalLinePlugin()
    }
}
