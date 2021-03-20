package co.anitrend.support.markdown.spoiler

import androidx.annotation.ColorInt
import androidx.annotation.VisibleForTesting
import co.anitrend.support.markdown.common.IMarkdownPlugin
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.MarkwonSpansFactory
import io.noties.markwon.MarkwonVisitor
import java.lang.reflect.Modifier
import java.net.URLEncoder

/**
 * Surround text you want to hide with: __~!some spoiler text!~__
 *
 * Using __<div rel="spoiler">some spoiler text</div>__ also works **for now**,
 * but this might not be the case in the future - stick with the Markdown approach!
 *
 * Note that the spoiler text feature has a few bugs at the moment:
 *
 * + __~!...!~__ is converted _even in code blocks_, so it can be difficult to explain how it works to other users.
 * + It doesn't interact well with __~~~...~~~__ or __<center>...</center>__ it puts the "hidden" text _after_ the **Spoiler, click to view** block.
 *
 * Hopefully, these will be fixed in the future, but for now, be careful to avoid unintentionally revealing spoilers to other users!
 *
 * @since 0.1.0
 */
class SpoilerPlugin private constructor(
    @ColorInt private val backgroundColor: Int
): IMarkdownPlugin, AbstractMarkwonPlugin() {

    /**
     * Regular expression that should be used for the implementing classing
     */
    override val regex = Regex(
        pattern = PATTERN_SPOILER,
        option = RegexOption.IGNORE_CASE
    )

    override fun configureVisitor(builder: MarkwonVisitor.Builder) {
        //builder.on(Text::class.java, SpoilerNode(regex))
    }

    override fun configureSpansFactory(builder: MarkwonSpansFactory.Builder) {
        super.configureSpansFactory(builder)
        //builder.appendFactory(Text::class.java, SpoilerRender(backgroundColor))
    }

    override fun processMarkdown(markdown: String): String {
        var replacement = markdown
        val matches = regex.findAll(markdown)
        matches.forEach { matchResult ->
            val fullMatch = matchResult.groupValues[GROUP_ORIGINAL_MATCH]
            val contentMatch = matchResult.groupValues[GROUP_CONTENT]

            replacement = replacement.replace(
                fullMatch,
                "[Show spoiler](app.anitrend://spoiler?data=${
                    URLEncoder.encode(
                        contentMatch,
                        "utf-8"
                    )
                })"
            )
        }
        return replacement
    }

    companion object {

        @VisibleForTesting(otherwise = Modifier.PRIVATE)
        const val PATTERN_SPOILER = "~!([\\s\\S]*?)!~"

        private const val GROUP_ORIGINAL_MATCH = 0
        private const val GROUP_CONTENT = 1

        fun create(@ColorInt backgroundColor: Int) =
            SpoilerPlugin(backgroundColor)
    }
}