# app/src/main/kotlin/co/anitrend/support/markdown/domain/interactor/

## Responsibility

Declares the use case boundary for retrieving paginated text feed data.

## Design

`GetTextFeedPaged` is an abstract callable class. Implementations accept `TextFeedQuery` and return `PagingDataState`, allowing UI code to depend on an abstract contract.

## Flow

`FeedViewModel` invokes the interactor with the current query. The concrete data use case resolves the query through the repository and returns the paging state.

## Integration

Implemented by `TextFeedUseCase` and bound in Koin as the abstraction injected into `FeedViewModel`.
