# markdown/src/main/kotlin/co/anitrend/support/markdown/heading/

## Responsibility

Provides a compatibility plugin entry point for markdown headings.

## Design

`HeadingPlugin` is intentionally a no-op `AbstractMarkwonPlugin`. Native commonmark-java parsing and Markwon core rendering already handle ATX headings and setext headings.

## Flow

When installed, the plugin does not modify parser, visitor, registry, or markdown text processing. Heading control flows through Markwon core unchanged.

## Integration

Retained for existing plugin chains that expect a heading feature plugin. Shared theme behavior that affects headings is configured by `CorePlugin`.
