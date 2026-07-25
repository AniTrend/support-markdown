# buildSrc/

## Responsibility
Gradle build logic module for repository convention plugins.

## Design
Aggregates Kotlin and Java build logic source maps used by Gradle during configuration.

## Flow
Gradle compiles buildSrc first, then exposes its plugin and helpers to root and module build scripts.

## Integration
Child map: `buildSrc/src/codemap.md`.
