# markdown/src/main/kotlin/co/anitrend/support/markdown/horizontal/

## Responsibility

Provides a compatibility plugin entry point for horizontal rules and thematic breaks.

## Design

`HorizontalLinePlugin` is intentionally a no-op `AbstractMarkwonPlugin`. Commonmark and Markwon core already handle `---`, `***`, and `___` thematic break syntax.

## Flow

Installing the plugin leaves markdown preprocessing, parsing, and visiting unchanged. Horizontal rule parsing continues through Markwon core behavior.

## Integration

Retained for backward compatible plugin composition where callers include a dedicated horizontal line feature plugin.
