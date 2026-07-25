# buildSrc/src/main/java/co/anitrend/support/markdown/buildSrc/plugin/extensions/

## Responsibility

Provides Gradle convenience extensions for typed extension lookup, version catalog access, plugin presence checks, and dependency handler shortcuts.

## Design

`ProjectExtensions.kt` wraps `extensions.getByType` for Android, Kotlin, Publishing, Spotless, Java, reporting, source sets, generated version catalog access, and build properties. `DependencyHandlerExtensions.kt` maps typed helper functions to Gradle configuration names through a private `DependencyType` enum and a shared `addDependency` dispatcher.

## Flow

Component and strategy code request typed Gradle extensions through these helpers, then apply configuration to the returned objects. Dependency helpers receive dependency notation and optionally an `ExternalModuleDependency` configuration block, then add the dependency to the target Gradle configuration.

## Integration

Used by plugin components for Android, Kotlin, Spotless, Publishing, and version property access. Used by `DependencyStrategy` and `configureDependencies()` to add implementation, test, androidTest, kapt, api, compileOnly, runtimeOnly, and variant-specific dependencies without repeating configuration strings.
