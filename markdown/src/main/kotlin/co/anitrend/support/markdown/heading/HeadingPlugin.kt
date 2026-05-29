package co.anitrend.support.markdown.heading

import io.noties.markwon.AbstractMarkwonPlugin

class HeadingPlugin private constructor() : AbstractMarkwonPlugin() {

    companion object {
        fun create() = HeadingPlugin()
    }
}
