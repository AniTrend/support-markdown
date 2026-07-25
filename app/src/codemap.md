# app/src/

## Responsibility
Android source set aggregator for the sample application.

## Design
Groups main application source maps. Test source sets are intentionally excluded from this codemap scope.

## Flow
Navigation enters through `app/src/main/`, then into Kotlin package maps and Android resources.

## Integration
Parent module map: `app/codemap.md`. Child map: `app/src/main/codemap.md`.
