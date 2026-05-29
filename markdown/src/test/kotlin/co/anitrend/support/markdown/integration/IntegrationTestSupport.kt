package co.anitrend.support.markdown.integration

import co.anitrend.support.markdown.center.CenterPlugin
import co.anitrend.support.markdown.core.CorePlugin
import co.anitrend.support.markdown.image.ImagePlugin
import co.anitrend.support.markdown.webm.WebMPlugin
import co.anitrend.support.markdown.youtube.YouTubePlugin
import org.commonmark.node.Node
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer

internal object IntegrationTestSupport {

    private val centerPlugin by lazy { CenterPlugin.create() }
    private val imagePlugin by lazy { ImagePlugin.create() }
    private val youtubePlugin by lazy { YouTubePlugin.create() }
    private val webmPlugin by lazy { WebMPlugin.create() }

    fun parseAndRender(markdown: String): Pair<Node, String> {
        val processed = preprocess(markdown)
        val parser = configuredParser()
        val document = parser.parse(processed)
        val renderer = HtmlRenderer.builder().build()
        return document to renderer.render(document)
    }

    fun preprocess(markdown: String): String {
        var current = markdown
        current = centerPlugin.processMarkdown(current)
        current = imagePlugin.processMarkdown(current)
        current = youtubePlugin.processMarkdown(current)
        current = webmPlugin.processMarkdown(current)
        return current
    }

    fun configuredParser(): Parser {
        val builder = Parser.builder()
        CorePlugin.create().configureParser(builder)
        CenterPlugin.create().configureParser(builder)
        return builder.build()
    }

    fun walkTree(node: Node, action: (Node) -> Unit) {
        action(node)
        var child = node.firstChild
        while (child != null) {
            walkTree(child, action)
            child = child.next
        }
    }
}
