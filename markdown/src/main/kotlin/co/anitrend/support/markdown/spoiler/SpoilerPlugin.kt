package co.anitrend.support.markdown.spoiler

import android.text.Spannable
import android.text.Spanned
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.VisibleForTesting
import co.anitrend.support.markdown.common.IMarkdownPlugin
import co.anitrend.support.markdown.spoiler.node.SpoilerNode
import co.anitrend.support.markdown.spoiler.render.SpoilerRender
import co.anitrend.support.markdown.spoiler.span.SpoilerClickableSpan
import co.anitrend.support.markdown.spoiler.span.SpoilerHideSpan
import co.anitrend.support.markdown.spoiler.span.SpoilerSpan
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.MarkwonSpansFactory
import io.noties.markwon.MarkwonVisitor
import org.commonmark.node.Text
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
    @ColorInt private val textColor: Int,
    @ColorInt private val backgroundColor: Int
): IMarkdownPlugin, AbstractMarkwonPlugin() {

    /**
     * Regular expression that should be used for the implementing classing
     */
    override val regex = Regex(
        pattern = PATTERN_SPOILER,
        option = RegexOption.IGNORE_CASE
    )

    private fun applySpoilerSpan(spannable: Spannable) {
        val text = spannable.toString()
        val matches = regex.findAll(text)
        val hideSpan = SpoilerHideSpan()

        matches.forEach { matchResult ->
            val spoilerSpan = SpoilerSpan(textColor, backgroundColor)
            val clickableSpan = SpoilerClickableSpan(spoilerSpan)

            val matchRange = matchResult.range

            val start = matchRange.first
            val end = matchRange.last + 1

            spannable.setSpan(spoilerSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            // Hide spoiler markers
            spannable.setSpan(hideSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(hideSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    override fun beforeSetText(textView: TextView, markdown: Spanned) {
        super.beforeSetText(textView, markdown)
        //applySpoilerSpan(markdown as Spannable)
    }

    override fun processMarkdown(markdown: String): String {
        //return super.processMarkdown(markdown)
        var replacement = markdown
        val matches = regex.findAll(markdown)
        matches.forEach { matchResult ->
            val fullMatch = matchResult.groupValues[GROUP_ORIGINAL_MATCH]
            val contentMatch = matchResult.groupValues[GROUP_CONTENT]

            replacement = replacement.replace(
                fullMatch,
                """<a href="app.anitrend://spoiler?data=${
                    URLEncoder.encode(
                        contentMatch,
                        "utf-8"
                    )
                }">Spoiler, click to view</a>"""
            )
        }
        return replacement
    }

    companion object {

        @VisibleForTesting(otherwise = Modifier.PRIVATE)
        const val PATTERN_SPOILER = "~!([\\s\\S]*?)!~"

        private const val GROUP_ORIGINAL_MATCH = 0
        private const val GROUP_CONTENT = 1

        fun create(@ColorInt textColor: Int, @ColorInt backgroundColor: Int) =
            SpoilerPlugin(textColor, backgroundColor)
    }
}