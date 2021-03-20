package co.anitrend.support.markdown.image

import co.anitrend.support.markdown.common.IMarkdownPlugin
import io.noties.markwon.*
import io.noties.markwon.core.CorePlugin
import io.noties.markwon.image.ImageProps
import io.noties.markwon.image.ImageSize
import org.commonmark.node.Image

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
    override val regex = Regex(
        pattern = PATTERN_IMAGE,
        option = RegexOption.IGNORE_CASE
    )

    override fun configure(registry: MarkwonPlugin.Registry) {
        registry.require(CorePlugin::class.java) { corePlugin: CorePlugin ->
            corePlugin.addOnTextAddedListener { visitor, text, start ->
                constructImageHandler(visitor, text, start)
            }
        }
    }

    private fun applyImageSizeDimensions(imageSize: String, renderProps: RenderProps) {
        val imageSizeLength = imageSize.length
        val dimension: ImageSize.Dimension = when {
            imageSize.endsWith('%') -> {
                val percentageSize = imageSize.substring(0, imageSizeLength - 1)
                percentageSize.toFloatOrNull()?.let { value ->
                    if (value <= 100)
                        ImageSize.Dimension(value, "%")
                    else null
                }
            }
            imageSize.endsWith("px") -> {
                val pixelSize = imageSize.substring(0, imageSizeLength - 2)
                pixelSize.toFloatOrNull()?.let { value ->
                    if (value <= 1000)
                        ImageSize.Dimension(value, "px")
                    else null
                }
            }
            else -> {
                imageSize.toFloatOrNull()?.let { value ->
                    if (value <= 1000)
                        ImageSize.Dimension(value.div(10), "%")
                    else null
                }
            }
        } ?: ImageSize.Dimension(100f, "%")

        ImageProps.IMAGE_SIZE.set(
            renderProps,
            ImageSize(
                dimension,
                null
            )
        )
    }

    private fun constructImageHandler(visitor: MarkwonVisitor, text: String, start: Int) {
        val matches = regex.findAll(text)
        matches.forEach { matchResult ->
            val imageSize = matchResult.groupValues[GROUP_IMAGE_SIZE]
            val imageSource = matchResult.groupValues[GROUP_IMAGE_SRC]
            ImageProps.DESTINATION.set(visitor.renderProps(), imageSource)

            applyImageSizeDimensions(imageSize, visitor.renderProps())

            // it's important to use `start` parameter for correct spans placement
            // oops, cannot use that here
            // visitor.setSpansForNode(Image::class.java, start + matchResult.start())

            val matchRange = matchResult.range

            val spanFactory = visitor.configuration()
                .spansFactory().require(Image::class.java)

            SpannableBuilder.setSpans(
                visitor.builder(),
                spanFactory.getSpans(
                    visitor.configuration(),
                    visitor.renderProps()
                ),
                start + matchRange.first,
                start + matchRange.last + 1
            )
        }
    }

    companion object {
        private const val PATTERN_IMAGE = "(img(\\d*|\\d*px|\\d*%))\\((.+)\\)"

        private const val GROUP_IMAGE_SIZE = 2
        private const val GROUP_IMAGE_SRC = 3

        fun create() =
            ImagePlugin()
    }
}