# app/src/main/kotlin/co/anitrend/support/markdown/data/

## Responsibility

Implements the data layer for loading AniList text activity feed pages into domain feed models.

## Design

`FeedDataSource` is an AndroidX `PagingSource` backed by Apollo GraphQL. It converts generated `FeedListQuery` activity results into `TextFeed` entities, formats creation timestamps, and computes paging keys. Child maps cover Koin bindings, repository construction, and the use case implementation.

## Flow

A `TextFeedQuery` enters the repository, which creates a `Pager` using `FeedDataSource`. The data source executes `FeedListQuery`, maps response data into `TextFeed`, and emits `LoadResult.Page` or `LoadResult.Error` for Paging.

## Integration

Consumes generated Apollo classes from `app/src/main/graphql`, exposes data through the domain interactor implementation, and is wired into the app through `dataModules` for Koin.
