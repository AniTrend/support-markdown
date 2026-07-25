# buildSrc/src/main/java/co/anitrend/support/markdown/buildSrc/common/

## Responsibility

Provides shared build constants and module identity helpers for the Gradle plugin code.

## Design

`Configuration` centralizes SDK version values. `Constants.kt` defines canonical project names and extension functions on `Project` so plugin components can branch on module role without duplicating string comparisons.

## Flow

Plugin components call `isSampleModule()` and `isLibraryModule()` during configuration. Those helpers compare `Project.name` with the known `app` and `markdown` module names.

## Integration

Used by plugin components and dependency strategy to select Android application versus library behavior, sample-only dependencies, lifecycle dependencies, Dokka publishing behavior, and lint configuration.
