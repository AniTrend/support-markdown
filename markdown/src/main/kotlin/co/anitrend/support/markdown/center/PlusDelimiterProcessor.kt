package co.anitrend.support.markdown.center

import org.commonmark.node.Node
import org.commonmark.node.Text
import org.commonmark.parser.delimiter.DelimiterProcessor
import org.commonmark.parser.delimiter.DelimiterRun

internal class PlusDelimiterProcessor : DelimiterProcessor {

    override fun getOpeningCharacter(): Char = '+'
    override fun getClosingCharacter(): Char = '+'
    override fun getMinLength(): Int = 3

    override fun getDelimiterUse(openingRun: DelimiterRun, closingRun: DelimiterRun): Int {
        if (openingRun.length() != 3 || closingRun.length() != 3) return 0
        if (!openingRun.canOpen() || !closingRun.canClose()) return 0
        return 3
    }

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
