package co.anitrend.support.markdown.italics

import co.anitrend.support.markdown.ICoreRegexTest
import co.anitrend.support.markdown.common.IMarkdownPlugin
import org.junit.Assert.*
import org.junit.Test

class ItalicsPluginTest : ICoreRegexTest {
    override val plugin by lazy {
        ItalicsPlugin.create()
    }

    @Test
    override fun `defined regex pattern detect elements`() {
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

        val matchResultSet = plugin.regex.findAll(testCase, 0)

        val actual = matchResultSet.count()
        assertEquals(1, actual)
    }
}