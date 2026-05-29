package co.anitrend.support.markdown.integration

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RobustnessStressTest {

    @Test(timeout = 5000)
    fun `high volume malformed payload remains stable`() {
        val chunk = "~!broken !~ ~~strike~~ +++center+++ youtube(no-close img20(url webm() [x]( "
        val payload = buildString {
            repeat(1200) {
                append(chunk)
                append(' ')
            }
        }

        val (document, rendered) = IntegrationTestSupport.parseAndRender(payload)
        assertTrue(document.firstChild != null)
        assertFalse(rendered.isBlank())
    }

    @Test(timeout = 5000)
    fun `output is deterministic for malformed mixed input`() {
        val input = """
            ~!broken
            ~!spoiler!~
            ~~strike~~
            +++center+++
            <align <a href=
            youtube() webm(no-close img(
        """.trimIndent()

        val (_, firstRender) = IntegrationTestSupport.parseAndRender(input)
        val (_, secondRender) = IntegrationTestSupport.parseAndRender(input)

        assertEquals(firstRender, secondRender)
    }
}
