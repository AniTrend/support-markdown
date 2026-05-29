package co.anitrend.support.markdown.integration

import org.commonmark.node.FencedCodeBlock
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FenceIsolationIntegrationTest {

    @Test
    fun `center bridge remains fence safe and idempotent`() {
        val markdown = """
            ~~~
            +++should stay literal in fence+++
            ~~~

            ~~~this should center~~~
        """.trimIndent()

        val once = IntegrationTestSupport.preprocess(markdown)
        val twice = IntegrationTestSupport.preprocess(once)

        assertEquals(once, twice)
        assertTrue(once.contains("~~~\n+++should stay literal in fence+++\n~~~"))
        assertTrue(once.contains("+++this should center+++"))

        val parser = IntegrationTestSupport.configuredParser()
        val document = parser.parse(once)
        val fencedBlocks = mutableListOf<FencedCodeBlock>()

        IntegrationTestSupport.walkTree(document) { node ->
            if (node is FencedCodeBlock) fencedBlocks.add(node)
        }

        assertEquals(1, fencedBlocks.size)
    }
}
