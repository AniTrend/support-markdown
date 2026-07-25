# buildSrc/src/main/java/co/anitrend/support/markdown/buildSrc/

## Responsibility

Package namespace aggregator for the custom Gradle build logic used by this repository. It contains no direct implementation files at this level. See child maps for shared constants and Gradle plugin implementation details.

## Design

The namespace is split into `common` for shared module classification and SDK constants, and `plugin` for the project plugin, Gradle extension helpers, configuration components, and dependency strategy.

## Flow

Gradle enters through `plugin/CorePlugin.kt`, then delegates to child package functions that apply plugins, configure Android options, and add dependencies. Shared helpers from `common` identify the app sample module and markdown library module.

## Integration

Integrated by Gradle build scripts through the buildSrc classpath. Child maps: `common/codemap.md`, `plugin/codemap.md`.
