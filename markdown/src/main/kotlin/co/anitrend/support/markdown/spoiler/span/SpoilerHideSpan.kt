package co.anitrend.support.markdown.spoiler.span

import android.text.TextPaint
import android.text.style.CharacterStyle

class SpoilerHideSpan : CharacterStyle() {
    override fun updateDrawState(textPaint: TextPaint?) {
        textPaint?.color = 0
    }
}