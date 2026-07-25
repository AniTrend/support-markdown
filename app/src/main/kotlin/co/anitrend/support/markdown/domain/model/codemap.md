# app/src/main/kotlin/co/anitrend/support/markdown/domain/model/

## Responsibility

Defines request parameters for loading AniList text feed pages.

## Design

`TextFeedQuery` stores feed filters and paging settings: reply or text activity filtering, HTML output selection, page size, and optional user filtering.

## Flow

The fragment creates default and user scoped queries. The view model forwards them to the interactor. The data source converts each query into Apollo `FeedListQuery` variables.

## Integration

Used by feed UI, view model, domain interactor, repository, and data source. Its fields map directly to GraphQL variables in `FeedList.graphql`.
