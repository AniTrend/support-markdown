# markdown/src/main/kotlin/co/anitrend/support/markdown/ephasis/

## Responsibility

Provides bold text rendering for commonmark strong emphasis nodes. The package name is `ephasis`, while the plugin handles standard emphasis behavior.

## Design

`EmphasisPlugin` hooks into native commonmark-java `StrongEmphasis` nodes instead of parsing bold syntax with a regex. It applies Android `StyleSpan(Typeface.BOLD)` to the rendered child range.

## Flow

Commonmark parses `**text**` or `__text__` into `StrongEmphasis`. The Markwon visitor records the current span start, visits children, then applies the bold span from start to the new visitor length.

## Integration

Integrates with Markwon visitor configuration and Android text styling. It is independent of the shared delimiter processor because commonmark already owns strong emphasis parsing.
