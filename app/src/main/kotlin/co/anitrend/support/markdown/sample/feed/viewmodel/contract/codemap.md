# app/src/main/kotlin/co/anitrend/support/markdown/sample/feed/viewmodel/contract/

## Responsibility

Defines the feed view model contract consumed by the feed fragment.

## Design

`AbstractFeedViewModel` extends AndroidX `ViewModel` and exposes a mutable query state, a paging result flow, the protected `GetTextFeedPaged` dependency, and a suspend callable function for executing a query.

## Flow

The fragment writes query values and calls the view model with explicit query requests. Implementations turn those requests into paging data and publish them through `result`.

## Integration

Implemented by `FeedViewModel`, injected into `FeedFragment` as an abstraction through Koin, and typed with domain `TextFeed`, `TextFeedQuery`, and `GetTextFeedPaged`.
