# commonmark-java AST and Extension Points

Source: https://github.com/commonmark/commonmark-java

## Core Concepts

commonmark-java parses markdown into a tree of `Node` objects (the AST). Markwon's `configureParser` hook receives a `Parser.Builder` that wraps this library's configuration surface.

## Node Hierarchy (Key Types)

```
Node
├── Document                  ← root
├── Block (abstract)
│   ├── Paragraph
│   ├── Heading               ← level 1-6
│   ├── FencedCodeBlock
│   ├── IndentedCodeBlock
│   ├── BlockQuote
│   ├── BulletList
│   ├── OrderedList
│   ├── ListItem
│   ├── HtmlBlock
│   ├── ThematicBreak         ← horizontal rule
│   └── CustomBlock           ← extend for new block types
└── Inline (abstract)
    ├── Text                  ← raw text literal
    ├── Emphasis              ← *italic*
    ├── StrongEmphasis        ← **bold**
    ├── Code                  ← `inline code`
    ├── HardLineBreak
    ├── SoftLineBreak
    ├── Image
    ├── Link
    ├── HtmlInline
    └── CustomNode            ← extend for new inline types (e.g. SpoilerNode)
```

## Visiting the AST

Implement `AbstractVisitor` to walk the tree:

```kotlin
class MyVisitor : AbstractVisitor() {
    override fun visit(text: Text) {
        // process text node
        visitChildren(text) // descend
    }
    override fun visit(heading: Heading) {
        val level = heading.level // 1..6
        visitChildren(heading)
    }
}

val doc: Node = parser.parse(markdown)
doc.accept(MyVisitor())
```

In Markwon, this visitor pattern is encapsulated in `MarkwonVisitor`. You register per-type handlers via `configureVisitor`.

## Custom Node Types

For AniList-specific syntax, create a `CustomNode` subclass:

```kotlin
// In node/ subpackage
class SpoilerNode : CustomNode() {
    var content: String = ""
}
```

Then in `configureParser`, produce this node from parsed syntax. In `configureVisitor`, register a handler that calls `visitor.setSpansForNodeOptional(node, length)` to apply the spans defined in `configureSpansFactory`.

## Parser Extension Points (via `Parser.Builder`)

All accessed through `AbstractMarkwonPlugin.configureParser(builder)`:

| Method | Use case |
|---|---|
| `builder.extensions(list)` | Add a standard extension (e.g. `StrikethroughExtension`) |
| `builder.customBlockParserFactory(factory)` | Parse new block-level syntax into `CustomBlock` nodes |
| `builder.customInlineContentParserFactory(factory)` | Parse new inline syntax into `CustomNode` nodes |
| `builder.customDelimiterProcessor(processor)` | Handle new delimiter-based inline syntax (e.g. `~~foo~~`) |
| `builder.linkProcessor(processor)` | Intercept and customize link resolution |
| `builder.enabledBlockTypes(set)` | Restrict which block types are parsed |
| `builder.includeSourceSpans(mode)` | Attach source position info to nodes |

## Custom Block Parser Pattern

```kotlin
class MyBlockParserFactory : BlockParserFactory {
    override fun tryStart(state: ParserState, matchedBlockParser: MatchedBlockParser): BlockStart? {
        // inspect state.line to determine if this block starts here
        return BlockStart.of(MyBlockParser()).atIndex(state.nextNonSpaceIndex)
    }
}

class MyBlockParser : AbstractBlockParser() {
    private val block = MyCustomBlock()
    override fun getBlock(): Block = block
    override fun tryContinue(state: ParserState): BlockContinue = BlockContinue.atIndex(state.index)
    override fun parseInlines(inlineParser: InlineParser) { /* optional */ }
}
```

## Custom Inline Parser Pattern

```kotlin
class MyInlineParserFactory : InlineContentParserFactory {
    override fun getTriggerCharacters(): Set<Char> = setOf('~')
    override fun create(): InlineContentParser = MyInlineParser()
}

class MyInlineParser : InlineContentParser {
    override fun tryParse(inlineParserState: InlineParserState): ParsedInline? {
        val scanner = inlineParserState.scanner
        // consume characters and return a node
        return ParsedInline.of(MyCustomNode(), scanner.position())
    }
}
```

## Delimiter Processor Pattern

Simpler than a full inline parser; handles symmetric delimiters like `~~text~~`:

```kotlin
class MyDelimiterProcessor : DelimiterProcessor {
    override fun getOpeningCharacter(): Char = '~'
    override fun getClosingCharacter(): Char = '~'
    override fun getMinLength(): Int = 2
    override fun process(openingRun: DelimiterRun, closingRun: DelimiterRun): Int {
        // wrap content in a custom node
        val node = MyCustomNode()
        openingRun.opener.insertAfter(node)
        // move children into node...
        return 2 // number of delimiters consumed
    }
}
```

## AniList Markdown vs Standard CommonMark

The AniList spec overlaps with CommonMark but adds custom constructs (`~!spoiler!~`, `youtube(...)`, `webm(...)`, `img###(...)`, `@mention`, `~~~center~~~`, `<p align>`).

For the full authoritative feature list, syntax rules, known bugs, and plugin-to-feature mapping, see the [AniList-Flavored Markdown specification](./anilist-markdown-spec.md).

## Source Position Access

Useful for debugging: attach source spans to nodes so you can trace rendered spans back to input offsets.

```kotlin
override fun configureParser(builder: Parser.Builder) {
    builder.includeSourceSpans(IncludeSourceSpans.BLOCKS_AND_INLINES)
}
// then in a visitor: node.sourceSpans[0].inputIndex
```

## Key API Links

- Javadoc: https://www.javadoc.io/doc/org.commonmark/commonmark
- CommonMark spec: https://spec.commonmark.org/
- AniList flavored markdown spec: https://files.kiniro.uk/anilist-flavored-markdown-v1.md
