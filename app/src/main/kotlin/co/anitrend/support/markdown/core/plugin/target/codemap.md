# app/src/main/kotlin/co/anitrend/support/markdown/core/plugin/target/

## Responsibility

Receives Coil image load callbacks and applies the resulting drawables back to Markwon async drawables.

## Design

`AsyncDrawableTarget` implements Coil `Target`. It uses an `AtomicBoolean` to handle callbacks that can arrive before the disposable is cached, removes completed drawables from loader state, applies intrinsic bounds, and starts animated drawables when attached.

## Flow

On start, a placeholder is applied if present. On success, the loaded drawable replaces the Markwon drawable result and animation starts when applicable. On error, an error drawable is applied if the load is still tracked.

## Integration

Instantiated by `CoilAsyncDrawableLoader`. Depends on Coil target callbacks and Markwon `DrawableUtils` for sizing rendered markdown images.
