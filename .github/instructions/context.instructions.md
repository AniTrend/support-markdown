---
applyTo: "**"
description: Use when understanding support-markdown architecture, module boundaries, Markwon plugin structure, Dokka documentation, consumer-facing APIs, or shared Gradle/buildSrc behavior.
---

# Support Markdown Context

- `support-markdown` is a reusable Android library, not an app. It parses and renders markdown following the [AniList flavored markdown specification](https://files.kiniro.uk/anilist-flavored-markdown-v1.md) using [Markwon](https://noties.github.io/Markwon/).
- The library is distributed via JitPack. Favor stable, consumer-facing APIs and well-documented extension points over app-specific shortcuts.
- Treat the published Dokka site as part of the product surface: `https://anitrend.github.io/support-markdown/`.

## Module Groups

- Library module: `:markdown` — all markdown parsing, rendering, and plugin logic.
- Sample module: `:app` — demonstration app; excluded from CI builds (`settings.gradle` conditionally includes it when `CI` env var is absent).
- Build logic: `buildSrc` — shared Gradle conventions, Android defaults, publishing, and dependency strategy.

## Plugin Architecture (`:markdown`)

Each AniList markdown feature is implemented as a Markwon plugin under `co.anitrend.support.markdown`:

- `core/` — `CorePlugin`: the main entry point that wires all plugins into a `Markwon` instance.
- `common/` — `IMarkdownPlugin` interface and `MarkdownPluginController` base controller.
- `center/` — `CenterPlugin` and `CenterPluginController` for `~~~centered text~~~`.
- `emphasis/` — `EmphasisPlugin` for bold/italic emphasis rendering.
- `heading/` — `HeadingPlugin` for ATX headings.
- `horizontal/` — `HorizontalLinePlugin` for `---` rules.
- `html/` — `CenterTagHandler`, `AlignTagHandler` for HTML tag pass-through.
- `image/` — `ImagePlugin` and `ImagePluginController` using Coil for image loading.
- `italics/` — `ItalicsPlugin` for AniList-style italic handling.
- `link/` — `LinkifyPlugin` using BetterLinkMovementMethod.
- `mention/` — `MentionPlugin`, `MentionPluginController`, `OnMentionTextAddedListener`.
- `spoiler/` — `SpoilerPlugin` with `controller/`, `node/`, `render/`, `span/` subpackages.
- `strike/` — `StrikeThroughPlugin`.
- `webm/` — `WebMPlugin` and `WebMPluginController` for inline WebM video.
- `youtube/` — `YouTubePlugin` and `YoutubePluginController` for YouTube embeds.

## Dependency Direction

- `:markdown` has no project dependencies; it depends only on external libraries (Markwon, Coil, BetterLinkMovementMethod).
- `:app` depends on `:markdown` for demonstration purposes only.
- `buildSrc` depends on external build tools only.

## Build And Tooling Facts

- All modules apply the shared `co.anitrend.support.markdown` Gradle plugin from `buildSrc`.
- Shared Android defaults live in `buildSrc/…/components/AndroidConfiguration.kt`: `compileSdk = 34`, `minSdk = 21`, `targetSdk = 34`, Java 17 source/target compatibility.
- The repo Java toolchain pin is `.java-version = 21.0.8`. Systems are expected to have `jenv` installed; `jenv` reads `.java-version` automatically to activate the correct JDK.
- Dependency versions belong in `gradle/libs.versions.toml` before they are referenced from module build files.
- Spotless and ktlint are enforced centrally via `buildSrc`.
- Publishing to JitPack is configured in `buildSrc/…/components/AndroidOptions.kt`.

## Documentation Contract

- Dokka is configured centrally; CI publishes API docs to the `docs` branch and GitHub Pages.
- When changing public behavior, update KDoc in the same change.
- Document what the API does, when to use it, and what a consumer must provide or expect.

## Commit Conventions

The project uses conventional commits with generic scopes:
- `feat:` — New features
- `fix:` — Bug fixes
- `docs:` — Documentation changes
- `style:` — Code style changes
- `refactor:` — Code refactoring
- `test:` — Test additions/changes
- `chore:` — Build/tooling changes

## Working Heuristics

- New markdown element: create a new top-level package in `:markdown` matching the existing plugin pattern (plugin class + optional `controller/` subpackage + supporting types).
- Shared plugin contracts or base behavior: `common/`.
- HTML tag handling: `html/`.
- Prefer shared build logic changes in `buildSrc` over duplicating Gradle configuration in individual module build files.
- When running Gradle locally, use the `jenv-gradle-low-ram` skill to align Java versions and avoid memory pressure.
- For questions about Markwon plugin hooks, the commonmark-java AST, Android span types, or the `processMarkdown` vs `configureParser` vs `configureVisitor` decision, use the `support-markdown-markwon-internals` skill.
