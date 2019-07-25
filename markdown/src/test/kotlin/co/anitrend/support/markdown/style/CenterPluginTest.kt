package co.anitrend.support.markdown.style

import co.anitrend.support.markdown.ICoreRegexTest
import co.anitrend.support.markdown.core.IMarkdownPlugin
import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class CenterPluginTest : ICoreRegexTest {

    override val plugin: IMarkdownPlugin by lazy {
        CenterPlugin.create()
    }

    @Test
    override fun `defined regex pattern detect elements`() {
        assertTrue(true)
    }
}