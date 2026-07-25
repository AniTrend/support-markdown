# markdown/src/main/kotlin/co/anitrend/support/markdown/image/

## Responsibility

Implements AniList custom image syntax, `imgSIZE(URL)`, and renders it as Markwon images with constrained dimensions.

## Design

`ImagePlugin` uses one regex for case-insensitive custom syntax. It supports both Markwon span rendering through a core text listener and an HTML fallback by converting matches to `<img>` tags during markdown preprocessing.

## Flow

On text addition, matches set `ImageProps.DESTINATION` and `ImageProps.IMAGE_SIZE`, obtain the configured `Image` span factory, and apply spans over the original custom syntax range. In preprocessing, matches become `<img src="..." width="..." />` using normalized dimensions.

## Integration

Requires Markwon core plugin listener support and image span factories from Markwon image handling. The HTML fallback relies on HtmlPlugin support in the configured Markwon instance.
