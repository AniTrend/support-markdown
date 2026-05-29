package co.anitrend.support.markdown.webm

import org.junit.Assert.*
import org.junit.Test

class WebMPluginTest {

    private val plugin by lazy {
        WebMPlugin.create()
    }

    @Test
    fun `processMarkdown converts webm syntax to linked thumbnails`() {
        val testCase = """
            __Blue Day Done Right &#128525; ♥️ &#128527;__

            img270(https://cdn.discordapp.com/attachments/458389398782869524/541236388897751061/WhatsApp_Image_2019-02-02_at_11.45.24.jpeg) img250(https://cdn.discordapp.com/attachments/458389398782869524/541236389522571264/WhatsApp_Image_2019-02-02_at_11.45.28.jpeg) 

            ~!img250(https://cdn.discordapp.com/attachments/458389398782869524/541236396401360906/WhatsApp_Image_2019-02-02_at_11.45.26.jpeg) img250(https://cdn.discordapp.com/attachments/458389398782869524/541236404471070730/WhatsApp_Image_2019-02-02_at_11.45.36_1.jpeg)!~

            __We're not done yet..

            webm(https://cdn.discordapp.com/attachments/458389398782869524/541236487275151360/WhatsApp_Video_2019-02-02_at_11.45.32.mp4)
        """.trimIndent()

        val result = plugin.processMarkdown(testCase)
        val linkCount = """<a href="https://cdn\.discordapp\.com/attachments/""".toRegex().findAll(result).count()
        assertEquals(1, linkCount)
    }
}
