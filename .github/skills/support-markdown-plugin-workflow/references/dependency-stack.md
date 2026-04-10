# Dependency Stack

Use this map before choosing an implementation strategy.

## Current `:markdown` Runtime Surface

`markdown/build.gradle.kts` currently pulls these Markwon modules:

| Alias | Artifact | What it exposes to this repo |
| --- | --- | --- |
| `markwon-core` | `io.noties.markwon:core` | `Markwon`, `AbstractMarkwonPlugin`, visitor and spans lifecycle |
| `markwon-editor` | `io.noties.markwon:editor` | Editor-oriented helpers; not the first choice for parser behavior |
| `markwon-html` | `io.noties.markwon:html` | `HtmlPlugin`, `HtmlTag`, and custom tag handling |
| `markwon-coil` | `io.noties.markwon:image-coil` | Coil-backed image loading integration |
| `markwon-parser` | `io.noties.markwon:inline-parser` | Inline parser support layered onto Markwon's commonmark pipeline |
| `markwon-linkify` | `io.noties.markwon:linkify` | Linkification support |
| `markwon-simple-ext` | `io.noties.markwon:simple-ext` | Simple delimiter-style extensions |
| `markwon-syntax-highlight` | `io.noties.markwon:syntax-highlight` | Syntax highlighting |
| `markwon-ext-tasklist` | `io.noties.markwon:ext-tasklist` | Task-list parsing and rendering |
| `markwon-ext-strikethrough` | `io.noties.markwon:ext-strikethrough` | GFM strikethrough support |
| `markwon-ext-tables` | `io.noties.markwon:ext-tables` | GFM tables support |

Markwon sits on top of commonmark-java. Parser-level features normally enter this repo through `AbstractMarkwonPlugin.configureParser`, which configures the underlying `org.commonmark.parser.Parser.Builder`.

## Extension Point Decision Table

| Problem shape | Preferred hook | Why |
| --- | --- | --- |
| Text can be normalized before markdown parsing | `processMarkdown` | Matches the repo's current rewrite-heavy plugins such as mentions and spoilers |
| New inline delimiter or block syntax should produce AST nodes | `configureParser` with commonmark-java extensions or custom parsers | Keeps syntax recognition in the parser instead of string rewriting |
| Existing AST nodes need different span output | `configureVisitor` or `configureSpansFactory` | Works at render time without inventing new transport syntax |
| HTML tags must be supported or customized | `configure(registry)` and `registry.require(HtmlPlugin::class.java)` | Aligns with `CorePlugin` and Markwon's HTML integration |
| Final view cleanup is needed after rendering | `beforeSetText` | Use only for `TextView` handoff concerns |

## Existing Repo Examples

- `CorePlugin` uses `configureVisitor` for `SoftLineBreak` and `configure(registry)` for `HtmlPlugin` handlers.
- `MentionPlugin` rewrites raw markdown in `processMarkdown` before parsing.
- `SpoilerPlugin` rewrites AniList spoiler syntax into links in `processMarkdown`.

## Dependency Guidance

- Prefer existing Markwon modules before adding new parser or renderer dependencies.
- If you need a commonmark-java feature that is not already reachable through the current Markwon surface, add the direct dependency explicitly in `gradle/libs.versions.toml` and the module build file.
- Keep parser concerns in `:markdown`; never rely on `:app` types to make a plugin work.