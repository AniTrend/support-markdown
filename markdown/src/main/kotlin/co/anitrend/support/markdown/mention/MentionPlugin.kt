package co.anitrend.support.markdown.mention

import co.anitrend.support.markdown.mention.controller.MentionTextAddedController
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.MarkwonPlugin
import io.noties.markwon.core.CorePlugin

class MentionPlugin private constructor(): AbstractMarkwonPlugin() {

    private val controller = MentionTextAddedController()

    override fun configure(registry: MarkwonPlugin.Registry) {
        // wont work for AL mixed content style
        //registry.require(CorePlugin::class.java) { core ->
        //    core.addOnTextAddedListener(
        //        OnMentionTextAddedListener.create(controller)
        //    )
        //}
    }

    override fun processMarkdown(markdown: String): String {
        var replacement = markdown
        val matches = controller.findAllMatches(markdown)
        matches.forEach { matchResult ->
            val content = controller.getContent(matchResult)
            val mention = controller.getMention(content)
            val url = controller.asUserUrl(mention)
            replacement = replacement.replace(
                matchResult.value,
                """<a href="$url">$content</a>"""
            )
        }
        return replacement
    }

    companion object {
        fun create() = MentionPlugin()
    }
}