package co.anitrend.support.markdown.integration

import co.anitrend.support.markdown.center.CenterNode
import co.anitrend.support.markdown.center.CenterPlugin
import co.anitrend.support.markdown.core.CorePlugin
import co.anitrend.support.markdown.spoiler.SpoilerPlugin
import co.anitrend.support.markdown.spoiler.node.SpoilerNode
import co.anitrend.support.markdown.strike.StrikeThroughNode
import org.commonmark.node.FencedCodeBlock
import org.commonmark.node.Node
import org.commonmark.parser.Parser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ParserConfigurationIntegrationTest {

    @Test
    fun `core center and spoiler parser configuration builds without delimiter conflicts`() {
        val builder = Parser.builder()

        CorePlugin.create().configureParser(builder)
        CenterPlugin.create().configureParser(builder)
        SpoilerPlugin.create().configureParser(builder)

        val parser = builder.build()
        val document = parser.parse("~!spoiler!~ ~~strike~~ +++center+++")

        val spoilerNodes = mutableListOf<SpoilerNode>()
        val strikeNodes = mutableListOf<StrikeThroughNode>()
        val centerNodes = mutableListOf<CenterNode>()

        walkTree(document) { node ->
            when (node) {
                is SpoilerNode -> spoilerNodes.add(node)
                is StrikeThroughNode -> strikeNodes.add(node)
                is CenterNode -> centerNodes.add(node)
            }
        }

        assertEquals(1, spoilerNodes.size)
        assertEquals(1, strikeNodes.size)
        assertEquals(1, centerNodes.size)
    }

    @Test
    fun `center processMarkdown does not rewrite fenced code markers`() {
        val plugin = CenterPlugin.create()
        val markdown = """
            ~~~
            this should remain a fenced block
            ~~~

            ~~~this should center~~~
        """.trimIndent()

        val processed = plugin.processMarkdown(markdown)
        val parser = Parser.builder().build()
        val document = parser.parse(processed)

        val fencedBlocks = mutableListOf<FencedCodeBlock>()
        walkTree(document) { node ->
            if (node is FencedCodeBlock) fencedBlocks.add(node)
        }

        assertTrue(processed.contains("~~~\nthis should remain a fenced block\n~~~"))
        assertTrue(processed.contains("+++this should center+++"))
        assertEquals(1, fencedBlocks.size)
    }

    private fun walkTree(node: Node, action: (Node) -> Unit) {
        action(node)
        var child = node.firstChild
        while (child != null) {
            walkTree(child, action)
            child = child.next
        }
    }
}
