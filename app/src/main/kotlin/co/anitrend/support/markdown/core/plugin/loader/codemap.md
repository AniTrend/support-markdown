# app/src/main/kotlin/co/anitrend/support/markdown/core/plugin/loader/

## Responsibility

Implements the Markwon `AsyncDrawableLoader` bridge that queues Coil requests for markdown image drawables.

## Design

`CoilAsyncDrawableLoader` keeps a small `AsyncDrawable` to `Disposable` cache so in flight image work can be cancelled. It builds each request through `CoilStore`, attaches an `AsyncDrawableTarget`, and tracks early completion with an `AtomicBoolean`.

## Flow

`load` creates a target, asks the store for an image request, adds the target, and enqueues it through Coil. `cancel` detaches the drawable, stops animated results, removes the cached disposable, and disposes active work.

## Integration

Configured by `CoilImagePlugin`. Depends on Coil `ImageLoader`, Coil `ImageRequest`, Markwon async drawable APIs, `CoilStore`, and `AsyncDrawableTarget`.
