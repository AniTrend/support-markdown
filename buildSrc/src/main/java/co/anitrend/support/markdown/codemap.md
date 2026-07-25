# buildSrc/src/main/java/co/anitrend/support/markdown/

## Responsibility
Markdown support namespace aggregator for buildSrc.

## Design
Contains no direct plugin implementation. It points to the buildSrc package where convention logic lives.

## Flow
Routes readers to `buildSrc/` plugin implementation maps.

## Integration
Child map: `buildSrc/src/main/java/co/anitrend/support/markdown/buildSrc/codemap.md`.
