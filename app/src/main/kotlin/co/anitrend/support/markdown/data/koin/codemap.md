# app/src/main/kotlin/co/anitrend/support/markdown/data/koin/

## Responsibility

Defines Koin modules for the sample app data layer, including networking, repository, and domain interactor bindings.

## Design

The module list separates use case, repository, and network setup. `GetTextFeedPaged` is bound to `TextFeedUseCase`, repositories are created as factories, and network clients are singletons.

## Flow

Koin creates an `OkHttpClient`, uses it to build an Apollo client pointed at `https://graphql.anilist.co`, injects that client into `GetFeedTextPagedRepository`, and injects the repository into `TextFeedUseCase`.

## Integration

`dataModules` is appended to `appModules` in the sample Koin package. Depends on Koin DSL, Apollo Client, Apollo OkHttp integration, OkHttp, and domain contracts.
