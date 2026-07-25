# markdown/src/main/kotlin/co/anitrend/support/markdown/spoiler/span/

## Responsibility

Contains Android span implementations that control spoiler appearance and reveal behavior.

## Design

`SpoilerSpan` stores reveal state and paints hidden or revealed colors. `SpoilerClickableSpan` mutates that shared state and disables underline drawing. `SpoilerHideSpan` makes text transparent so hidden content is not readable under the overlay.

## Flow

`SpoilerPlugin` applies all three spans to the same text range. Draw state runs through the character styles. User taps go to `SpoilerClickableSpan.onClick`, which sets `SpoilerSpan.isShown` to true and requests view redraw.

## Integration

Used by `SpoilerPlugin` and compatible with Android `TextView` clickable span handling. `SpoilerRender` can also create `SpoilerSpan` instances for legacy render paths.
