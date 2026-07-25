# app/src/main/kotlin/co/anitrend/support/markdown/sample/feed/viewmodel/

## Responsibility

Implements feed screen state management between UI queries and domain paging results.

## Design

`FeedViewModel` extends `AbstractFeedViewModel`, stores a `MutableStateFlow<PagingData<TextFeed>>`, and injects the `GetTextFeedPaged` interactor. It listens to non null query changes in `viewModelScope` and delegates each query to its callable implementation.

## Flow

When the fragment updates `query`, the view model invokes the interactor, caches the returned paging flow in `viewModelScope`, and emits all paging data into `result` for the fragment to collect.

## Integration

Bound through sample Koin as `AbstractFeedViewModel`. Depends on AndroidX lifecycle view model scope, Paging cached flows, Kotlin Flow, and domain interactor contracts.
