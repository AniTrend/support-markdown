package co.anitrend.support.markdown.integration

import co.anitrend.support.markdown.center.CenterNode
import co.anitrend.support.markdown.spoiler.node.SpoilerNode
import co.anitrend.support.markdown.strike.StrikeThroughNode
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Test

class DelimiterRecoveryIntegrationTest {

    private data class ParseCase(
        val name: String,
        val input: String
    )

    @Test
    fun `malformed delimiter matrix does not crash parse or render`() {
        val cases = listOf(
            ParseCase("unclosed_spoiler", "before ~!broken spoiler after"),
            ParseCase("mismatched_spoiler_close", "~!foo~~ bar !~"),
            ParseCase("crossed_delimiters", "~!a ~~b!~ c~~"),
            ParseCase("stray_tilde_runs", "~~~~ ~ ~~ ~~~"),
            ParseCase("broken_center_open_only", "+++center starts only"),
            ParseCase("broken_center_close_only", "center ends only+++")
        )

        for (case in cases) {
            val (document, rendered) = IntegrationTestSupport.parseAndRender(case.input)
            assertNotNull("document must exist for ${case.name}", document)
            assertFalse("render must not be blank for ${case.name}", rendered.isBlank())
        }
    }

    @Test
    fun `valid delimiters still parse when surrounded by garbage`() {
        val input = """
            [broken](
            ~!spoiler content!~ ???
            ~~strike~~ !!!
            +++center+++
            youtube() webm(no-close
        """.trimIndent()

        val (document, _) = IntegrationTestSupport.parseAndRender(input)
        val spoilerNodes = mutableListOf<SpoilerNode>()
        val strikeNodes = mutableListOf<StrikeThroughNode>()
        val centerNodes = mutableListOf<CenterNode>()

        IntegrationTestSupport.walkTree(document) { node ->
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
}
