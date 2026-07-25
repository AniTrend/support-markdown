# app/

## Responsibility

Android sample application for exercising the markdown library in a real feed screen. It is excluded on CI by `settings.gradle`, uses namespace `co.anitrend.support.markdown.sample`, and depends on the local `:markdown` module.

## Design

- Applies the local `co.anitrend.support.markdown` Gradle convention plugin and the Apollo plugin.
- Apollo service `main` generates GraphQL models under `co.anitrend.support.markdown.domain.entities`.
- Source is organized around data, domain, sample UI, and sample specific Markwon image loading helpers.
- Koin wires application services, fragments, view models, data access, Markwon, and Coil.
- The sample Markwon builder combines library plugins with Markwon HTML, linkify, strikethrough, task list support, and a Coil backed image plugin.

## Flow

`App` starts Koin and exposes the shared Coil `ImageLoader`. `FeedFragment` initializes a paged RecyclerView, emits `TextFeedQuery` values, and observes `FeedViewModel.result`. `FeedViewModel` calls `GetTextFeedPaged`, which delegates through `TextFeedUseCase` and `GetFeedTextPagedRepository` to a Paging `FeedDataSource`. The data source queries `https://graphql.anilist.co` with Apollo, maps activities into `TextFeed`, and returns paging keys to AndroidX Paging.

## Integration

- Depends on `:markdown` for custom Markwon plugins such as core, mention, center, image, webm, YouTube, spoiler, strike, emphasis, heading, horizontal line, and italics support.
- Uses `gradle/libs.versions.toml` aliases for AndroidX, Material, Paging, Markwon, Coil, and BetterLinkMovementMethod dependencies.
- Uses direct Apollo runtime coordinate `com.apollographql.apollo:apollo-runtime:5.0.1` and the Apollo Gradle plugin.
- Excludes the default `org.jetbrains:annotations` module from implementation and Android test implementation configurations.
