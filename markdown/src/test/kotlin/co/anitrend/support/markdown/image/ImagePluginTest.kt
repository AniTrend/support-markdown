package co.anitrend.support.markdown.image

import co.anitrend.support.markdown.ICoreRegexTest
import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ImagePluginTest : ICoreRegexTest {

    override val plugin by lazy {
        ImagePlugin.create()
    }

    @Test
    override fun `defined regex pattern detect elements`() {
        val testCase = """
            ~!
            img180(https://www.anime-planet.com/images/characters/henrietta-168.jpg) 
            img180(https://cdn.myanimelist.net/images/characters/7/30959.jpg)!~

            img(https://something.top.find)

            img20%(https://something.top.find)

            iMg20px(https://something.top.find) img20px(https://something.img.ref)

            ImG20px(https://something.top.find)
        """.trimIndent()

        assertTrue(plugin.regex.containsMatchIn(testCase))

        val matchResultSet = plugin.regex.findAll(testCase, 0)

        val actual = matchResultSet.count()
        assertEquals(7, actual)
    }

    @Test
    fun `why would you even do this`() {
        val testCase = """
            1. Armin Arlertimg220(https://media1.tenor.com/images/b959fa83a0a6c9687e8570f6a66ed0cd/tenor.gif?itemid=20271472)2. Giorno Giovannaimg220(https://i.pinimg.com/originals/e3/e2/43/e3e24317171a30a05de210561ce63084.gif)3. Ai Hayasakaimg220(https://64.media.tumblr.com/9f889a4e3bc90860d9682e6a4704da6c/tumblr_pn17jhbwYy1y0nwq1o1_540.gifv)4. Katsuki Bakugoimg220(https://i.pinimg.com/originals/19/3e/d6/193ed6fe2dbba74ea2945a812afdfa43.gif)
            5. Violet Evergarden
        """.trimIndent()

        assertTrue(plugin.regex.containsMatchIn(testCase))

        val matchResultSet = plugin.regex.findAll(testCase, 0).toList()

        val actual = matchResultSet.count()
        assertEquals(4, actual)
    }
}