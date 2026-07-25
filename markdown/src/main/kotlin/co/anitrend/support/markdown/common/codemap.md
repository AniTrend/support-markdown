# markdown/src/main/kotlin/co/anitrend/support/markdown/common/

## Responsibility

Provides shared commonmark parsing helpers. `TildeDelimiterProcessor` interprets tilde delimiter runs used by spoiler, strikethrough, and center syntax.

## Design

The processor implements `DelimiterProcessor` with one opening and closing character, `~`, and dispatches by exact delimiter length. Length 1 creates spoilers, length 2 creates strikethrough, and length 3 creates center nodes.

## Flow

`getDelimiterUse` accepts matching open and close runs of lengths 1, 2, or 3. `process` wraps the nodes between opener and closer into the matching custom node, removes delimiter text, and for spoilers trims required leading and trailing `!` markers.

## Integration

Registered by `CorePlugin.configureParser`. Emits `SpoilerNode`, `StrikeThroughNode`, and `CenterNode`, which are rendered by their feature plugins through Markwon visitor handlers.
