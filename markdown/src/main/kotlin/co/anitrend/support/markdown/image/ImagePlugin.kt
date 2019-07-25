package co.anitrend.support.markdown.image

import androidx.annotation.VisibleForTesting
import co.anitrend.support.markdown.core.IMarkdownPlugin
import io.noties.markwon.AbstractMarkwonPlugin
import java.lang.reflect.Modifier

/**
 * Images look like links, but with a `!` in front:
 * __`![fallback text](https://anilist.co/img/icons/icon.svg)`__
 *
 * You can also use HTML:
 * __<img alt="fallback text" src="https://anilist.co/img/icons/icon.svg">__
 *
 * There's also an (Anilist-specific) way to specify a size:
 * __img###(https://anilist.co/img/icons/icon.svg) where `###` is the width in pixels, such as `420`__
 *
 * Note that the `img` code is __always__ converted (even within code blocks) so be careful when trying to explain how it works to other users!
 *
 * @since 0.1.0
 */
class ImagePlugin private constructor(): IMarkdownPlugin, AbstractMarkwonPlugin() {

    /**
     * Regular expression that should be used for the implementing classing
     */
    @VisibleForTesting(otherwise = Modifier.PRIVATE)
    override val regex = Regex(
        pattern = PATTERN_IMAGE,
        option = RegexOption.IGNORE_CASE
    )


    override fun processMarkdown(markdown: String): String {
        val pattern = regex.toPattern()
        return markdown.replace(regex, """<img width="" href=""/>""")
    }

    companion object {

        @VisibleForTesting(otherwise = Modifier.PRIVATE)
        const val PATTERN_IMAGE = "(img).*?(\\([^)]+\\))"

        fun create() =
            ImagePlugin()
    }
}