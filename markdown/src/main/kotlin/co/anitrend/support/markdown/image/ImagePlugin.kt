package co.anitrend.support.markdown.image

import io.noties.markwon.*
import io.noties.markwon.core.CorePlugin
import io.noties.markwon.image.ImageProps
import io.noties.markwon.image.ImageSize
import org.commonmark.node.Image

class ImagePlugin private constructor() : AbstractMarkwonPlugin() {

    private val regex = Regex(
        pattern = PATTERN_IMAGE,
        option = RegexOption.IGNORE_CASE
    )

    override fun configure(registry: MarkwonPlugin.Registry) {
        registry.require(CorePlugin::class.java) { plugin: CorePlugin ->
            plugin.addOnTextAddedListener { visitor, text, start ->
                constructImageHandler(visitor, text, start)
            }
        }
    }

    private fun getImageSize(imageSize: String): ImageSize.Dimension {
        val imageSizeLength = imageSize.length
        return when {
            imageSize.endsWith('%') -> {
                val percentageSize = imageSize.substring(0, imageSizeLength - 1)
                percentageSize.toFloatOrNull()?.let { value ->
                    when {
                        value <= 10 -> ImageSize.Dimension(value.times(2), "%")
                        value <= 30 -> ImageSize.Dimension(value.times(1.5f), "%")
                        value <= 100 -> ImageSize.Dimension(value, "%")
                        else -> null
                    }
                }
            }
            imageSize.endsWith("px") -> {
                val pixelSize = imageSize.substring(0, imageSizeLength - 2)
                pixelSize.toFloatOrNull()?.let { value ->
                    when {
                        value <= 100 -> ImageSize.Dimension(value.times(2), "%")
                        value <= 300 -> ImageSize.Dimension(value.times(1.5f), "%")
                        value <= 1000 -> ImageSize.Dimension(value, "px")
                        else -> null
                    }
                }
            }
            else -> {
                imageSize.toFloatOrNull()?.let { value ->
                    when {
                        value <= 200 -> ImageSize.Dimension(value.div(2), "%")
                        value <= 500 -> ImageSize.Dimension(value.div(5), "%")
                        value <= 1000 -> ImageSize.Dimension(value.div(10), "%")
                        else -> null
                    }
                }
            }
        } ?: ImageSize.Dimension(100f, "%")
    }

    private fun applyImageSizeDimensions(imageSize: String, renderProps: RenderProps) {
        val dimension = getImageSize(imageSize)

        ImageProps.IMAGE_SIZE.set(
            renderProps,
            ImageSize(dimension, null)
        )
    }

    private fun constructImageHandler(visitor: MarkwonVisitor, text: String, start: Int) {
        val matches = regex.findAll(text)
        matches.forEach { matchResult ->
            val imageSize = matchResult.groupValues[GROUP_IMAGE_SIZE]
            val imageSource = matchResult.groupValues[GROUP_IMAGE_SRC]
            ImageProps.DESTINATION.set(visitor.renderProps(), imageSource)

            applyImageSizeDimensions(imageSize, visitor.renderProps())

            val spanFactory = visitor.configuration()
                .spansFactory().require(Image::class.java)

            SpannableBuilder.setSpans(
                visitor.builder(),
                spanFactory.getSpans(
                    visitor.configuration(),
                    visitor.renderProps()
                ),
                start + matchResult.range.first,
                start + matchResult.range.last + 1
            )
        }
    }

    override fun processMarkdown(markdown: String): String {
        var replacement = markdown
        val matches = regex.findAll(markdown)
        matches.forEach { matchResult ->
            val imageSize = matchResult.groupValues[GROUP_IMAGE_SIZE]
            val imageSource = matchResult.groupValues[GROUP_IMAGE_SRC]

            val dimensions = getImageSize(imageSize)

            replacement = replacement.replace(
                matchResult.value,
                """<img src="${imageSource.trim()}" width="${dimensions.value.toInt()}${dimensions.unit}" />"""
            )
        }
        return replacement
    }

    companion object {
        private const val PATTERN_IMAGE = "img\\s?(\\d*|\\d*px|\\d*%)?\\s?\\((.[\\S]+)\\)"

        private const val GROUP_IMAGE_SIZE = 1
        private const val GROUP_IMAGE_SRC = 2

        fun create() = ImagePlugin()
    }
}
