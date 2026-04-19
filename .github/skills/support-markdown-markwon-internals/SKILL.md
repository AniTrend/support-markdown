---
name: support-markdown-markwon-internals
description: "Deep reference for Markwon plugin internals, commonmark-java AST extension points, Android Spanned rendering, and AniList-Flavored Markdown syntax. Use when implementing new plugins, debugging rendering pipelines, choosing the correct hook layer (processMarkdown vs configureParser vs configureVisitor vs beforeSetText), or understanding how commonmark nodes become Android spans."
argument-hint: "Describe the rendering problem, syntax feature, or hook layer you need to understand or implement"
---

# Support Markdown Markwon Internals

## What This Skill Produces

- Precise hook selection for where a given behavior belongs in the pipeline.
- A grounded understanding of how commonmark-java AST nodes become Android `Spanned` text.
- Correct span type selection from `android.text.style` for custom rendering.
- Repo-specific plugin structural patterns to follow for new work.

## When To Use

- Implementing a new `AbstractMarkwonPlugin` and unsure which method to override.
- Debugging a rendering issue where text appears wrong or a span is not applied.
- Deciding between a text-rewrite approach (`processMarkdown`) and a parser extension (`configureParser`).
- Writing or reading custom `Node`/`CustomNode` types in the commonmark-java AST.
- Understanding why Markwon uses `Spanned` instead of HTML rendering on Android.
- Choosing the right `android.text.style` span class for a new visual effect.
- Understanding AniList-specific syntax (`~!spoiler!~`, `youtube(...)`, `webm(...)`, `img###(...)`, `@mention`, `~~~center~~~`) and which plugin owns each feature.

## Layered Architecture

```
Raw markdown string
        |
        v
[1] processMarkdown()          ← text-to-text rewrite (regex replacement)
        |
        v
[2] commonmark-java Parser     ← String → AST (Node tree)
    configureParser()          ← register extensions, block/inline factories
        |
        v
[3] beforeRender(Node)         ← inspect or mutate AST before visit
        |
        v
[4] MarkwonVisitor             ← Node tree → SpannableStringBuilder
    configureVisitor()         ← register NodeVisitor per node type
    configureSpansFactory()    ← define what span(s) each Node produces
        |
        v
[5] afterRender(Node, visitor) ← post-visit cleanup
        |
        v
[6] Spanned (result)
        |
        v
[7] beforeSetText(TextView, Spanned) ← pre-TextView handoff (unschedule old spans)
        |
        v
[8] textView.setText(spanned)
        |
        v
[9] afterSetText(TextView)     ← schedule new AsyncDrawableSpans, etc.
```

## Hook Decision Table

| Problem shape | Correct hook | Repo example |
|---|---|---|
| Text can be rewritten before parsing (regex-safe, no AST needed) | `processMarkdown` | `MentionPlugin`, `SpoilerPlugin` |
| New syntax should create AST nodes for proper rendering | `configureParser` + `configureVisitor` | any `StrikethroughExtension`-style |
| Existing AST node needs different or additional spans | `configureSpansFactory` | `EmphasisPlugin`, `HeadingPlugin` |
| Custom AST nodes need a visitor registration | `configureVisitor` | `SpoilerPlugin` via `SpoilerNode` |
| Another plugin must be required before this one runs | `configure(registry)` | `CorePlugin` HTML handler wiring |
| HTML tags need custom handling | `configure(registry)` + `HtmlPlugin` tag handler | `CenterTagHandler`, `AlignTagHandler` |
| TextView lifecycle (schedule/unschedule async drawables) | `beforeSetText` / `afterSetText` | `ImagePlugin` |
| Inspect or patch AST after parsing but before rendering | `beforeRender(Node)` | rarely needed; prefer parser-level |

## Repo Plugin Pattern

All plugins in this repo follow this structure:

```kotlin
class MyPlugin private constructor(/* config */) : IMarkdownPlugin, AbstractMarkwonPlugin() {

    // Required by IMarkdownPlugin: the Regex this plugin matches against
    override val regex = Regex(PATTERN, RegexOption.IGNORE_CASE)

    // Companion factory — always named create()
    companion object {
        fun create(/* config */) = MyPlugin(/* config */)
    }
}
```

- `IMarkdownPlugin` is the repo contract in `co.anitrend.support.markdown.common`. It requires a `regex` property.
- Controller classes live in `controller/` subpackage and hold reusable logic separate from the plugin wiring.
- Span classes live in `span/` subpackage when a plugin introduces its own span types.
- Node classes live in `node/` subpackage for custom AST nodes.
- Render helpers live in `render/` subpackage (see `spoiler/render/`).

## References

- [Markwon plugin lifecycle](./references/markwon-lifecycle.md)
- [commonmark-java AST and extension points](./references/commonmark-ast.md)
- [Android span types for custom rendering](./references/android-spans.md)
- [AniList-Flavored Markdown specification](./references/anilist-markdown-spec.md)
