package co.anitrend.support.markdown.spoiler.span

import android.text.TextPaint
import android.text.style.CharacterStyle
import androidx.annotation.ColorInt
import io.noties.markwon.utils.ColorUtils

/**
 * Character style that controls spoiler visibility.
 *
 * When [isShown] is `false` the text is hidden behind a colored background.
 * When revealed (`true`) the background shifts to a light tint of the text color.
 */
class SpoilerSpan(
    @ColorInt private val textColor: Int,
    @ColorInt private val backgroundColor: Int
) : CharacterStyle() {

    var isShown: Boolean = false

    override fun updateDrawState(textPaint: TextPaint?) {
        if (!isShown) {
            textPaint?.color = textColor
            textPaint?.bgColor = backgroundColor
        } else {
            textPaint?.bgColor = ColorUtils.applyAlpha(textColor, 25)
        }
    }
}