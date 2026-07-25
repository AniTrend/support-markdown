# markdown/src/main/kotlin/co/anitrend/support/markdown/

## Responsibility

Package namespace aggregator for the markdown library feature plugins. Child packages provide Markwon plugins and helpers for AniList flavored markdown syntax, Android spans, custom commonmark nodes, and HTML tag handling.

## Design

The package is split by feature. `core` installs shared parser and HTML infrastructure, `common` owns shared delimiter parsing, and feature folders provide focused `AbstractMarkwonPlugin` implementations or helper types. Namespace-only responsibility lives here, with implementation detail in child maps.

## Flow

Consumers compose Markwon with the selected plugins. String preprocessors run through `processMarkdown`, parser plugins create commonmark nodes, visitor hooks convert nodes to spans, and registry hooks connect feature listeners to Markwon core.

## Integration

Integrates with Markwon, commonmark-java, Android text spans, and Markwon HtmlPlugin. See child maps for `core`, `common`, `center`, `ephasis`, `heading`, `horizontal`, `html`, `image`, `italics`, `link`, `mention`, `spoiler`, `strike`, `webm`, and `youtube`.
