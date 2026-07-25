# markdown/src/main/kotlin/co/anitrend/support/markdown/core/

## Responsibility

Owns shared Markwon setup for the library. `CorePlugin` installs delimiter parsing, HTML tag support, soft line break handling, and a heading theme tweak used by feature plugins.

## Design

`CorePlugin` is an `AbstractMarkwonPlugin` created through `create(autoCloseTags)`. It keeps parser and visitor configuration centralized so feature plugins can rely on one shared base instead of registering duplicate infrastructure.

## Flow

During Markwon setup, `configureParser` registers `TildeDelimiterProcessor`, `configureVisitor` maps `SoftLineBreak` to a newline, `configureTheme` removes heading break height, and `configure` augments `HtmlPlugin` with `AlignTagHandler`, `CenterTagHandler`, and non-closed tag behavior.

## Integration

Depends on `common` for tilde syntax and `html` for custom tags. Spoiler, strike, and center syntax depend on the delimiter processor registered here. HTML alignment requires Markwon HtmlPlugin to be present in the registry.
