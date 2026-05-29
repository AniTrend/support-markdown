package co.anitrend.support.markdown.spoiler.span

import android.text.TextPaint
import android.text.style.CharacterStyle

/**
 * Character style that renders spoiler text fully transparent when not revealed.
 *
 * Used in combination with [SpoilerSpan] so the text is invisible while the
 * colored overlay is active.
 */
class SpoilerHideSpan : CharacterStyle() {
    override fun updateDrawState(textPaint: TextPaint?) {
        textPaint?.color = 0
    }
}