# app/src/main/kotlin/co/anitrend/support/markdown/data/repository/

## Responsibility

Creates the paged feed stream used by the domain use case.

## Design

`GetFeedTextPagedRepository` wraps Apollo access behind a small callable class. It builds an AndroidX `Pager` with a `PagingConfig` based on `TextFeedQuery.pageSize` and disables placeholders for network only feed results.

## Flow

The repository receives a `TextFeedQuery`, constructs a new `FeedDataSource` through `pagingSourceFactory`, and returns `pager.flow` as `Flow<PagingData<TextFeed>>`.

## Integration

Injected into `TextFeedUseCase` by data Koin modules. Depends on `FeedDataSource`, Apollo Client, AndroidX Paging, and domain feed models.
