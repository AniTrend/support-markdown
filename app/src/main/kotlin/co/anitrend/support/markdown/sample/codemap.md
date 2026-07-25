# app/src/main/kotlin/co/anitrend/support/markdown/sample/

## Responsibility

Contains the runnable Android sample app that demonstrates rendering AniList markdown feed content with the support markdown library.

## Design

`App` starts Koin, installs the fragment factory, and exposes the Koin provided Coil `ImageLoader`. Child packages define the main activity, feed UI, view model, adapter, and dependency modules.

## Flow

Application startup registers Koin modules. `MainActivity` hosts `FeedFragment`. The fragment drives feed queries through the view model, receives paging data, and binds rows through a Markwon powered adapter.

## Integration

Combines data layer modules, markdown library plugins, Coil, Markwon, Koin, AndroidX Fragment and Paging, generated view bindings, and Material components.
