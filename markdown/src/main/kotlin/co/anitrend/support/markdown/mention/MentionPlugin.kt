package co.anitrend.support.markdown.mention

import co.anitrend.support.markdown.mention.controller.MentionTextAddedController
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.MarkwonPlugin
import io.noties.markwon.core.CorePlugin

/**
 * Handles `@username` mentions by converting them to profile links.
 *
 * Uses [CorePlugin.addOnTextAddedListener] to intercept text nodes during
 * Markwon's visitor walk and apply a [Link] span pointing to the user's
 * AniList profile page.
 */
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
