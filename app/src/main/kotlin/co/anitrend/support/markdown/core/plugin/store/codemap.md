# app/src/main/kotlin/co/anitrend/support/markdown/core/plugin/store/

## Responsibility

Builds Coil image requests for Markwon async drawables and owns cancellation behavior for their disposables.

## Design

`CoilStore` defines request creation plus a default safe cancel operation. `CoilStorePlugin` wraps an `ImageRequest.Builder`, sets crossfade, drawable destination, sample placeholder and error drawables, and derives request size from Markwon image metadata when available.

## Flow

The loader passes each `AsyncDrawable` to `CoilStore.load`. The store returns a configured Coil request, then the loader adds its target and enqueues it. Cancellation routes through `CoilStore.cancel`.

## Integration

Created from sample Koin when building `CoilImagePlugin`. Uses Coil request APIs, Markwon `AsyncDrawable`, and sample drawable resources for placeholder and error states.
