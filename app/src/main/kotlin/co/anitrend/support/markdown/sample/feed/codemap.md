# app/src/main/kotlin/co/anitrend/support/markdown/sample/feed/

## Responsibility

Implements the feed screen that loads, filters, refreshes, and displays paged markdown text activities.

## Design

`FeedFragment` extends the core fragment base, receives `FeedAdapter` through Koin scoped injection, and gets `AbstractFeedViewModel` through Koin view model lookup. It coordinates RecyclerView setup, swipe refresh, paging load states, click driven filtering, and back navigation reset behavior.

## Flow

When resumed, the fragment seeds a default query if the adapter is empty. The view model emits paging data to the adapter. Load state changes toggle refresh UI, show retry Snackbar errors, and scroll successful refreshes to the top. Avatar clicks emit a user scoped query, and back resets to the default query before closing the activity.

## Integration

Depends on core fragment lifecycle hooks, domain `TextFeedQuery`, the feed adapter, the feed view model contract, AndroidX Paging load states, lifecycle coroutines, Koin, RecyclerView, SwipeRefreshLayout bindings, and Material Snackbar.
