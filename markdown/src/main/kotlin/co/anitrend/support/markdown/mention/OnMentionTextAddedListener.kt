package co.anitrend.support.markdown.mention

import co.anitrend.support.markdown.common.IMarkdownPlugin
import co.anitrend.support.markdown.mention.controller.MentionTextAddedController
import io.noties.markwon.MarkwonVisitor
import io.noties.markwon.SpannableBuilder
import io.noties.markwon.core.CorePlugin
import io.noties.markwon.core.CoreProps
import org.commonmark.node.Link

class OnMentionTextAddedListener private constructor(): CorePlugin.OnTextAddedListener,
    IMarkdownPlugin {

    /**
     * Regular expression that should be used for the implementing classing
     */
    override val regex: Regex = Regex("")

    private val controller = MentionTextAddedController()

    private fun MarkwonVisitor.setLinkSpan(source: String, start: Int, end: Int) {
        val configuration = configuration()
        val renderProps = renderProps()

        CoreProps.LINK_DESTINATION.set(renderProps, source)

        val link = configuration.spansFactory().require(Link::class.java)
        val spans = link.getSpans(configuration, renderProps)

        SpannableBuilder.setSpans(builder(), spans, start, end)
    }

    /**
     * Will be called when new text is added to resulting [SpannableBuilder].
     * Please note that only text represented by [Text] node will trigger this callback
     * (text inside code and code-blocks won\'t trigger it).
     *
     *
     * Please note that if you wish to add spans you must use `start` parameter
     * in order to place spans correctly (`start` represents the index at which `text`
     * was added). So, to set a span for the whole length of the text added one should use:
     *
     *
     * `visitor.builder().setSpan(new MySpan(), start, start + text.length(), 0);
    ` *
     *
     * @param visitor [MarkwonVisitor]
     * @param text    literal that had been added
     * @param start   index in `visitor` as which text had been added
     * @see .addOnTextAddedListener
     */
    override fun onTextAdded(visitor: MarkwonVisitor, text: String, start: Int) {
        val matches = controller.findAllMatches(text)
        matches.forEach { matchResult ->
            val content = controller.getContent(matchResult)
            val mention = controller.getMention(content)
            val url = controller.asUserUrl(mention)
            val matchRange = matchResult.range
            visitor.setLinkSpan(
                url,
                start + matchRange.first,
                start + matchRange.last + 1
            )
        }
    }

    companion object {
        fun create() = OnMentionTextAddedListener()
    }
}