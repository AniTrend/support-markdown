# app/src/main/kotlin/co/anitrend/support/markdown/domain/entities/

## Responsibility

Defines the domain model rendered by the feed sample.

## Design

`TextFeed` captures text activity fields needed by the UI: identifiers, markdown text, formatted timestamp, user details, counts, and site URL. Nested `User` and `Avatar` data classes keep author data colocated with feed items.

## Flow

`FeedDataSource` maps generated GraphQL activity responses into `TextFeed`. Paging emits these entities to the view model, and `FeedAdapter` binds them to row views.

## Integration

Used across data, domain state, view model, and adapter code. Apollo generated `FeedListQuery` data is converted into this shape at the data boundary.
