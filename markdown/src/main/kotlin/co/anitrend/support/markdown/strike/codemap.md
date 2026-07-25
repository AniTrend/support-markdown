# markdown/src/main/kotlin/co/anitrend/support/markdown/strike/

## Responsibility

Implements strikethrough rendering for `~~text~~` markdown syntax.

## Design

`StrikeThroughNode` is a lightweight commonmark `CustomNode`. `StrikeThroughPlugin` handles that node in Markwon's visitor and applies Android `StrikethroughSpan` over the rendered child range.

## Flow

`TildeDelimiterProcessor` converts paired double tildes into a `StrikeThroughNode`. The plugin visitor records the start offset, visits child content, and spans the resulting text as strikethrough.

## Integration

Depends on `CorePlugin` to register shared tilde parsing. Integrates with Android text styling through Markwon visitor span application.
