# buildSrc/src/main/java/co/anitrend/support/markdown/buildSrc/plugin/

## Responsibility

Defines the custom Gradle `CorePlugin` that applies and coordinates all repository build configuration for Android modules.

## Design

`CorePlugin` is a thin orchestrator. It delegates concrete work to extension functions in `components`, while `extensions` hides Gradle extension lookup and dependency configuration names. `strategy` owns dependency selection rules.

## Flow

`apply(project)` calls plugin configuration first, logs available Gradle extensions and components, then configures Android defaults, build options, and dependencies. Child packages perform module-specific branching for sample and library modules.

## Integration

Gradle applies this plugin from build scripts through buildSrc. It integrates Android Gradle Plugin, Kotlin Android, Spotless, Dokka, Maven Publish, version catalog access, and `gradle/version.properties` through child helpers. Child maps: `components/codemap.md`, `extensions/codemap.md`, `strategy/codemap.md`.
