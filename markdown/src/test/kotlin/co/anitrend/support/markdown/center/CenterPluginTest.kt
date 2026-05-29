package co.anitrend.support.markdown.center

import co.anitrend.support.markdown.common.TildeDelimiterProcessor
import org.commonmark.parser.Parser
import org.junit.Assert.*
import org.junit.Test

class CenterPluginTest {

    private val parser by lazy {
        Parser.builder()
            .customDelimiterProcessor(TildeDelimiterProcessor())
            .customDelimiterProcessor(PlusDelimiterProcessor())
            .build()
    }

    @Test
    fun `triple tilde elements are parsed into CenterNode`() {
        val testCase = """
            # +++__Creator of [AniTrend](https://anitrend.co), check it out :p__+++

            +++<a href="https://discordapp.com/invite/2wzTqnF"><img src="https://img.shields.io/discord/314442908478472203.svg?color=%237289da&label=Join%20Anitrend!&logo=discord&logoColor=%23fff" alt="best anitrend"/>+++

            +++__I can make stuff with my mind, how cool is that?? :p!__+++
            +++img250(https://media.giphy.com/media/gZq7GstcdqVXi/giphy.gif)+++

            +++__[Kitsu](https://kitsu.io/users/wax911)__ | __[Instagram](https://www.instagram.com/nekosenpaic/)__ | __[GitHub](https://github.com/wax911)__+++

            +++__Can I Be Your Senpai Now??__+++
            +++img250(https://media.giphy.com/media/VnNdJolKFyg7e/giphy.gif)+++

            +++<a href="https://www.patreon.com/bePatron?u=7968843" data-patreon-widget-type="become-patron-button">Support Me On Patron!</a>+++
        """.trimIndent()

        val document = parser.parse(testCase)
        val centerNodes = mutableListOf<CenterNode>()
        walkTree(document) { node ->
            if (node is CenterNode) centerNodes.add(node)
        }

        assertEquals(8, centerNodes.size)
    }

    private fun walkTree(node: org.commonmark.node.Node, action: (org.commonmark.node.Node) -> Unit) {
        action(node)
        var child = node.firstChild
        while (child != null) {
            walkTree(child, action)
            child = child.next
        }
    }
}
