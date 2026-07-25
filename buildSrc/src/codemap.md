# buildSrc/src/

## Responsibility
Source set aggregator for buildSrc.

## Design
Groups main build logic source maps. Test source sets are intentionally excluded from this codemap scope.

## Flow
Gradle discovers plugin implementation through the main Java source set.

## Integration
Child map: `buildSrc/src/main/codemap.md`.
