# app/src/main/kotlin/co/anitrend/support/markdown/domain/

## Responsibility

Defines the sample app domain API for text feed loading without depending on Android UI or network implementation details.

## Design

This package is split into entities, query models, interactor contracts, and common state wrappers. It keeps feed data shape and use case boundaries explicit so the sample UI can depend on stable contracts.

## Flow

UI code builds a `TextFeedQuery` and sends it to `GetTextFeedPaged`. The implementation returns `PagingDataState`, whose flow emits pages of `TextFeed` entities for rendering.

## Integration

Implemented by the data layer and consumed by the sample view model and feed adapter. Generated Apollo models are mapped into these domain entities before they reach UI code.
