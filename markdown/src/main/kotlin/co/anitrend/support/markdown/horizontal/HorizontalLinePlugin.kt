package co.anitrend.support.markdown.horizontal

import io.noties.markwon.AbstractMarkwonPlugin

class HorizontalLinePlugin private constructor() : AbstractMarkwonPlugin() {

    companion object {
        fun create() = HorizontalLinePlugin()
    }
}
