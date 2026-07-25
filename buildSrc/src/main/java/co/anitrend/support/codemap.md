# buildSrc/src/main/java/co/anitrend/support/

## Responsibility
Support package namespace aggregator for buildSrc.

## Design
Contains no independent implementation. It narrows the package path to repository build logic.

## Flow
Routes readers to markdown support build logic.

## Integration
Child map: `buildSrc/src/main/java/co/anitrend/support/markdown/codemap.md`.
