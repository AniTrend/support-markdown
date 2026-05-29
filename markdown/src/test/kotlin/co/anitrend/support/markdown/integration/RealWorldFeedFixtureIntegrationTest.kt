package co.anitrend.support.markdown.integration

import co.anitrend.support.markdown.center.CenterNode
import co.anitrend.support.markdown.spoiler.node.SpoilerNode
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RealWorldFeedFixtureIntegrationTest {

    @Test(timeout = 5000)
    fun `real world feed fixture parses and renders safely`() {
        val cases = loadCases()
        assertFalse(cases.isEmpty())

        for ((index, input) in cases.withIndex()) {
            val (_, rendered) = IntegrationTestSupport.parseAndRender(input)
            assertFalse("render must not be blank for case $index", rendered.isBlank())
        }
    }

    @Test(timeout = 5000)
    fun `real world fixture keeps valid custom syntax discoverable`() {
        val joined = loadCases().joinToString(separator = "\n\n")
        val (document, rendered) = IntegrationTestSupport.parseAndRender(joined)

        val spoilerNodes = mutableListOf<SpoilerNode>()
        val centerNodes = mutableListOf<CenterNode>()

        IntegrationTestSupport.walkTree(document) { node ->
            when (node) {
                is SpoilerNode -> spoilerNodes.add(node)
                is CenterNode -> centerNodes.add(node)
            }
        }

        assertTrue(spoilerNodes.isNotEmpty())
        assertTrue(centerNodes.isNotEmpty())

        val (_, renderedAgain) = IntegrationTestSupport.parseAndRender(joined)
        assertEquals(rendered, renderedAgain)
    }

    private fun loadCases(): List<String> {
        val content = javaClass.classLoader
            .getResource("fixtures/anilist-feed-degenerate-inputs.txt")
            ?.readText()
            ?: error("fixture not found")

        return content
            .split("---CASE---")
            .map { it.trim() }
            .filter { it.isNotBlank() }
    }
}
