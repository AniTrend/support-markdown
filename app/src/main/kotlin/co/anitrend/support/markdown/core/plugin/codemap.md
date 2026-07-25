# app/src/main/kotlin/co/anitrend/support/markdown/core/plugin/

## Responsibility

Hosts the sample app plugin that connects Markwon image nodes to Coil asynchronous image loading.

## Design

`CoilImagePlugin` extends `AbstractMarkwonPlugin`, registers Markwon image span support, configures a custom async drawable loader, and schedules or unschedules async drawable work around `TextView` updates. Child maps describe request storage, loader behavior, and Coil target updates.

## Flow

When Markwon renders markdown with image syntax, it asks the configured loader to fetch the image. Before new text is applied, current drawable work is unscheduled. After text is set, async drawables are scheduled for loading and display updates.

## Integration

Constructed in sample Koin through `CoilImagePlugin.create(...)`. Uses Markwon image APIs, CommonMark image nodes, Coil `ImageLoader`, and the store, loader, and target classes in this package.
