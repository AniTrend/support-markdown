# Build Map

Use this map to choose the right build file before editing.

| Concern | Primary files | Notes |
| --- | --- | --- |
| Module includes | `settings.gradle` | Conditionally includes `:app` only when the `CI` env var is absent |
| Root build config | `build.gradle.kts` | Repository-level plugin and repository declarations |
| Shared plugin entry point | `buildSrc/.../plugin/CorePlugin.kt` | Applies plugins, Android config, options, and dependencies to each module |
| Shared plugin application | `buildSrc/.../components/AndroidPlugins.kt` | Selects `com.android.library` or `com.android.application`, applies Spotless, Dokka, and maven-publish |
| Shared Android defaults | `buildSrc/.../components/AndroidConfiguration.kt` | `compileSdk = 34`, `minSdk = 21`, `targetSdk = 34`, Java 17 source/target compat, lint, test options, Spotless |
| Publishing options | `buildSrc/.../components/AndroidOptions.kt` | Maven publication (JitPack), sources JAR, classes JAR, POM metadata |
| Shared dependency strategy | `buildSrc/.../strategy/DependencyStrategy.kt` | Kotlin stdlib, lifecycle, test libraries applied by default |
| Dependency versions and aliases | `gradle/libs.versions.toml` | Add or update versions and aliases here first |
| Version code and name | `gradle/version.properties` | `version`, `code`, and `name` properties consumed by build scripts |
| Library module | `markdown/build.gradle.kts` | Applies `co.anitrend.support.markdown`, declares the Markwon runtime surface used by the library |
| Sample app module | `app/build.gradle.kts` | Applies `co.anitrend.support.markdown`, demo only, not published |
| Dokka publication | `.github/workflows/gradle-dokka.yml` | Generates and publishes API docs to GitHub Pages |

## Module Dependency Snapshot

- `:markdown`: no project dependencies; depends only on external libraries (Markwon, Coil, BetterLinkMovementMethod).
- `:app`: depends on `:markdown` for demonstration. Not published.

## Current `:markdown` Dependency Surface

| Alias | Artifact | Role |
| --- | --- | --- |
| `markwon-core` | `io.noties.markwon:core` | Core builder, plugin lifecycle, visitor, and spans APIs |
| `markwon-editor` | `io.noties.markwon:editor` | Editor integration helpers |
| `markwon-html` | `io.noties.markwon:html` | HTML parsing and custom tag handlers |
| `markwon-coil` | `io.noties.markwon:image-coil` | Image loading via Coil |
| `markwon-parser` | `io.noties.markwon:inline-parser` | Inline parser support on top of commonmark-java |
| `markwon-linkify` | `io.noties.markwon:linkify` | Linkification support |
| `markwon-simple-ext` | `io.noties.markwon:simple-ext` | Simple delimiter extensions |
| `markwon-syntax-highlight` | `io.noties.markwon:syntax-highlight` | Syntax highlighting |
| `markwon-ext-tasklist` | `io.noties.markwon:ext-tasklist` | Task-list support |
| `markwon-ext-strikethrough` | `io.noties.markwon:ext-strikethrough` | Strikethrough support |
| `markwon-ext-tables` | `io.noties.markwon:ext-tables` | Table support |

Parser behavior ultimately flows through commonmark-java underneath Markwon. If you need custom delimiters, block parsers, or a parser extension Markwon does not already expose, evaluate a direct commonmark-java dependency instead of forcing the behavior into unrelated layers.

## Edit Strategy

- New library version or alias: `gradle/libs.versions.toml`.
- Cross-module convention (lint, test options, SDK levels): `buildSrc`.
- One module only: that module's `build.gradle.kts`.
- Documentation generation or publish behavior: Dokka config plus workflow file.
- Version bump: `gradle/version.properties`.
