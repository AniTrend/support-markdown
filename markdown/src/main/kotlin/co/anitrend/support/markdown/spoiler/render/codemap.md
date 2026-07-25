# markdown/src/main/kotlin/co/anitrend/support/markdown/spoiler/render/

## Responsibility

Provides a legacy SpanFactory bridge for spoiler span creation.

## Design

`SpoilerRender` implements Markwon `SpanFactory` and returns a `SpoilerSpan` configured with text and background colors. Current primary rendering is performed directly by `SpoilerPlugin` visitor logic.

## Flow

When invoked as a span factory, it touches the configured `Text` span factory, then creates and returns a new `SpoilerSpan` for the render props.

## Integration

Retained for compatibility with older span-factory-based spoiler rendering paths. Shares the same `SpoilerSpan` type used by the current plugin.
