package co.anitrend.support.markdown.core.extensions

import android.content.Context
import android.text.util.Linkify
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import io.noties.markwon.Markwon
import me.saket.bettermovementmethod.BetterLinkMovementMethod

fun AppCompatTextView.setMarkdown(markwon: Markwon, text: String) {
    movementMethod = BetterLinkMovementMethod.newInstance().apply {
        setOnLinkClickListener { textView, url ->
            // Handle click or return false to let the framework handle this link.
            Toast.makeText(textView.context, "clicked", Toast.LENGTH_SHORT).show()
            true
        }
        setOnLinkLongClickListener { textView, url ->
            // Handle long-click or return false to let the framework handle this link.
            Toast.makeText(textView.context, "long clicked", Toast.LENGTH_SHORT).show()
            true
        }
    }
    markwon.setMarkdown(this, text)
}

fun AppCompatTextView.onDestroy() {
    with (movementMethod) {
        if (this is BetterLinkMovementMethod) {
            setOnLinkClickListener(null)
            setOnLinkLongClickListener(null)
        }
    }
}