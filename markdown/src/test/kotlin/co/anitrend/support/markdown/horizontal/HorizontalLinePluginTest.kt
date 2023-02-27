package co.anitrend.support.markdown.horizontal

import co.anitrend.support.markdown.ICoreRegexTest
import org.junit.Assert.*
import org.junit.Ignore
import org.junit.Test

class HorizontalLinePluginTest : ICoreRegexTest {

    override val plugin by lazy {
        HorizontalLinePlugin.create()
    }

    @Ignore("Known to be failing, a fix has not been created yet")
    @Test
    override fun `defined regex pattern detect elements`() {
        val testCase = """
            Hello this is a title
            ===

            this is also another title
            ---

            ---
            what about this?
            *****

            Make sure to have a blank line either side to avoid ambiguity: 
            this is a header
            - - - - - - - - - - - - - - 

            this is text followed by a horizontal line
            _ _ _ _

            Nanikore *** wiykd akhf k kajfhl safh
            ___
            skjadlkjfweafs lasdkfewa


            ***

            Make sure to have a blank line either side to avoid ambiguity: 
            this is a header
            ---
            this is text followed by a horizontal line
            ---

            Alternatively, you can use the <hr> HTML tag. 
        """.trimIndent()

        assertTrue(plugin.regex.containsMatchIn(testCase))

        val matchResultSet = plugin.regex.findAll(testCase)

        val actual = matchResultSet.count()
        assertEquals(4, actual)
    }


    @Test
    fun `assure no matches can be found`() {
        val testCase = """
            <Center> <a>  **OP and ED of the day** </a>
            <Center>  *Thanks for the nomination @neonwolf!!!*
            ____
            <Center>  **OP** 
            youtube(https://youtu.be/_DIqplrohhg)
            
            **Harumodoki** by **Yanagi Nagi**
            __
            
            **ED**
            youtube(https://youtu.be/L3WiZx_XUOo)
            
            **Everyday World** 
            ____
            I nominate @chrisenpai || @bunns || @tobibot || @champi || @reeda || @astaa and anyone else who's interested in doing this
        """.trimIndent()

        assertTrue(plugin.regex.containsMatchIn(testCase))

        val matchResultSet = plugin.regex.findAll(testCase)

        val actual = matchResultSet.count()
        assertEquals(2, actual)
    }
}