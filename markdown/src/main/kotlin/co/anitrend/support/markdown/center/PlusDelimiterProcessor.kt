package co.anitrend.support.markdown.center

import org.commonmark.node.Node
import org.commonmark.node.Text
import org.commonmark.parser.delimiter.DelimiterProcessor
import org.commonmark.parser.delimiter.DelimiterRun

/**
 * DelimiterProcessor for centered text via `+++...+++`.
 *
 * Requires exactly 3 `+` characters for both opening and closing runs.
 * This is the canonical delimiter for center-alignment; the legacy `~~~` syntax
 * is bridged to `+++` by [CenterPlugin.processMarkdown].
 */
internal class PlusDelimiterProcessor : DelimiterProcessor {

    override fun getOpeningCharacter(): Char = '+'
    override fun getClosingCharacter(): Char = '+'
    override fun getMinLength(): Int = 3

    /** Returns 3 when both runs are length 3 and positioned to open/close, else 0. */
    override fun getDelimiterUse(openingRun: DelimiterRun, closingRun: DelimiterRun): Int {
        if (openingRun.length() != 3 || closingRun.length() != 3) return 0
        if (!openingRun.canOpen() || !closingRun.canClose()) return 0
        return 3
    }

    /** Wraps all nodes between [opener] and [closer] into a [CenterNode], then unlinks the delimiter texts. */
    override fun process(opener: Text, closer: Text, delimiterUse: Int) {
        val centerNode = CenterNode()
        var child: Node? = opener.next
        while (child != null && child !== closer) {
            val next = child.next
            centerNode.appendChild(child)
            child = next
        }
        opener.insertAfter(centerNode)
        opener.unlink()
        closer.unlink()
    }
}
