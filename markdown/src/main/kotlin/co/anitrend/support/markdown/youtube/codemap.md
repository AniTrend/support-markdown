# markdown/src/main/kotlin/co/anitrend/support/markdown/youtube/

## Responsibility

Converts AniList `youtube(ID-or-URL)` custom syntax into a clickable video thumbnail.

## Design

`YouTubePlugin` preprocesses markdown with a case-insensitive regex. Helper methods normalize short IDs or youtu.be URLs into full YouTube watch links and derive thumbnail URLs when a video ID is recognized.

## Flow

`processMarkdown` finds YouTube matches, builds the full video link, builds the thumbnail URL or placeholder fallback, then replaces the custom token with an HTML anchor wrapping a full-width thumbnail image.

## Integration

Relies on Markwon HtmlPlugin to render generated `<a>` and `<img>` tags. Thumbnail helpers are visible for tests but the plugin itself only exposes `create()` for normal composition.
