# gradle/

## Responsibility

Holds shared Gradle metadata for the repository. The version catalog defines plugin and library coordinates consumed by the root build, buildSrc, and Android modules. Version metadata records the published artifact version, and the wrapper properties pin the Gradle runtime.

## Design

- `libs.versions.toml` centralizes versions for Android Gradle Plugin, Kotlin, Dokka, Spotless, AndroidX, Koin, Coil, Markwon, Apollo adjacent runtime use, and test libraries.
- `version.properties` stores the release triplet as `version`, numeric `code`, and display `name`.
- `wrapper/gradle-wrapper.properties` pins Gradle 9.6.1 and validates the distribution URL.
- The version catalog is imported by `buildSrc/settings.gradle.kts`, which lets the custom Gradle plugin use the same dependency coordinates as the application build.

## Flow

Gradle starts from `settings.gradle`, includes `:markdown`, and includes `:app` only outside CI. The root build and buildSrc resolve shared aliases from `libs.versions.toml`. Module build scripts apply the local `co.anitrend.support.markdown` plugin, then add module specific dependencies through catalog aliases or direct coordinates.

## Integration

- Root buildscript classpaths use `libs.android.gradle.plugin` and `libs.jetbrains.kotlin.gradle`.
- `buildSrc` imports this catalog and builds the `co.anitrend.support.markdown` convention plugin.
- `markdown` consumes Markwon aliases from the catalog.
- `app` consumes AndroidX, Material, Paging, Markwon, Coil, and BetterLinkMovementMethod aliases, plus `project(":markdown")`.
