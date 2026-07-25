# buildSrc/src/main/java/co/anitrend/support/markdown/buildSrc/plugin/strategy/

## Responsibility

Owns dependency selection rules for modules configured by the custom Gradle plugin.

## Design

`DependencyStrategy` is constructed with a `Project` so it can read the version catalog and determine whether the current module is the sample app. Dependency groups are split into default, test, and lifecycle sets through private `DependencyHandler` extension functions.

## Flow

`applyDependenciesOn(handler)` always adds Kotlin standard library, Kotlin reflection, JUnit, MockK, and Android instrumentation dependencies. For the sample app, it also adds Koin runtime, Koin test dependencies, and AndroidX lifecycle dependencies.

## Integration

Called from `components/configureDependencies()`. Uses `common.isSampleModule()` for module branching, generated `libs` catalog access from `plugin/extensions`, and dependency helper functions from `plugin/extensions/DependencyHandlerExtensions.kt`.
