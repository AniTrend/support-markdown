package co.anitrend.support.markdown.youtube

import co.anitrend.support.markdown.ICoreRegexTest
import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class YoutubePluginTest : ICoreRegexTest {

    override val plugin by lazy {
        YouTubePlugin.create()
    }

    @Test
    override fun `defined regex pattern detect elements`() {
        val testCase = """
            youtube(HbVaFnx-uAw) 
            
            ~!youtube(https://www.youtube.com/watch?v=HbVaFnx-uAw)!~
            
            ~!youtube(https://youtu.be/HbVaFnx-uAw)!~

            [Manaria Friends](https://anilist.co/anime/21322) "TVアニメ『マナリアフレンズ』第2弾PV" on YouTube

            New Waifu??? __#t h i c c  t h i g h s__ =_=
        """.trimIndent()

        assertTrue(plugin.regex.containsMatchIn(testCase))

        val matchResultSet = plugin.regex.findAll(testCase, 0)

        val actual = matchResultSet.count()
        assertEquals(3, actual)
    }

    @Test
    fun `create full youtube url from text`() {
        val input = "HbVaFnx-uAw"
        val expected = "https://www.youtube.com/watch?v=HbVaFnx-uAw"
        val actual = plugin.buildYoutubeFullLink(input)

        assertEquals(expected, actual)
    }

    @Test
    fun `create full youtube thumbnail from text`() {
        val input = "https://www.youtube.com/watch?v=HbVaFnx-uAw"
        val expected = "https://img.youtube.com/vi/HbVaFnx-uAw/hqdefault.jpg"
        val actual = plugin.buildYoutubeThumbnail(input)

        assertEquals(expected, actual)
    }

    @Test
    fun `parse content with youtube links`() {
        val expected = """
            <align center>img420(https://cdn.discordapp.com/attachments/712681140481163287/822825744375742503/Song_of_the_Day.png)</align>

            ##<align center><b>Nominated by @Sunshot「[Link](https://anilist.co/activity/190596535)」</b></align>

            <align center>img600(https://cdn.discordapp.com/attachments/712681140481163287/822826209494695936/LineGray.png)</align>

            ##<align center><b>[Stand By Me](https://www.youtube.com/watch?v=vv2DSmy3Tro&ab_channel=FlorenceMachineVEVO) </b></align>
            ####<align center><b>from FINAL FANTASY XV</b></align>
            ###<align center><b>by [Florence + The Machine](https://www.youtube.com/channel/UC5MujsH-hrVWHBBfSSmv18A)</b></align>

            <align center><a href="https://www.youtube.com/watch?v=vv2DSmy3Tro&ab_channel=FlorenceMachineVEVO"><img src="https://img.youtube.com/vi/vv2DSmy3Tro/hqdefault.jpg" width="100%"/></a></align>

            <align center>「[Spotify Link](https://open.spotify.com/track/5XSU59wtE5CRCAEyHmmGy4?si=JtsEJuASSv-Y2j_M1PYb9A)」</align>


            <align center>img600(https://cdn.discordapp.com/attachments/712681140481163287/822826209494695936/LineGray.png)</align>

            <align center>
            Stand By Me was a single originally made by [Ben E. King](https://www.youtube.com/watch?v=hwZNL7QVJjE&ab_channel=SoulfulSounds) in 1961 that got covered by Florence and The Machine in 2016.

            Although Final Fantasy XV has a lot of plot holes and an overall bad reputation in the FF community i still see it as my favourite game of all time. I just love the characters, the worldbuilding the music and the story (including all novels and movies that got released for the game). It annoys me so hard that Square Enix rushed this game so hard in the end, it could have been such a amazing game. 

            I get goose bumps every time when i replay FFXV and see this scene at the beginning of the game. 
            God, it's so amazing.

            <a href="https://www.youtube.com/watch?v=hgJkqyuwWBw&ab_channel=Apalagi08"><img src="https://img.youtube.com/vi/hgJkqyuwWBw/hqdefault.jpg" width="100%"/></a>
            </align>

            <align center>
            -<a> ✧</a> - ――――――――――――――――― - <a>✧</a> -
            #<b>- <a>Nominations</a> -</b>
            ##<b>[- - -]</b>

            ####<b><a>+</a> Everyone who wants to to do this challenge</b>
            -<a> ✧</a> - ――――――――― - <a>✧</a> - ――――――――― - <a>✧</a> -
            </align>
        """.trimIndent()
        val input = """
            <align center>img420(https://cdn.discordapp.com/attachments/712681140481163287/822825744375742503/Song_of_the_Day.png)</align>

            ##<align center><b>Nominated by @Sunshot「[Link](https://anilist.co/activity/190596535)」</b></align>

            <align center>img600(https://cdn.discordapp.com/attachments/712681140481163287/822826209494695936/LineGray.png)</align>

            ##<align center><b>[Stand By Me](https://www.youtube.com/watch?v=vv2DSmy3Tro&ab_channel=FlorenceMachineVEVO) </b></align>
            ####<align center><b>from FINAL FANTASY XV</b></align>
            ###<align center><b>by [Florence + The Machine](https://www.youtube.com/channel/UC5MujsH-hrVWHBBfSSmv18A)</b></align>

            <align center>youtube(https://www.youtube.com/watch?v=vv2DSmy3Tro&ab_channel=FlorenceMachineVEVO)</align>

            <align center>「[Spotify Link](https://open.spotify.com/track/5XSU59wtE5CRCAEyHmmGy4?si=JtsEJuASSv-Y2j_M1PYb9A)」</align>


            <align center>img600(https://cdn.discordapp.com/attachments/712681140481163287/822826209494695936/LineGray.png)</align>

            <align center>
            Stand By Me was a single originally made by [Ben E. King](https://www.youtube.com/watch?v=hwZNL7QVJjE&ab_channel=SoulfulSounds) in 1961 that got covered by Florence and The Machine in 2016.

            Although Final Fantasy XV has a lot of plot holes and an overall bad reputation in the FF community i still see it as my favourite game of all time. I just love the characters, the worldbuilding the music and the story (including all novels and movies that got released for the game). It annoys me so hard that Square Enix rushed this game so hard in the end, it could have been such a amazing game. 

            I get goose bumps every time when i replay FFXV and see this scene at the beginning of the game. 
            God, it's so amazing.

            youtube(https://www.youtube.com/watch?v=hgJkqyuwWBw&ab_channel=Apalagi08)
            </align>

            <align center>
            -<a> ✧</a> - ――――――――――――――――― - <a>✧</a> -
            #<b>- <a>Nominations</a> -</b>
            ##<b>[- - -]</b>

            ####<b><a>+</a> Everyone who wants to to do this challenge</b>
            -<a> ✧</a> - ――――――――― - <a>✧</a> - ――――――――― - <a>✧</a> -
            </align>
        """.trimIndent()

        val actual = plugin.processMarkdown(input)

        assertEquals(expected, actual)
    }
}