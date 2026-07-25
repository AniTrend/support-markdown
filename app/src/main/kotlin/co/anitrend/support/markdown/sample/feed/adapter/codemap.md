# app/src/main/kotlin/co/anitrend/support/markdown/sample/feed/adapter/

## Responsibility

Renders paged text feed items into RecyclerView rows and publishes author click events for feed filtering.

## Design

`FeedAdapter` extends `PagingDataAdapter<TextFeed, ViewHolder>` with a `DiffUtil.ItemCallback`. Each view holder owns Coil avatar loading, Markwon text rendering, and cleanup for recycled rows. `clickState` is a mutable flow that emits the clicked feed item.

## Flow

`onBindViewHolder` retrieves the current `TextFeed`, loads the author avatar with Coil, binds author and timestamp text, renders markdown with `setMarkdown`, and attaches an avatar click listener. Recycling clears the click listener, destroys text movement callbacks, and disposes the Coil request.

## Integration

Used by `FeedFragment` and constructed in sample Koin. Depends on AndroidX Paging, RecyclerView, generated row binding, Coil, Markwon, core text extensions, and the domain `TextFeed` entity.
