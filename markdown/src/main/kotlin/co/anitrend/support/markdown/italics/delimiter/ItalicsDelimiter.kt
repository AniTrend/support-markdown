package co.anitrend.support.markdown.italics.delimiter

import org.commonmark.node.Emphasis
import org.commonmark.node.Text
import org.commonmark.parser.delimiter.DelimiterProcessor
import org.commonmark.parser.delimiter.DelimiterRun

class ItalicsDelimiter : DelimiterProcessor {
    /**
     * @return the character that marks the beginning of a delimited node, must not clash with any built-in special
     * characters
     */
    override fun getOpeningCharacter(): Char = '*'

    /**
     * @return the character that marks the the ending of a delimited node, must not clash with any built-in special
     * characters. Note that for a symmetric delimiter such as "*", this is the same as the opening.
     */
    override fun getClosingCharacter(): Char = '*'

    /**
     * Minimum number of delimiter characters that are needed to activate this. Must be at least 1.
     */
    override fun getMinLength(): Int = 1

    /**
     * Determine how many (if any) of the delimiter characters should be used.
     *
     *
     * This allows implementations to decide how many characters to use based on the properties of the delimiter runs.
     * An implementation can also return 0 when it doesn't want to allow this particular combination of delimiter runs.
     *
     * @param opener the opening delimiter run
     * @param closer the closing delimiter run
     * @return how many delimiters should be used; must not be greater than length of either opener or closer
     */
    override fun getDelimiterUse(opener: DelimiterRun?, closer: DelimiterRun?): Int {
        if (opener?.length() == 1 && closer?.length() == 1)
            return 1
        return 0
    }

    /**
     * Process the matched delimiters, e.g. by wrapping the nodes between opener and closer in a new node, or appending
     * a new node after the opener.
     *
     *
     * Note that removal of the delimiter from the delimiter nodes and unlinking them is done by the caller.
     *
     * @param opener the text node that contained the opening delimiter
     * @param closer the text node that contained the closing delimiter
     * @param delimiterUse the number of delimiters that were used
     */
    override fun process(opener: Text?, closer: Text?, delimiterUse: Int) {
        //val node = ()
//
        //var nextNode = opener?.next
        //while (nextNode != null && nextNode != closer) {
        //    val next = nextNode.next
        //    node.appendChild(nextNode)
        //    nextNode = next
        //}
//
        //opener?.insertAfter(node)
    }
}