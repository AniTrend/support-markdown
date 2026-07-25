# markdown/src/main/kotlin/co/anitrend/support/markdown/mention/

## Responsibility

Implements AniList user mention support for `@username` text patterns.

## Design

`MentionPlugin` wires an `OnMentionTextAddedListener` into Markwon core. The listener delegates pattern finding and AniList URL construction to `MentionTextAddedController`, then applies Markwon link spans over matched mention ranges.

## Flow

When Markwon adds text, the listener finds mention matches, extracts the username, builds `https://anilist.co/user/{username}`, sets `CoreProps.LINK_DESTINATION`, obtains the `Link` span factory, and applies spans to the mention text.

## Integration

Requires Markwon core listener support and the configured Markwon link span factory. The `controller` child package owns regex and URL mechanics.
