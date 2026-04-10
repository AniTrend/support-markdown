# Module Reference Map

Use this map to place code before searching for a specific file.

## Modules

| Module | Depends on | Use for | Dokka |
| --- | --- | --- | --- |
| `:markdown` | Markwon core/html/inline-parser/linkify/simple-ext/ext-tasklist/ext-strikethrough/ext-tables/syntax-highlight, Coil, BetterLinkMovementMethod, commonmark-java via Markwon | All markdown parsing and rendering plugins | `https://anitrend.github.io/support-markdown/` |
| `:app` | `:markdown` | Sample app only — not published, not a dependency target | n/a |
| `buildSrc` | build tooling only | Shared Gradle conventions, Android defaults, publishing | n/a |

## Dependency Layers

| Layer | Primary APIs | Use when |
| --- | --- | --- |
| Pre-parse rewrite | `AbstractMarkwonPlugin.processMarkdown` | Raw text can be normalized before parsing |
| Parser and AST | `AbstractMarkwonPlugin.configureParser`, commonmark-java extensions | Syntax should be recognized structurally, not by ad-hoc string replacement |
| HTML bridge | `HtmlPlugin`, custom tag handlers | Markdown input carries HTML tags that need support or customization |
| Render and spans | `MarkwonVisitor.Builder`, `MarkwonSpansFactory.Builder` | Parsed nodes need custom rendering |
| View handoff | `beforeSetText` | Final `TextView` cleanup or scheduling concerns |

## Plugin Packages In `:markdown`

All plugin code lives under `co.anitrend.support.markdown` in `markdown/src/main/kotlin/`:

| Package | Key types | Handles |
| --- | --- | --- |
| `core/` | `CorePlugin` | Main entry point; wires all plugins into a `Markwon` instance |
| `common/` | `IMarkdownPlugin`, `MarkdownPluginController` | Shared plugin interface and base controller |
| `center/` | `CenterPlugin`, `CenterPluginController` | `~~~centered text~~~` syntax |
| `emphasis/` | `EmphasisPlugin` | Bold and italic emphasis rendering |
| `heading/` | `HeadingPlugin` | ATX headings (`#`, `##`, …) |
| `horizontal/` | `HorizontalLinePlugin` | Horizontal rules (`---`) |
| `html/` | `CenterTagHandler`, `AlignTagHandler` | HTML tag pass-through for center and align tags |
| `image/` | `ImagePlugin`, `ImagePluginController` | Inline images via Coil |
| `italics/` | `ItalicsPlugin` | AniList-style italic handling |
| `link/` | `LinkifyPlugin` | Link detection via BetterLinkMovementMethod |
| `mention/` | `MentionPlugin`, `MentionPluginController`, `OnMentionTextAddedListener` | `@username` mention detection and callback |
| `spoiler/` | `SpoilerPlugin`, `controller/`, `node/`, `render/`, `span/` | Spoiler tag with toggle behavior |
| `strike/` | `StrikeThroughPlugin` | Strikethrough text |
| `webm/` | `WebMPlugin`, `WebMPluginController` | Inline WebM video embedding |
| `youtube/` | `YouTubePlugin`, `YoutubePluginController` | YouTube video embedding |

## Placement Heuristics

- New AniList markdown syntax element: create a new top-level package following the existing pattern (`<feature>/`, plugin class, optional `controller/` subpackage, and any supporting `node/`, `render/`, `span/` types).
- Shared plugin contracts or base controller behavior: `common/`.
- HTML tag handling (center, align, custom tags): `html/`.
- Shared entry point changes (adding a new plugin to the chain): `core/CorePlugin`.
- Build convention changes affecting all modules: `buildSrc`.

## Consumer Notes

- Consumers typically import `CorePlugin` or individual plugin classes and configure them via the `Markwon.builder()` API.
- `OnMentionTextAddedListener` is the primary extension point for apps that handle `@mention` text.
- Markwon plugin hooks ultimately sit on top of commonmark-java parsing, so parser decisions are separate from package-placement decisions.
- Favor documenting external extension points clearly because downstream apps configure and extend these plugins at runtime.
- If a change affects a public type, assume the Dokka page is part of the deliverable.
