package co.anitrend.support.markdown.spoiler

import co.anitrend.support.markdown.common.TildeDelimiterProcessor
import co.anitrend.support.markdown.spoiler.node.SpoilerNode
import org.commonmark.parser.Parser
import org.junit.Assert.*
import org.junit.Test

class SpoilerPluginTest {

    private val parser by lazy {
        Parser.builder()
            .customDelimiterProcessor(TildeDelimiterProcessor())
            .build()
    }

    @Test
    fun `spoiler elements are parsed into SpoilerNode`() {
        val testCase = """
            ~!youtube(ZVJ3Ho83Ksg)!~

            Watch "**京都橘高校吹奏楽部**　大手筋商店街パレード　_Kyoto Tachibana SHS Band_" ~~on YouTube~~

            ~!**Just enjoy &#x1f642;** !~
        """.trimIndent()

        val document = parser.parse(testCase)
        val spoilerNodes = mutableListOf<SpoilerNode>()
        walkTree(document) { node ->
            if (node is SpoilerNode) spoilerNodes.add(node)
        }

        assertEquals(2, spoilerNodes.size)
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
