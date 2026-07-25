# app/src/main/kotlin/co/anitrend/support/markdown/core/extensions/

## Responsibility

Adds markdown rendering and cleanup helpers for `AppCompatTextView` in the sample feed rows.

## Design

`setMarkdown` installs `BetterLinkMovementMethod`, registers click and long click listeners, then delegates markdown parsing and span application to a supplied `Markwon` instance. `onDestroy` clears BetterLinkMovementMethod listeners when views are recycled.

## Flow

Feed row binding calls `setMarkdown(markwon, text)` after selecting the feed text fallback. RecyclerView recycling calls `onDestroy()` before the view holder is reused.

## Integration

Called from `FeedAdapter.ViewHolder`. Depends on Markwon for markdown rendering, BetterLinkMovementMethod for link handling, and Android Toasts for sample link click feedback.
