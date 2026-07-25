# buildSrc/src/main/

## Responsibility
Main source set for buildSrc Gradle plugin code.

## Design
Aggregates Java and Kotlin package maps for custom build configuration logic.

## Flow
Gradle compiles this source set before project configuration applies the convention plugin.

## Integration
Child map: `buildSrc/src/main/java/codemap.md`.
