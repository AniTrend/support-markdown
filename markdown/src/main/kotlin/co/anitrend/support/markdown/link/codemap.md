# markdown/src/main/kotlin/co/anitrend/support/markdown/link/

## Responsibility

Adds automatic linking for bare `http`, `https`, and `ftp` URLs in markdown text.

## Design

`LinkifyPlugin` is an internal `AbstractMarkwonPlugin` that performs string-level preprocessing. It wraps matched URLs in HTML anchor tags and leaves rendering to HtmlPlugin.

## Flow

`processMarkdown` scans the markdown with `PATTERN_LINK`, then replaces each matched URL with `<a href="url">url</a>`. Later Markwon HTML processing converts the anchor into link spans.

## Integration

Requires HtmlPlugin or equivalent link handling after preprocessing. It does not modify the parser or visitor directly.
