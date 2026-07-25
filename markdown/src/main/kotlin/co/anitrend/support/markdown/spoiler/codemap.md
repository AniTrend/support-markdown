# markdown/src/main/kotlin/co/anitrend/support/markdown/spoiler/

## Responsibility

Implements click-to-reveal spoiler rendering for AniList `~!spoiler!~` syntax.

## Design

`SpoilerPlugin` renders `SpoilerNode` ranges with three cooperating spans: `SpoilerSpan` stores revealed state and colors, `SpoilerClickableSpan` reveals content on tap, and `SpoilerHideSpan` hides unrevealed text. Child packages contain the node, render bridge, and span implementations.

## Flow

`TildeDelimiterProcessor` creates `SpoilerNode` from valid `~!...!~` delimiters. The visitor renders children, creates the spoiler spans, and applies them over the rendered range. Tapping the clickable span flips the shared `SpoilerSpan` state and invalidates the view.

## Integration

Depends on `CorePlugin` registering the shared tilde delimiter processor. Uses Android text spans and Markwon visitor callbacks. See `node`, `span`, and `render` child maps for helper details.
