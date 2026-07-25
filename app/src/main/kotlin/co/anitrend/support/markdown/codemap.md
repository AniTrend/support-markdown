# app/src/main/kotlin/co/anitrend/support/markdown/

## Responsibility

Package namespace aggregator for the sample application. It groups the app specific markdown demo code into core support, domain contracts, data access, and UI sample layers.

## Design

No Kotlin sources live directly in this folder. Child maps describe the concrete responsibilities:

- [core](core/codemap.md) for base Android components, extensions, and Markwon image plugins.
- [domain](domain/codemap.md) for feed entities, query models, and use case contracts.
- [data](data/codemap.md) for AniList GraphQL paging and dependency bindings.
- [sample](sample/codemap.md) for the runnable Android sample UI.

## Flow

Application control starts in the sample package, resolves dependencies through Koin, asks the domain interactor for paged feed data, and renders markdown through core helpers and plugins.

## Integration

This namespace depends on generated Apollo models from the GraphQL query, the markdown library modules under the main library source set, AndroidX Paging and lifecycle APIs, Koin, Coil, and Markwon.
