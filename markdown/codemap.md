# markdown/

## Responsibility

Android library module that extends Markwon for AniList flavored markdown. It owns parser, visitor, and markdown preprocessing plugins for custom syntax, HTML alignment handling, inline media, mentions, spoilers, and text styling.

## Design

- Applies the local `co.anitrend.support.markdown` Gradle convention plugin and uses namespace `io.wax911.support.markdown`.
- Depends on Markwon core, editor, HTML, Coil image integration, inline parser, linkify, simple extensions, syntax highlight, task list, strikethrough, and table artifacts.
- Plugins are implemented as `AbstractMarkwonPlugin` factories with `create()` helpers.
- Syntax support is split by feature packages: core, center, spoiler, image, webm, YouTube, mention, link, strike, emphasis, italics, heading, horizontal line, and HTML handlers.
- Core markdown setup registers the shared tilde delimiter processor, HTML align and center handlers, soft line break rendering, and heading theme behavior.

## Flow

Consumers install Markwon plugins into a `Markwon.builder`. String level plugins can rewrite custom markdown during `processMarkdown`, for example center, image, webm, YouTube, and link handling. Parser plugins register delimiter processors for custom nodes such as center, spoiler, and strike. Visitor plugins convert nodes or text matches into spans, including spoiler reveal spans, center alignment spans, mention links, and image spans.

## Integration

- Exported plugin factories are consumed by the `app` sample Koin Markwon module.
- Requires Markwon `HtmlPlugin` for HTML tag handlers and media rewrites that produce HTML tags.
- Uses commonmark parser APIs for custom delimiter processing and Markwon visitor APIs for span creation.
- Relies on the shared Gradle catalog for Markwon dependencies and on the repository convention plugin for Android library configuration.
