# app/src/main/kotlin/co/anitrend/support/markdown/core/

## Responsibility

Provides shared Android base classes and rendering helpers used by the sample app. It centralizes view binding safety, Koin scoped component setup, fragment lifecycle hooks, and markdown text cleanup.

## Design

`AbstractActivity` and `AbstractFragment` are generic `ViewBinding` bases with `requireBinding()` guards and Koin Android scopes. Fragment subclasses implement fetch, initialization, and view model observer hooks. Child maps cover extensions and the Coil backed Markwon image plugin.

## Flow

Activities install the Koin fragment factory before normal creation. Fragments initialize components in `onCreate`, trigger first data loading when resumed, start their observer job in `onViewCreated`, and cancel or clear resources during teardown.

## Integration

Used by `MainActivity`, `FeedFragment`, and `FeedAdapter`. Integrates AndroidX AppCompat, Fragment, lifecycle coroutines, Koin scopes, ViewBinding, BetterLinkMovementMethod, Markwon, and Coil image rendering.
