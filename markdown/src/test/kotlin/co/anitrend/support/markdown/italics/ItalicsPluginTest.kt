package co.anitrend.support.markdown.italics

import org.commonmark.node.Emphasis
import org.commonmark.node.Text
import org.commonmark.parser.Parser
import org.junit.Assert.*
import org.junit.Test

class ItalicsPluginTest {

    private val parser by lazy {
        Parser.builder().build()
    }

    @Test
    fun `single asterisk emphasis is parsed as Emphasis node`() {
        val testCase = """
            **OP and ED of the day**
            *Thanks for the nomination @neonwolf!!!*
            ____
            **OP** 
            youtube(https://youtu.be/_DIqplrohhg)
            
            **Harumodoki** by **Yanagi Nagi**
            __
            
            **ED**
            youtube(https://youtu.be/L3WiZx_XUOo)
            
            **Everyday World** 
            ____
            I nominate @chrisenpai || @bunns || @tobibot || @champi || @reeda || @astaa and anyone else who's interested in doing this
        """.trimIndent()

        val document = parser.parse(testCase)
        val emphasisNodes = mutableListOf<Emphasis>()
        walkTree(document) { node ->
            if (node is Emphasis) emphasisNodes.add(node)
        }

        assertEquals(1, emphasisNodes.size)
        val text = (emphasisNodes[0].firstChild as? Text)?.literal
        assertEquals("Thanks for the nomination @neonwolf!!!", text)
    }

    private fun walkTree(node: org.commonmark.node.Node, action: (org.commonmark.node.Node) -> Unit) {
        action(node)
        var child = node.firstChild
        while (child != null) {
            walkTree(child, action)
            child = child.next
        }
    }
}
