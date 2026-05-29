package co.anitrend.support.markdown.common

import co.anitrend.support.markdown.center.CenterNode
import co.anitrend.support.markdown.spoiler.node.SpoilerNode
import co.anitrend.support.markdown.strike.StrikeThroughNode
import org.commonmark.node.Node
import org.commonmark.node.Text
import org.commonmark.parser.delimiter.DelimiterProcessor
import org.commonmark.parser.delimiter.DelimiterRun

/**
 * Shared `~` delimiter processor that dispatches to three distinct AST nodes based on run length:
 *
 * - Length 1: spoiler (`~!...!~`) via [handleSpoiler] — requires leading `!` and trailing `!`
 * - Length 2: strikethrough (`~~...~~`) via [handleStrikethrough]
 * - Length 3: center (`~~~...~~~`) via [handleCenter] (note: block-level `~~~` is consumed
 *   by fenced-code parsing; inline center is bridged to `+++` by [CenterPlugin.processMarkdown])
 *
 * Registered once in [CorePlugin.configureParser] and shared by all consuming plugins.
 */
internal class TildeDelimiterProcessor : DelimiterProcessor {

    override fun getOpeningCharacter(): Char = '~'
    override fun getClosingCharacter(): Char = '~'
    override fun getMinLength(): Int = 1

    /** Returns the run length when opening and closing counts match (1, 2, or 3), else 0. */
    override fun getDelimiterUse(openingRun: DelimiterRun, closingRun: DelimiterRun): Int {
        val openingLen = openingRun.length()
        val closingLen = closingRun.length()
        if (openingLen != closingLen) return 0
        if (!openingRun.canOpen() || !closingRun.canClose()) return 0
        return when (openingLen) {
            1, 2, 3 -> openingLen
            else -> 0
        }
    }

    override fun process(opener: Text, closer: Text, delimiterUse: Int) {
        when (delimiterUse) {
            1 -> handleSpoiler(opener, closer)
            2 -> handleStrikethrough(opener, closer)
            3 -> handleCenter(opener, closer)
        }
    }

    private fun handleSpoiler(opener: Text, closer: Text) {
        val nextNode = opener.next ?: return
        if (nextNode !is Text || !nextNode.literal.startsWith("!")) return
        val prevNode = closer.previous ?: return
        if (prevNode !is Text || !prevNode.literal.endsWith("!")) return

        val spoilerNode = SpoilerNode()
        var child: Node? = opener.next
        while (child != null && child !== closer) {
            val next = child.next
            spoilerNode.appendChild(child)
            child = next
        }

        val firstChild = spoilerNode.firstChild as? Text
        if (firstChild != null && firstChild.literal.startsWith("!")) {
            firstChild.literal = firstChild.literal.substring(1)
            if (firstChild.literal.isEmpty()) {
                firstChild.unlink()
            }
        }

        val lastChild = spoilerNode.lastChild as? Text
        if (lastChild != null && lastChild.literal.endsWith("!")) {
            lastChild.literal = lastChild.literal.substring(0, lastChild.literal.length - 1)
            if (lastChild.literal.isEmpty()) {
                lastChild.unlink()
            }
        }

        opener.insertAfter(spoilerNode)
        opener.unlink()
        closer.unlink()
    }

    private fun handleStrikethrough(opener: Text, closer: Text) {
        val strikeNode = StrikeThroughNode()
        var child: Node? = opener.next
        while (child != null && child !== closer) {
            val next = child.next
            strikeNode.appendChild(child)
            child = next
        }
        opener.insertAfter(strikeNode)
        opener.unlink()
        closer.unlink()
    }

    private fun handleCenter(opener: Text, closer: Text) {
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
