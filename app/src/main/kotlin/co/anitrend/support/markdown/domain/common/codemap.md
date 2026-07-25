# app/src/main/kotlin/co/anitrend/support/markdown/domain/common/

## Responsibility

Contains shared domain state wrappers for paged feed results.

## Design

`PagingDataState` is a minimal data holder around `Flow<PagingData<TextFeed>>` with an empty flow default. It gives the interactor a stable return type without exposing repository classes.

## Flow

Use cases create `PagingDataState` after obtaining a paging flow. View models read `state.data`, cache it in their scope, and emit the paging data to UI collectors.

## Integration

Used by `GetTextFeedPaged`, `TextFeedUseCase`, and `FeedViewModel`. Depends on AndroidX Paging, Kotlin Flow, and the `TextFeed` entity.
