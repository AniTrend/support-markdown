# Repository Atlas: support-markdown

## Project Responsibility

`support-markdown` is an Android Kotlin multi-module project that provides AniList flavored markdown support on top of Markwon. The publishable `:markdown` library contains custom parser, visitor, span, and plugin extensions for markdown features such as spoilers, mentions, centered text, inline images, WebM links, YouTube previews, and auto-linked URLs. The local `:app` module is a sample Android application that exercises the library against a paged AniList text feed. `buildSrc` contains the repository Gradle convention plugin used to configure Android modules, dependencies, publication settings, lint, Dokka, and version catalog access.

## System Entry Points

- `settings.gradle`: Includes `:markdown` always and includes `:app` only outside CI.
- `build.gradle.kts`: Defines top-level buildscript repositories, Android and Kotlin Gradle classpaths, shared repositories, and the root `clean` task.
- `gradle/libs.versions.toml`: Central version catalog for AndroidX, Kotlin, Dokka, Coil, Koin, Markwon, Apollo, Retrofit, test libraries, and build plugins.
- `buildSrc/src/main/java/co/anitrend/support/markdown/buildSrc/plugin/CorePlugin.kt`: Custom Gradle convention plugin entry point.
- `markdown/build.gradle.kts`: Library module build configuration for the Markwon extension artifact.
- `markdown/src/main/kotlin/co/anitrend/support/markdown/core/CorePlugin.kt`: Base Markwon plugin configuration shared by feature plugins.
- `app/build.gradle.kts`: Sample app module configuration for local demonstration and integration testing outside CI.
- `app/src/main/kotlin/co/anitrend/support/markdown/sample/App.kt`: Sample app application class and dependency graph bootstrap.

## Architecture Overview

The project is organized around three cooperating layers:

1. Build configuration is centralized in `buildSrc`. The custom convention plugin applies Android and Dokka plugins, configures Android options, resolves release properties, and injects dependency sets through strategy classes and Gradle extension helpers.
2. The `:markdown` module exposes feature-oriented Markwon plugins. Each feature package owns one markdown concern and composes parser preprocessing, commonmark delimiter processing, span creation, node rendering, or visitor behavior.
3. The `:app` module demonstrates library usage through a clean architecture split. Domain contracts describe feed requests and results, data repositories fetch AniList feed pages, Koin modules assemble dependencies, and sample UI classes render paged markdown feed items.

## Directory Map

| Directory | Responsibility Summary | Detailed Map |
|-----------|------------------------|--------------|
| `gradle/` | Shared Gradle metadata, version catalog, wrapper configuration, and published artifact version metadata. | [View Map](gradle/codemap.md) |
| `buildSrc/` | Gradle build logic module for repository convention plugins. | [View Map](buildSrc/codemap.md) |
| `buildSrc/src/` | Source set aggregator for buildSrc. | [View Map](buildSrc/src/codemap.md) |
| `buildSrc/src/main/` | Main source set for buildSrc Gradle plugin code. | [View Map](buildSrc/src/main/codemap.md) |
| `buildSrc/src/main/java/` | Java package root for buildSrc convention plugin code. | [View Map](buildSrc/src/main/java/codemap.md) |
| `buildSrc/src/main/java/co/` | Top-level Java namespace aggregator for buildSrc. | [View Map](buildSrc/src/main/java/co/codemap.md) |
| `buildSrc/src/main/java/co/anitrend/` | Organization namespace aggregator for buildSrc. | [View Map](buildSrc/src/main/java/co/anitrend/codemap.md) |
| `buildSrc/src/main/java/co/anitrend/support/` | Support package namespace aggregator for buildSrc. | [View Map](buildSrc/src/main/java/co/anitrend/support/codemap.md) |
| `buildSrc/src/main/java/co/anitrend/support/markdown/` | Markdown support namespace aggregator for buildSrc. | [View Map](buildSrc/src/main/java/co/anitrend/support/markdown/codemap.md) |
| `buildSrc/src/main/java/co/anitrend/support/markdown/buildSrc/` | Package namespace aggregator for repository custom Gradle build logic. | [View Map](buildSrc/src/main/java/co/anitrend/support/markdown/buildSrc/codemap.md) |
| `buildSrc/src/main/java/co/anitrend/support/markdown/buildSrc/common/` | Shared build constants and module identity helpers for Gradle plugin code. | [View Map](buildSrc/src/main/java/co/anitrend/support/markdown/buildSrc/common/codemap.md) |
| `buildSrc/src/main/java/co/anitrend/support/markdown/buildSrc/plugin/` | Custom Gradle `CorePlugin` that coordinates repository build configuration for Android modules. | [View Map](buildSrc/src/main/java/co/anitrend/support/markdown/buildSrc/plugin/codemap.md) |
| `buildSrc/src/main/java/co/anitrend/support/markdown/buildSrc/plugin/components/` | Concrete Gradle configuration steps for plugins, Android defaults, dependencies, publishing options, and release properties. | [View Map](buildSrc/src/main/java/co/anitrend/support/markdown/buildSrc/plugin/components/codemap.md) |
| `buildSrc/src/main/java/co/anitrend/support/markdown/buildSrc/plugin/extensions/` | Gradle convenience extensions for typed lookups, version catalogs, plugin checks, and dependency shortcuts. | [View Map](buildSrc/src/main/java/co/anitrend/support/markdown/buildSrc/plugin/extensions/codemap.md) |
| `buildSrc/src/main/java/co/anitrend/support/markdown/buildSrc/plugin/strategy/` | Dependency selection rules for modules configured by the convention plugin. | [View Map](buildSrc/src/main/java/co/anitrend/support/markdown/buildSrc/plugin/strategy/codemap.md) |
| `markdown/` | Android library module that extends Markwon for AniList flavored markdown. | [View Map](markdown/codemap.md) |
| `markdown/src/` | Android library source set aggregator. | [View Map](markdown/src/codemap.md) |
| `markdown/src/main/` | Main source set for the markdown library module. | [View Map](markdown/src/main/codemap.md) |
| `markdown/src/main/kotlin/` | Kotlin package root for the markdown library. | [View Map](markdown/src/main/kotlin/codemap.md) |
| `markdown/src/main/kotlin/co/` | Top-level Kotlin namespace aggregator for markdown library code. | [View Map](markdown/src/main/kotlin/co/codemap.md) |
| `markdown/src/main/kotlin/co/anitrend/` | Organization namespace aggregator for markdown library code. | [View Map](markdown/src/main/kotlin/co/anitrend/codemap.md) |
| `markdown/src/main/kotlin/co/anitrend/support/` | Support package namespace aggregator for markdown library code. | [View Map](markdown/src/main/kotlin/co/anitrend/support/codemap.md) |
| `markdown/src/main/kotlin/co/anitrend/support/markdown/` | Package namespace aggregator for markdown library feature plugins. | [View Map](markdown/src/main/kotlin/co/anitrend/support/markdown/codemap.md) |
| `markdown/src/main/kotlin/co/anitrend/support/markdown/core/` | Shared Markwon setup for delimiter parsing, HTML tags, line breaks, and heading theming. | [View Map](markdown/src/main/kotlin/co/anitrend/support/markdown/core/codemap.md) |
| `markdown/src/main/kotlin/co/anitrend/support/markdown/common/` | Commonmark parsing helpers for tilde delimiter syntax. | [View Map](markdown/src/main/kotlin/co/anitrend/support/markdown/common/codemap.md) |
| `markdown/src/main/kotlin/co/anitrend/support/markdown/center/` | Centered text support for AniList `~~~text~~~` markdown syntax. | [View Map](markdown/src/main/kotlin/co/anitrend/support/markdown/center/codemap.md) |
| `markdown/src/main/kotlin/co/anitrend/support/markdown/ephasis/` | Strong emphasis rendering package, with the package name preserved as `ephasis`. | [View Map](markdown/src/main/kotlin/co/anitrend/support/markdown/ephasis/codemap.md) |
| `markdown/src/main/kotlin/co/anitrend/support/markdown/heading/` | Compatibility plugin entry point for markdown headings. | [View Map](markdown/src/main/kotlin/co/anitrend/support/markdown/heading/codemap.md) |
| `markdown/src/main/kotlin/co/anitrend/support/markdown/horizontal/` | Compatibility plugin entry point for horizontal rules and thematic breaks. | [View Map](markdown/src/main/kotlin/co/anitrend/support/markdown/horizontal/codemap.md) |
| `markdown/src/main/kotlin/co/anitrend/support/markdown/html/` | Custom Markwon HTML tag handlers for alignment syntax. | [View Map](markdown/src/main/kotlin/co/anitrend/support/markdown/html/codemap.md) |
| `markdown/src/main/kotlin/co/anitrend/support/markdown/image/` | AniList custom image syntax parsing and constrained Markwon image rendering. | [View Map](markdown/src/main/kotlin/co/anitrend/support/markdown/image/codemap.md) |
| `markdown/src/main/kotlin/co/anitrend/support/markdown/italics/` | Italic text rendering for commonmark emphasis nodes. | [View Map](markdown/src/main/kotlin/co/anitrend/support/markdown/italics/codemap.md) |
| `markdown/src/main/kotlin/co/anitrend/support/markdown/link/` | Automatic linking for bare `http`, `https`, and `ftp` URLs. | [View Map](markdown/src/main/kotlin/co/anitrend/support/markdown/link/codemap.md) |
| `markdown/src/main/kotlin/co/anitrend/support/markdown/mention/` | AniList user mention support for `@username` text patterns. | [View Map](markdown/src/main/kotlin/co/anitrend/support/markdown/mention/codemap.md) |
| `markdown/src/main/kotlin/co/anitrend/support/markdown/mention/controller/` | Mention matching and profile URL construction utilities. | [View Map](markdown/src/main/kotlin/co/anitrend/support/markdown/mention/controller/codemap.md) |
| `markdown/src/main/kotlin/co/anitrend/support/markdown/spoiler/` | Click-to-reveal spoiler rendering for AniList `~!spoiler!~` syntax. | [View Map](markdown/src/main/kotlin/co/anitrend/support/markdown/spoiler/codemap.md) |
| `markdown/src/main/kotlin/co/anitrend/support/markdown/spoiler/node/` | Custom commonmark AST node definitions for spoilers. | [View Map](markdown/src/main/kotlin/co/anitrend/support/markdown/spoiler/node/codemap.md) |
| `markdown/src/main/kotlin/co/anitrend/support/markdown/spoiler/render/` | Legacy span factory bridge for spoiler span creation. | [View Map](markdown/src/main/kotlin/co/anitrend/support/markdown/spoiler/render/codemap.md) |
| `markdown/src/main/kotlin/co/anitrend/support/markdown/spoiler/span/` | Android spans that control spoiler appearance and reveal behavior. | [View Map](markdown/src/main/kotlin/co/anitrend/support/markdown/spoiler/span/codemap.md) |
| `markdown/src/main/kotlin/co/anitrend/support/markdown/strike/` | Strikethrough rendering for `~~text~~` markdown syntax. | [View Map](markdown/src/main/kotlin/co/anitrend/support/markdown/strike/codemap.md) |
| `markdown/src/main/kotlin/co/anitrend/support/markdown/webm/` | Conversion of AniList `webm(URL)` syntax into clickable media previews. | [View Map](markdown/src/main/kotlin/co/anitrend/support/markdown/webm/codemap.md) |
| `markdown/src/main/kotlin/co/anitrend/support/markdown/youtube/` | Conversion of AniList `youtube(ID-or-URL)` syntax into clickable video thumbnails. | [View Map](markdown/src/main/kotlin/co/anitrend/support/markdown/youtube/codemap.md) |
| `app/` | Android sample application for exercising the markdown library in a real feed screen. | [View Map](app/codemap.md) |
| `app/src/` | Android source set aggregator for the sample application. | [View Map](app/src/codemap.md) |
| `app/src/main/` | Main Android source set for the sample app. | [View Map](app/src/main/codemap.md) |
| `app/src/main/kotlin/` | Kotlin package root for the sample app. | [View Map](app/src/main/kotlin/codemap.md) |
| `app/src/main/kotlin/co/` | Top-level Kotlin namespace aggregator for sample app code. | [View Map](app/src/main/kotlin/co/codemap.md) |
| `app/src/main/kotlin/co/anitrend/` | Organization namespace aggregator for sample app code. | [View Map](app/src/main/kotlin/co/anitrend/codemap.md) |
| `app/src/main/kotlin/co/anitrend/support/` | Support package namespace aggregator for sample app code. | [View Map](app/src/main/kotlin/co/anitrend/support/codemap.md) |
| `app/src/main/kotlin/co/anitrend/support/markdown/` | Sample app namespace aggregator for core support, domain contracts, data access, and UI sample layers. | [View Map](app/src/main/kotlin/co/anitrend/support/markdown/codemap.md) |
| `app/src/main/kotlin/co/anitrend/support/markdown/core/` | Shared Android base classes and markdown rendering helpers for the sample app. | [View Map](app/src/main/kotlin/co/anitrend/support/markdown/core/codemap.md) |
| `app/src/main/kotlin/co/anitrend/support/markdown/core/extensions/` | Markdown rendering and cleanup helpers for `AppCompatTextView`. | [View Map](app/src/main/kotlin/co/anitrend/support/markdown/core/extensions/codemap.md) |
| `app/src/main/kotlin/co/anitrend/support/markdown/core/plugin/` | App-side Markwon image plugin that connects image nodes to Coil loading. | [View Map](app/src/main/kotlin/co/anitrend/support/markdown/core/plugin/codemap.md) |
| `app/src/main/kotlin/co/anitrend/support/markdown/core/plugin/loader/` | Markwon `AsyncDrawableLoader` bridge that queues Coil requests. | [View Map](app/src/main/kotlin/co/anitrend/support/markdown/core/plugin/loader/codemap.md) |
| `app/src/main/kotlin/co/anitrend/support/markdown/core/plugin/store/` | Coil image request factory and cancellation owner for async drawables. | [View Map](app/src/main/kotlin/co/anitrend/support/markdown/core/plugin/store/codemap.md) |
| `app/src/main/kotlin/co/anitrend/support/markdown/core/plugin/target/` | Coil callback target that applies loaded drawables to Markwon async drawables. | [View Map](app/src/main/kotlin/co/anitrend/support/markdown/core/plugin/target/codemap.md) |
| `app/src/main/kotlin/co/anitrend/support/markdown/domain/` | Sample app domain API for text feed loading. | [View Map](app/src/main/kotlin/co/anitrend/support/markdown/domain/codemap.md) |
| `app/src/main/kotlin/co/anitrend/support/markdown/domain/common/` | Shared domain state wrappers for paged feed results. | [View Map](app/src/main/kotlin/co/anitrend/support/markdown/domain/common/codemap.md) |
| `app/src/main/kotlin/co/anitrend/support/markdown/domain/entities/` | Domain model rendered by the feed sample. | [View Map](app/src/main/kotlin/co/anitrend/support/markdown/domain/entities/codemap.md) |
| `app/src/main/kotlin/co/anitrend/support/markdown/domain/interactor/` | Use case boundary for retrieving paginated text feed data. | [View Map](app/src/main/kotlin/co/anitrend/support/markdown/domain/interactor/codemap.md) |
| `app/src/main/kotlin/co/anitrend/support/markdown/domain/model/` | Request parameters for loading AniList text feed pages. | [View Map](app/src/main/kotlin/co/anitrend/support/markdown/domain/model/codemap.md) |
| `app/src/main/kotlin/co/anitrend/support/markdown/data/` | Data layer for loading AniList text activity feed pages. | [View Map](app/src/main/kotlin/co/anitrend/support/markdown/data/codemap.md) |
| `app/src/main/kotlin/co/anitrend/support/markdown/data/koin/` | Koin modules for networking, repository, and domain interactor bindings. | [View Map](app/src/main/kotlin/co/anitrend/support/markdown/data/koin/codemap.md) |
| `app/src/main/kotlin/co/anitrend/support/markdown/data/repository/` | Paged feed stream repository used by the domain use case. | [View Map](app/src/main/kotlin/co/anitrend/support/markdown/data/repository/codemap.md) |
| `app/src/main/kotlin/co/anitrend/support/markdown/data/usecase/` | Concrete domain interactor implementation for fetching paged text feeds. | [View Map](app/src/main/kotlin/co/anitrend/support/markdown/data/usecase/codemap.md) |
| `app/src/main/kotlin/co/anitrend/support/markdown/sample/` | Runnable Android sample app demonstrating AniList markdown feed rendering. | [View Map](app/src/main/kotlin/co/anitrend/support/markdown/sample/codemap.md) |
| `app/src/main/kotlin/co/anitrend/support/markdown/sample/component/` | Sample app activity shell. | [View Map](app/src/main/kotlin/co/anitrend/support/markdown/sample/component/codemap.md) |
| `app/src/main/kotlin/co/anitrend/support/markdown/sample/feed/` | Feed screen that loads, filters, refreshes, and displays paged markdown text activities. | [View Map](app/src/main/kotlin/co/anitrend/support/markdown/sample/feed/codemap.md) |
| `app/src/main/kotlin/co/anitrend/support/markdown/sample/feed/adapter/` | RecyclerView feed row rendering and author click events for feed filtering. | [View Map](app/src/main/kotlin/co/anitrend/support/markdown/sample/feed/adapter/codemap.md) |
| `app/src/main/kotlin/co/anitrend/support/markdown/sample/feed/viewmodel/` | Feed screen state management between UI queries and domain paging results. | [View Map](app/src/main/kotlin/co/anitrend/support/markdown/sample/feed/viewmodel/codemap.md) |
| `app/src/main/kotlin/co/anitrend/support/markdown/sample/feed/viewmodel/contract/` | Feed view model contract consumed by the feed fragment. | [View Map](app/src/main/kotlin/co/anitrend/support/markdown/sample/feed/viewmodel/contract/codemap.md) |
| `app/src/main/kotlin/co/anitrend/support/markdown/sample/koin/` | Sample app dependency graph for markdown rendering, image loading, UI, view models, and data modules. | [View Map](app/src/main/kotlin/co/anitrend/support/markdown/sample/koin/codemap.md) |

## Reading Guide

- Start with `markdown/codemap.md` when changing published markdown behavior.
- Start with `app/codemap.md` when changing sample app rendering, paging, or dependency injection.
- Start with `buildSrc/codemap.md` when changing Gradle convention behavior.
- For any deep folder change, read the nearest folder `codemap.md`, then follow its Integration section to caller or dependency maps.
