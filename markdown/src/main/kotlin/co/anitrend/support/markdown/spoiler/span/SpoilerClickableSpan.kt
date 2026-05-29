package co.anitrend.support.markdown.spoiler.span

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import androidx.annotation.ColorInt

/**
 * Clickable span that reveals spoiler content on tap.
 *
 * Sets [SpoilerSpan.isShown] to `true` and invalidates the view so
 * the spoiler transitions to its revealed visual state.
 */
class SpoilerClickableSpan(
    private val spoilerSpan: SpoilerSpan
) : ClickableSpan() {

    /**
     * Performs the click action associated with this span.
     */
    override fun onClick(widget: View) {
        spoilerSpan.isShown = true
        widget.postInvalidateOnAnimation()
    }

    override fun updateDrawState(textPaint: TextPaint) {
        textPaint.isUnderlineText = false
    }
}