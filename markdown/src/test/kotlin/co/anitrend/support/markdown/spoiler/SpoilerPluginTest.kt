package co.anitrend.support.markdown.spoiler

import android.graphics.Color
import co.anitrend.support.markdown.ICoreRegexTest
import co.anitrend.support.markdown.common.IMarkdownPlugin
import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class SpoilerPluginTest : ICoreRegexTest {

    override val plugin by lazy {
        SpoilerPlugin.create(Color.BLACK, Color.BLUE)
    }

    @Test
    override fun `defined regex pattern detect elements`() {
        val testCase = """
            ~!youtube(ZVJ3Ho83Ksg)!~

            Watch "**京都橘高校吹奏楽部**　大手筋商店街パレード　_Kyoto Tachibana SHS Band_" ~~on YouTube~~

            ~!**Just enjoy &#x1f642;** !~
        """.trimIndent()

        assertTrue(plugin.regex.containsMatchIn(testCase))

        val matchResultSet = plugin.regex.findAll(testCase, 0)

        val actual = matchResultSet.count()
        assertEquals(2, actual)
    }
}