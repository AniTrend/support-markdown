# markdown/src/main/kotlin/co/anitrend/support/markdown/center/

## Responsibility

Implements centered text support for AniList markdown syntax. It recognizes inline `~~~text~~~` through preprocessing and renders the content with centered Android text alignment.

## Design

`CenterPlugin` rewrites inline legacy tildes to `+++text+++` because line-start `~~~` conflicts with fenced code blocks. `PlusDelimiterProcessor` parses exact triple plus delimiters into `CenterNode` instances.

## Flow

`processMarkdown` converts single-line center spans from tildes to pluses. The parser processor wraps delimited children in `CenterNode`. The visitor renders children and applies `AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER)` over the produced range.

## Integration

Works with commonmark parser delimiter processing and Markwon visitor spans. `TildeDelimiterProcessor` can also emit `CenterNode` for triple tilde runs when commonmark does not consume them as code fences.
