package co.anitrend.support.markdown.heading

import io.noties.markwon.AbstractMarkwonPlugin

/**
 * Placeholder plugin for headings — setext (`===` / `---`) and ATX (`# `) headings
 * are already handled by commonmark-java's native parser and Markwon's core rendering.
 *
 * Retained as a no-op wrapper for backward compatibility in the plugin chain.
 */
class HeadingPlugin private constructor() : AbstractMarkwonPlugin() {

    companion object {
        fun create() = HeadingPlugin()
    }
}
