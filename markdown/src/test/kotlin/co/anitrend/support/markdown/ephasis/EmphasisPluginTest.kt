package co.anitrend.support.markdown.ephasis

import co.anitrend.support.markdown.ICoreRegexTest
import org.junit.Assert.*
import org.junit.Test

class EmphasisPluginTest : ICoreRegexTest {

    override val plugin by lazy {
        EmphasisPlugin.create()
    }

    @Test
    override fun `defined regex pattern detect elements`() {
        val testCase = """
            ~~~__100 Day Anime Challenge__
            ========= ♡ ==========
            __Day 53: An anime scene that made you cry__

            _Terror in resonance_

            ~!img220(https://static.tumblr.com/3f12092c6fced04243d9f04bfd04f620/gjjyxzf/Nmsnzyskj/tumblr_static_tumblr_static_1h7d42ehp4u84408s0oogks0k_focused_v3.gif)!~

            ========= ♡ ==========~~~

            ~!~~1. Very first anime you watched
            2. Favorite anime you’ve watched so far
            3. Favorite male anime character ever
            4. Favorite female anime character ever
            5. Anime you’re ashamed you enjoyed
            6. A anime you want to see but haven’t yet
            7. Your anime crush
            8. Favorite anime couple
            9. Best anime villain
            10. Favorite fighter anime
            11. Favorite mech anime
            12. Saddest anime scene
            13. Anime character you most similar to
            14. Saddest anime death
            15. Favorite animal sidekick, pet or summoning
            16. Anime with the best animation
            17. Favorite supporting male anime character
            18. Favorite supporting female anime character
            19. Most epic scene ever
            20. Anime character that gets on your nerves
            21. Favorite goofy anime character
            22. Favorite weapon, gear or armor used in an anime
            23. Favorite attack someone used in an anime
            24. Moment that shocked you the most in any anime
            25. Anime that never gets old no matter how many times you’ve rewatched it
            26. Best anime fight
            27. Most badass scene from any anime character
            28. Favorite quote from any anime character
            29. An anime you wished was real
            30. An anime you wish never ended and continued on
            31. Favorite anime OP
            32. Which anime characters would you want as a best friend?
            33. Anime character you would like to cosplay as
            34. What anime character would be your workout buddy?
            35. What crossover would you like to see?
            36. Your first anime crush
            37. Anime that changed you
            38. Do you like yaoi and/or yuri?
            39. Favourite music in an anime
            40. Favourite VA/seiyuu
            41. Anime character you hate
            42. Most epic or shocking anime scene ever
            43. Favorite canon couple
            44. Who do you ship?
            45. Favorite anime ED
            46. What’s your favorite shonen anime?
            47. Favorite tsundere
            48. Favorite dandere
            49. Subs or dubs?
            50. Favorite studio ghibi work
            51. Favorite BL anime
            52. Anime with the best soundtrack
            53. An anime scene that made you cry~~
            54. Recommend one anime that most people may not have seen
            55. Do you share your anime interests with anyone?
            56. Your favorite character
            57. What anime has your heart?
            58. What’s your favorite hentai anime?
            59. What’s your favorite yaoi anime?
            60. Wish that manga would turn into an anime
            61. What current anime are you watching?
            62. Favorite anime movie
            63. Favorite shonen series
            64. Favorite shojo series
            65. Favorite sports anime
            66. Favorite slice of life anime
            67. Favorite comedy anime
            68. Favorite science fiction anime
            69. Favorite action/adventure anime
            70. Favorite fantasy anime
            71. Favorite romance anime
            72. An overrated series
            73. An underrated series
            74. That series that got you into anime
            75. Favorite friendship
            76. Favorite bromance
            77. Favorite rivalry
            78. Favorite team
            79. Favorite quote
            80. Favorite scene
            81. Favorite episode
            82. Most annoying anime character
            83. Favorite gore anime
            84. Most recent anime wallpaper
            85. Favorite shojo anime
            86. Best yandere character
            87. Favorite anime hero
            88. Favorite school uniformAQA
            89. Favorite anime opening theme song
            90. Picture of a character in a swimsuit
            91. Favorite attack a character has used
            92. Favorite anime of high school characters
            93. Hottest anime guy
            94. Favorite kuudere
            95. Favorite manga
            96. Anime character frame in your room
            97. An anime character you want to see in a nightmare
            98. Male character’s best kimono ever
            99. Female character’s best yukata
            100. Favorite harem/reverse harem!~
        """.trimIndent()
    }

    @Test
    fun `test mixed html and markdown emphasis`() {
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
            <Center> <a>  <b>OP and ED of the day</b> </a>
            <Center>  *Thanks for the nomination @neonwolf!!!*
            ____
            <Center>  <b>OP</b> 
            youtube(https://youtu.be/_DIqplrohhg)
            
            <b>Harumodoki</b> by <b>Yanagi Nagi</b>
            __
            
            <b>ED</b>
            youtube(https://youtu.be/L3WiZx_XUOo)
            
            <b>Everyday World</b> 
            ____
            I nominate @chrisenpai || @bunns || @tobibot || @champi || @reeda || @astaa and anyone else who's interested in doing this
        """.trimIndent()

        val actual = plugin.processMarkdown(testCase)
        assertEquals(testCaseExpected, actual)
    }
}