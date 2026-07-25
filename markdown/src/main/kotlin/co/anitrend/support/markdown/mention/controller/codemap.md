# markdown/src/main/kotlin/co/anitrend/support/markdown/mention/controller/

## Responsibility

Provides the mention matching and URL construction utility used by mention rendering.

## Design

`MentionTextAddedController` keeps a compiled multiline regex and exposes small methods for match lookup, username extraction, and AniList profile URL formatting. It is internal to the mention feature.

## Flow

`findAllMatches` returns regex matches for `(^|>| )@([A-Za-z0-9]+)`. `getContent` returns the captured username. `asUserUrl` combines the username with the AniList profile base URL.

## Integration

Called by `OnMentionTextAddedListener`, which converts controller results into Markwon link spans over the source text.
