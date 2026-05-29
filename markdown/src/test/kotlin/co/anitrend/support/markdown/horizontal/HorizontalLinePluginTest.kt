package co.anitrend.support.markdown.horizontal

import org.commonmark.node.ThematicBreak
import org.commonmark.parser.Parser
import org.junit.Assert.*
import org.junit.Test

class HorizontalLinePluginTest {

    private val parser by lazy {
        Parser.builder().build()
    }

    @Test
    fun `thematic breaks are parsed by commonmark-java`() {
        val testCase = """
            text above

            ---

            ***

            ___
        """.trimIndent()

        val document = parser.parse(testCase)
        val breaks = mutableListOf<ThematicBreak>()
        walkTree(document) { node ->
            if (node is ThematicBreak) breaks.add(node)
        }

        assertEquals(3, breaks.size)
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
