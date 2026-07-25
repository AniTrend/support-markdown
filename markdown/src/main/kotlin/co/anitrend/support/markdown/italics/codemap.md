# markdown/src/main/kotlin/co/anitrend/support/markdown/italics/

## Responsibility

Provides italic text rendering for standard commonmark emphasis nodes.

## Design

`ItalicsPlugin` handles native commonmark-java `Emphasis` nodes instead of scanning text manually. It applies Android `StyleSpan(Typeface.ITALIC)` over rendered emphasis content.

## Flow

Commonmark parses `*text*` or `_text_` into `Emphasis`. The Markwon visitor records the start offset, renders children, and then spans the rendered range as italic.

## Integration

Integrates directly with Markwon visitor configuration and Android text styling. It depends on commonmark core parsing, not custom delimiter processors.
