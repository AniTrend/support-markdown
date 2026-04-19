# Markwon Plugin Lifecycle Reference

Source: https://noties.io/Markwon/docs/v4/core/plugins.html

## AbstractMarkwonPlugin Override Points

Override only the methods you need. All are no-ops by default.

### 1. `configure(Registry registry)`

Called before any other hook. Used to declare plugin ordering and to retrieve other plugins by type.

```kotlin
override fun configure(registry: MarkwonPlugin.Registry) {
    registry.require(CorePlugin::class.java) { core ->
        core.addOnTextAddedListener(myListener)
    }
}
```

Use when: this plugin depends on another plugin's API (e.g., registering an `OnTextAddedListener` on `CorePlugin`, or adding HTML tag handlers via `HtmlPlugin`).

### 2. `configureParser(Parser.Builder builder)`

Configures the commonmark-java `Parser` before any markdown is parsed.

```kotlin
override fun configureParser(builder: Parser.Builder) {
    builder.extensions(listOf(StrikethroughExtension.create()))
    // or custom block/inline factories:
    builder.customInlineContentParserFactory(MyInlineParserFactory())
}
```

Use when: new syntax needs a parser-level representation in the AST (e.g., a new delimiter, a new block type). Avoid calling `super` — there is nothing to call.

### 3. `configureTheme(MarkwonTheme.Builder builder)`

Adjusts the core theme (colors, sizes for headings, code blocks, blockquotes, links).

```kotlin
override fun configureTheme(builder: MarkwonTheme.Builder) {
    builder.codeTextColor(Color.BLACK)
}
```

Use when: changing visual defaults for built-in elements without replacing spans entirely.

### 4. `configureConfiguration(MarkwonConfiguration.Builder builder)`

Configures cross-cutting services: `LinkResolver`, `UrlProcessor`, `ImageSizeResolver`, `SyntaxHighlight`.

### 5. `configureVisitor(MarkwonVisitor.Builder builder)`

Registers a `NodeVisitor<T>` for each custom or overridden `Node` type.

```kotlin
override fun configureVisitor(builder: MarkwonVisitor.Builder) {
    builder.on(SpoilerNode::class.java) { visitor, node ->
        val length = visitor.length()
        visitor.visitChildren(node)
        visitor.setSpansForNodeOptional(node, length)
    }
}
```

To disable a node type: `builder.on(Heading::class.java, null)`

Use when: a custom AST node (from `configureParser`) needs to produce spans, or when overriding how a core node is visited.

### 6. `configureSpansFactory(MarkwonSpansFactory.Builder builder)`

Sets or replaces the `SpanFactory` that produces span object(s) for a `Node` type.

```kotlin
override fun configureSpansFactory(builder: MarkwonSpansFactory.Builder) {
    builder.setFactory(Emphasis::class.java) { config, props ->
        // return one span or an array of spans
        arrayOf(StyleSpan(Typeface.ITALIC), ForegroundColorSpan(Color.GRAY))
    }
}
```

Use when: the span type used for an existing node should change, or when a new node type needs a span factory without overriding its entire visitor.

### 7. `processMarkdown(markdown: String): String`

Called before parsing. Receives raw markdown, returns transformed markdown.

```kotlin
override fun processMarkdown(markdown: String): String {
    return regex.replace(markdown) { match ->
        """<a href="${asUrl(match.value)}">@${match.groupValues[1]}</a>"""
    }
}
```

Use when: the syntax can be normalized into valid HTML or standard markdown before parsing. This is the repo's primary approach for AniList-specific shorthand (mentions, spoilers, center, webm, youtube).

### 8. `beforeRender(node: Node)`

Called after parsing, before visiting. Rarely used. Acceptable for inspecting or patching the AST.

### 9. `afterRender(node: Node, visitor: MarkwonVisitor)`

Called after visiting. Use only for stateful plugin cleanup. Plugins should otherwise be stateless.

### 10. `beforeSetText(textView: TextView, markdown: Spanned)`

Called before `textView.setText(spanned)`. Use to unschedule old `AsyncDrawableSpan`s.

### 11. `afterSetText(textView: TextView)`

Called after `textView.setText(spanned)`. Use to schedule new `AsyncDrawableSpan`s. Note: `Spanned` is not passed here; query spans from the `TextView` if needed.

## Plugin Execution Order

Plugins execute in the order they are registered in `CorePlugin` (via `Markwon.builder`). The `configure(Registry)` step controls ordering. `CorePlugin` is always first (added automatically by `Markwon.builder`).

## Markwon Module Inventory

| Gradle alias | Artifact | Key contribution |
|---|---|---|
| `markwon-core` | `io.noties.markwon:core` | `Markwon`, `AbstractMarkwonPlugin`, `CorePlugin`, visitor/spans lifecycle |
| `markwon-html` | `io.noties.markwon:html` | `HtmlPlugin`, `TagHandler`, `HtmlTag` |
| `markwon-coil` | `io.noties.markwon:image-coil` | Coil-backed `AsyncDrawable` loading |
| `markwon-parser` | `io.noties.markwon:inline-parser` | Additional inline parsing hooks |
| `markwon-linkify` | `io.noties.markwon:linkify` | `LinkifyPlugin` auto-linkification |
| `markwon-simple-ext` | `io.noties.markwon:simple-ext` | Delimiter-based inline extensions |
| `markwon-ext-strikethrough` | `io.noties.markwon:ext-strikethrough` | GFM `~~strikethrough~~` |
| `markwon-ext-tables` | `io.noties.markwon:ext-tables` | GFM pipe tables |
| `markwon-ext-tasklist` | `io.noties.markwon:ext-tasklist` | `- [ ]` / `- [x]` task lists |
| `markwon-syntax-highlight` | `io.noties.markwon:syntax-highlight` | Code block syntax highlighting |
| `markwon-editor` | `io.noties.markwon:editor` | Editor-oriented helpers; not the parser path |
