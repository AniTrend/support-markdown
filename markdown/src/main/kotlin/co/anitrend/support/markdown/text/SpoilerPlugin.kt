package co.anitrend.support.markdown.text

import androidx.annotation.VisibleForTesting
import co.anitrend.support.markdown.core.IMarkdownPlugin
import io.noties.markwon.AbstractMarkwonPlugin
import java.lang.reflect.Modifier

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
class SpoilerPlugin private constructor(): IMarkdownPlugin, AbstractMarkwonPlugin() {

    /**
     * Regular expression that should be used for the implementing classing
     */
    @VisibleForTesting(otherwise = Modifier.PRIVATE)
    override val regex = Regex(
        pattern = PATTERN_SPOILER,
        option = RegexOption.IGNORE_CASE
    )

    override fun processMarkdown(markdown: String): String {
        val pattern = regex.toPattern()
        return markdown.replace(regex, "")
    }

    companion object {

        @VisibleForTesting(otherwise = Modifier.PRIVATE)
        const val PATTERN_SPOILER = "~!(.*?)!~"

        fun create() =
            SpoilerPlugin()
    }
}