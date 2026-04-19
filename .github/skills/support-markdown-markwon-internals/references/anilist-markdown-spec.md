# AniList-Flavored Markdown Specification

Source: https://files.kiniro.uk/anilist-flavored-markdown-v1.md

AniList's format is CommonMark-based with AniList-specific extensions. This file is the authoritative reference for what the `:markdown` library must handle.

---

## Standard CommonMark Features (supported as-is)

These are handled by commonmark-java and the standard Markwon modules.

| Feature | Syntax | Notes |
|---|---|---|
| Italic | `*text*` or `_text_` or `<i>` / `<em>` | Known AniList bug: `_` fails for < 3 chars (`_1_` fails, `_123_` works) |
| Bold | `**text**` or `__text__` or `<b>` / `<strong>` | Can be combined: `_**hello**_` |
| Strikethrough | `~~text~~` or `<del>` / `<strike>` | GFM extension, handled by `markwon-ext-strikethrough` |
| Headings (ATX) | `# H1` … `##### H5` | AniList **does not support H6** — `######` or `<h6>` is rejected |
| Headings (Setext) | `text\n==` (H1) or `text\n--` (H2) | At least 2 `=` or `-` chars required |
| Horizontal rule | `---`, `***`, `- - -`, `* * *` or `<hr>` | Must have a blank line on each side to avoid header ambiguity |
| Links | `[text](url)` or `<a href="...">` | Opens in new tab by default on AniList web |
| Images | `![alt](url)` or `<img alt="..." src="...">` | HTTP images auto-upgraded to HTTPS |
| Inline code | `` `code` `` | Markdown does not render inside backtick code |
| Code blocks | 4-space indent, ` ``` ` fenced, or `<pre>` | Markdown does not render inside fenced code either |
| Blockquote | `> text` or `<blockquote>` | Always renders italic on AniList web styling; nestable |
| Unordered list | `-`, `*`, or `+` prefix | Sub-lists with 2-space indent |
| Ordered list | `1.`, `2.` etc. | Numbers don't need to be sequential |
| Escaping | `\*`, `\_`, etc. | Double backslash for literal backslash |

---

## AniList-Specific Extensions

These are **not** part of CommonMark and require custom handling in this library. All of them are converted **even inside code blocks** (AniList web-side known limitation; the library should match this behavior).

### Spoiler text

**Syntax:** `~!hidden content!~`

**HTML fallback (legacy):** `<div rel="spoiler">hidden content</div>` — should still be handled but may be deprecated.

**Behavior:**
- Text is hidden until the user taps/clicks.
- Renders as a "Spoiler, click to view" placeholder with clickable reveal.
- Known interaction bugs with `~~~...~~~` and `<center>...</center>` — spoiler text can appear after the center block rather than inside it.
- Converted even inside code blocks (quirk to preserve parity with AniList web).

**Repo implementation:** `SpoilerPlugin` — `processMarkdown` rewrites `~!...!~` into a link/anchor, then `SpoilerClickableSpan`/`SpoilerHideSpan`/`SpoilerSpan` handle tap-to-reveal.

**Regex pattern:** matches `~!` … `!~` with `IGNORE_CASE`.

---

### Center-aligned text

**Syntax:** `~~~text~~~` or `<center>text</center>`

**Extended alignment via HTML:**
```
<p align="left">...</p>
<p align="center">...</p>
<p align="right">...</p>
<p align="justify">...</p>
<div align="...">...</div>
```

**Behavior:** Renders text with the specified paragraph alignment. `<p align>` and `<div align>` use HTML4 attributes (may break in future AniList web versions).

**Repo implementation:** `CenterPlugin` — `processMarkdown` rewrites `~~~...~~~` into `<center>...</center>`. `CenterTagHandler` and `AlignTagHandler` in `html/` handle the HTML tag pass-through via `HtmlPlugin`.

---

### YouTube video embeds

**Syntax:** `youtube(VIDEO_ID_OR_FULL_URL)`

Examples:
- `youtube(D0q0QeQbw9U)` — short form with video ID only
- `youtube(https://www.youtube.com/watch?v=D0q0QeQbw9U)` — full URL form

**Behavior:** Renders as a clickable thumbnail image. The library uses a placeholder thumbnail (`IMarkdownPlugin.VIDEO_THUMBNAIL_URL`) since there is no YouTube API access in the library layer.

**Repo implementation:** `YouTubePlugin` — `processMarkdown` extracts the video ID and rewrites the syntax as an image link pointing to the thumbnail placeholder.

---

### WebM (and other video) embeds

**Syntax:** `webm(URL)`

Example: `webm(https://files.kiniro.uk/video/sonic.webm)`

**Behavior:** Auto-plays, loops, muted. Despite the name, any audio/video URL is accepted. On Android this renders as an image/placeholder since inline video playback is not a `TextView` concern.

**Repo implementation:** `WebMPlugin` — `processMarkdown` rewrites `webm(...)` into an image embed pointing to the URL.

---

### Image with explicit width

**Syntax:** `img###(URL)` where `###` is width in pixels.

Example: `img420(https://anilist.co/img/icons/icon.svg)`

**Behavior:** Renders the image at the specified pixel width.

**Repo implementation:** `ImagePlugin` / `ImagePluginController` — handles the `img###(...)` syntax and Coil-backed loading.

**Linked image variant:** `[ img###(url) ](link-url)` — spaces around the `img` code are required for correct conversion.

---

### @mentions

**Syntax:** `@username`

**Behavior:** Converted to a clickable link to `https://anilist.co/user/username`. The `OnMentionTextAddedListener` callback notifies consumers so they can handle navigation.

**Repo implementation:** `MentionPlugin` — `processMarkdown` rewrites `@username` into `<a href="https://anilist.co/user/username">@username</a>`. The controller regex lives in `MentionTextAddedController`.

---

## Newlines

AniList treats every newline as a hard line break (unlike CommonMark, which collapses adjacent lines into a paragraph). A single `\n` produces a visible new line.

The library handles this via `ItalicsPlugin` / `EmphasisPlugin` or through Markwon's `SoftLineBreak` visitor — confirm the actual handling by inspecting `CorePlugin.configureVisitor` for `SoftLineBreak`.

---

## Disallowed HTML Tags

AniList web blocks certain tags:
- `<h6>` / `######` headings — not rendered
- `<u>` — underline not supported
- Unspecified additional tags may be filtered server-side

The library should be defensive: if a tag passes through, it should not crash; unknown tags are silently ignored by `HtmlPlugin`.

---

## Unicode / Emoji Caveats

AniList's MySQL backend uses `utf8mb3` (3-byte UTF-8), so code points > U+FFFF (most emoji) cause post truncation on the web. The library itself does not enforce this restriction — it renders whatever `CharSequence` it receives. Consumers are responsible for sanitizing input before display if necessary.

---

## Feature-to-Plugin Mapping (Quick Reference)

| AniList feature | Syntax | Plugin | Hook |
|---|---|---|---|
| Spoiler | `~!...!~` | `SpoilerPlugin` | `processMarkdown` → custom spans |
| Center / Align | `~~~...~~~`, `<center>`, `<p align>` | `CenterPlugin` + `html/` handlers | `processMarkdown` + `HtmlPlugin` |
| YouTube embed | `youtube(...)` | `YouTubePlugin` | `processMarkdown` |
| WebM embed | `webm(...)` | `WebMPlugin` | `processMarkdown` |
| Image with width | `img###(...)` | `ImagePlugin` | `processMarkdown` |
| @mention | `@username` | `MentionPlugin` | `processMarkdown` |
| Strikethrough | `~~...~~` | `StrikeThroughPlugin` | `configureParser` (extension) |
| Headings (H1–H5) | `#`…`#####` | `HeadingPlugin` | `configureSpansFactory` |
| Horizontal rule | `---` etc. | `HorizontalLinePlugin` | `configureVisitor` / `configureSpansFactory` |
| Italic quirk | `_text_` < 3 chars bug | `ItalicsPlugin` / `EmphasisPlugin` | `processMarkdown` / `configureParser` |
