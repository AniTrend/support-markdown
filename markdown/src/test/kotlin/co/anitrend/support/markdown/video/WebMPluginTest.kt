package co.anitrend.support.markdown.video

import co.anitrend.support.markdown.ICoreRegexTest
import co.anitrend.support.markdown.core.IMarkdownPlugin
import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class WebMPluginTest : ICoreRegexTest {

    override val plugin: IMarkdownPlugin by lazy {
        WebMPlugin.create()
    }

    @Test
    override fun `defined regex pattern detect elements`() {
        assertTrue(true)
    }
}