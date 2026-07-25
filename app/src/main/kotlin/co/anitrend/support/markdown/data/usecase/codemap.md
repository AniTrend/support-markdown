# app/src/main/kotlin/co/anitrend/support/markdown/data/usecase/

## Responsibility

Provides the concrete domain interactor implementation for fetching paged text feeds.

## Design

`TextFeedUseCase` extends the abstract `GetTextFeedPaged` contract and delegates all data retrieval to `GetFeedTextPagedRepository`. It wraps the returned paging flow in `PagingDataState`.

## Flow

A caller invokes the use case with `TextFeedQuery`. The use case calls the repository and returns a state object containing the resulting `Flow<PagingData<TextFeed>>`.

## Integration

Bound as `GetTextFeedPaged` in data Koin modules and consumed by `FeedViewModel`. Depends on the domain interactor contract, query model, paging state, and repository class.
