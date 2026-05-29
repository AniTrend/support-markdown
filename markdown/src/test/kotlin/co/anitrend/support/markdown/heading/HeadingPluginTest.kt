package co.anitrend.support.markdown.heading

import org.commonmark.node.Heading
import org.commonmark.parser.Parser
import org.junit.Assert.*
import org.junit.Test

class HeadingPluginTest {

    private val parser by lazy {
        Parser.builder().build()
    }

    @Test
    fun `setext headings are parsed by commonmark-java`() {
        val testCase = """
            Hello this is a title
            ===

            this is also another title
            ---
        """.trimIndent()

        val document = parser.parse(testCase)
        val headings = mutableListOf<Heading>()
        walkTree(document) { node ->
            if (node is Heading) headings.add(node)
        }

        assertEquals(2, headings.size)
        assertEquals(1, headings[0].level)
        assertEquals(2, headings[1].level)
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
