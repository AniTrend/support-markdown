# Android Span Types for Custom Rendering

Sources:
- https://developer.android.com/reference/android/text/package-summary
- https://developer.android.com/reference/android/text/style/package-summary

## Why Markwon Uses Spanned

Android does not have a DOM. The output of Markwon's rendering pipeline is a `Spanned` — specifically a `SpannableStringBuilder` built up during the `MarkwonVisitor` traversal. `Spanned` is the `CharSequence` interface for text with markup objects (spans) attached to character ranges. `TextView` renders these spans natively, so Markwon avoids WebView entirely.

## Key Interfaces

| Interface | Role |
|---|---|
| `Spanned` | Read-only: text + markup at character ranges |
| `Spannable` | Mutable markup (can attach/detach spans) |
| `Editable` | Mutable both text and markup |
| `SpannableStringBuilder` | Primary mutable implementation used by Markwon internally |
| `SpannedString` | Immutable snapshot; Markwon's final output type |

Spans are attached via:
```kotlin
spannable.setSpan(spanObject, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
```

The flag controls whether newly inserted text at the boundaries is included in the span.

## Span Flag Constants

| Flag | Behaviour |
|---|---|
| `SPAN_EXCLUSIVE_EXCLUSIVE` | New text at start/end NOT included (most common for inline spans) |
| `SPAN_INCLUSIVE_INCLUSIVE` | New text at either boundary included |
| `SPAN_EXCLUSIVE_INCLUSIVE` | New text at end included (useful for trailing cursor spans) |
| `SPAN_MARK_MARK` | Aliases inclusive-inclusive |

## Span Taxonomy

### Character-level spans (`CharacterStyle`)

Affect individual characters without changing line metrics.

| Class | Effect | Markwon usage |
|---|---|---|
| `ForegroundColorSpan` | Text color | Links, colored elements |
| `BackgroundColorSpan` | Background behind characters | Spoiler hide, code background |
| `StyleSpan(Typeface.BOLD)` | Bold | `StrongEmphasis` |
| `StyleSpan(Typeface.ITALIC)` | Italic | `Emphasis` |
| `UnderlineSpan` | Underline | Links |
| `StrikethroughSpan` | Strikethrough | GFM strikethrough |
| `ClickableSpan` | Tap target + `onClick` | Links, spoilers, mentions |
| `URLSpan` | Clickable URL (subclass of `ClickableSpan`) | Autolinks |
| `ImageSpan` | Replace text with a `Drawable` | Images |
| `TypefaceSpan` | Change typeface/font-family | Code spans |
| `AbsoluteSizeSpan` | Set text size in px | Headings |
| `RelativeSizeSpan` | Scale text size | Headings (relative) |
| `SuperscriptSpan` / `SubscriptSpan` | Baseline shift | Math-style text |
| `MaskFilterSpan` | Apply `MaskFilter` (e.g. blur) | Special effects |

### Metric-affecting spans (`MetricAffectingSpan`)

Subclass of `CharacterStyle`. Change character dimensions, triggering layout recalculation.

- `AbsoluteSizeSpan`, `RelativeSizeSpan`, `ScaleXSpan` — sizing
- `TextAppearanceSpan` — apply a full `TextAppearance` style
- `TypefaceSpan` — font family

### Paragraph-level spans (`ParagraphStyle`)

Applied to whole paragraphs (from newline to newline). Do not mix with character spans.

| Class | Effect | Markwon usage |
|---|---|---|
| `AlignmentSpan.Standard` | Text alignment (ALIGN_CENTER, etc.) | `CenterPlugin` |
| `QuoteSpan` | Vertical stripe at paragraph start | Blockquotes |
| `BulletSpan` | Bullet point marker | Unordered lists |
| `LeadingMarginSpan.Standard` | Indent left margin | Ordered/unordered lists |
| `LineHeightSpan.Standard` | Paragraph line height | Spacing adjustments |

### Replacement spans (`ReplacementSpan`)

Replace an entire span of text with custom drawing. Subclass `ReplacementSpan` when you want full control over measurement and drawing.

```kotlin
class MyReplacementSpan : ReplacementSpan() {
    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        return paint.measureText(text, start, end).toInt()
    }
    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int,
                      x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        canvas.drawText(text, start, end, x, y.toFloat(), paint)
    }
}
```

Used by Markwon for `AsyncDrawableSpan` (images), tables, and other block-level replacements.

## Span Interaction with Markwon

In `configureSpansFactory`, the return value of `SpanFactory.getSpans()` can be:

- A single span object
- An `Array<Any>` of multiple spans (all applied to the same range)
- `null` (no spans applied)

```kotlin
builder.setFactory(Heading::class.java) { config, props ->
    val level = CoreProps.HEADING_LEVEL.require(props)
    arrayOf(
        AbsoluteSizeSpan(headingSizeForLevel(level), true),
        StyleSpan(Typeface.BOLD)
    )
}
```

`RenderProps` carries node-specific data (e.g. heading level, link href) from the visitor to the factory. Access with `CoreProps.HEADING_LEVEL.require(props)` or define your own `Prop<T>` keys.

## Custom Clickable Spans

Subclass `ClickableSpan` for tap-to-reveal or tap-to-navigate behaviour:

```kotlin
class SpoilerClickableSpan(private val spoilerSpan: SpoilerSpan) : ClickableSpan() {
    override fun onClick(widget: View) {
        // toggle spoiler visibility
        spoilerSpan.toggle()
        widget.invalidate()
    }
    override fun updateDrawState(ds: TextPaint) {
        // suppress default underline/color from ClickableSpan
        ds.isUnderlineText = false
    }
}
```

`ClickableSpan` requires the `TextView` to use `LinkMovementMethod` (or `BetterLinkMovementMethod` as used by `LinkifyPlugin`).

## Lifecycle Caution: AsyncDrawableSpan

For spans that hold references to loading async resources (images):

- In `beforeSetText`: unschedule old spans so they can be GC'd.
- In `afterSetText`: schedule new spans to start loading.

Never hold a strong reference to a `TextView` from inside a span — use weak references or the scheduler pattern that `ImagePlugin` uses.

## Key Span Flags Recap for Markwon Spans

Markwon's `MarkwonVisitor` applies spans with `SPAN_EXCLUSIVE_EXCLUSIVE` by default via `setSpansForNodeOptional`. Only override the flag when inserting cursor-tracking or growing spans.
