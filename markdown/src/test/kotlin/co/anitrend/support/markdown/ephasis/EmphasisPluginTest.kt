package co.anitrend.support.markdown.ephasis

import org.commonmark.node.StrongEmphasis
import org.commonmark.node.Text
import org.commonmark.parser.Parser
import org.junit.Assert.*
import org.junit.Test

class EmphasisPluginTest {

    private val parser by lazy {
        Parser.builder().build()
    }

    @Test
    fun `double asterisk emphasis is parsed as StrongEmphasis node`() {
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
        val strongNodes = mutableListOf<StrongEmphasis>()
        walkTree(document) { node ->
            if (node is StrongEmphasis) strongNodes.add(node)
        }

        assertEquals(6, strongNodes.size)
        val firstText = (strongNodes[0].firstChild as? Text)?.literal
        assertEquals("OP and ED of the day", firstText)
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
