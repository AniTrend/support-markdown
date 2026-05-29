package co.anitrend.support.markdown.mention

import co.anitrend.support.markdown.mention.controller.MentionTextAddedController
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.MarkwonPlugin
import io.noties.markwon.core.CorePlugin

class MentionPlugin private constructor() : AbstractMarkwonPlugin() {

    private val controller = MentionTextAddedController()

    override fun configure(registry: MarkwonPlugin.Registry) {
        registry.require(CorePlugin::class.java) { core ->
            core.addOnTextAddedListener(
                OnMentionTextAddedListener.create(controller)
            )
        }
    }

    companion object {
        fun create() = MentionPlugin()
    }
}
