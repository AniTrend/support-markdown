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
        val testCaseExpected = """
            <Center> <a>  **OP and ED of the day** </a>
            <Center>  <i>Thanks for the nomination @neonwolf!!!</i>
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

        val actual = plugin.processMarkdown(testCase)
        assertEquals(testCaseExpected, actual)
    }
}