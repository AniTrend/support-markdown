# markdown/src/main/kotlin/co/anitrend/support/markdown/webm/

## Responsibility

Converts AniList `webm(URL)` custom syntax into a clickable media preview.

## Design

`WebMPlugin` uses case-insensitive string preprocessing. It replaces matching syntax with an HTML anchor containing an image whose source is the same resource URL.

## Flow

`processMarkdown` finds WebM matches, extracts the resource URL from the final capture group, and replaces the original token with `<a href="url"><img src="url" /></a>`.

## Integration

Relies on Markwon HtmlPlugin and image/link span handling after preprocessing. It does not register parser or visitor hooks.
