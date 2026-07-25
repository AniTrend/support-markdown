# app/src/main/kotlin/co/anitrend/support/markdown/sample/koin/

## Responsibility

Assembles the sample app dependency graph for markdown rendering, image loading, UI components, view models, and data modules.

## Design

`coreModule` builds a Markwon instance with the support markdown plugins and extra Markwon extensions. `viewModelModule` binds `AbstractFeedViewModel` to `FeedViewModel`. `fragmentModule` scopes `FeedFragment` and its adapter to `MainActivity`. `imageLoaderModule` creates Coil image loaders with disk cache and GIF decoder support.

## Flow

`App.onCreate` starts Koin with `appModules`. Markwon is configured once, FeedFragment is built with a Markwon backed adapter, FeedViewModel receives the domain interactor, and Coil provides image loading for both app images and markdown images.

## Integration

Imports plugin implementations from the markdown library module, data modules from the data package, Coil image components, Markwon extensions, Koin Android DSL, app resources, `MainActivity`, `FeedFragment`, `FeedAdapter`, and `FeedViewModel`.
