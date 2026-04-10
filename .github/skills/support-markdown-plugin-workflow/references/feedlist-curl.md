# FeedList Curl Workflow

Use the local GraphQL query file as the source of truth when you want real AniList text activity samples.

## Query File

- `app/src/main/graphql/co/anitrend/support/markdown/data/FeedList.graphql`

## Working Curl Pattern

This request was validated against `https://graphql.anilist.co` without authentication for a small public sample:

```bash
query_path="app/src/main/graphql/co/anitrend/support/markdown/data/FeedList.graphql"
query="$(tr '\n' ' ' < "$query_path")"

curl -s https://graphql.anilist.co \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  --data "{
    \"query\": \"$query\",
    \"variables\": {
      \"page\": 1,
      \"perPage\": 2,
      \"asHtml\": false
    }
  }"
```

## Why `asHtml: false` First

- It exposes the raw markdown string that support-markdown is expected to parse.
- It helps distinguish server-side HTML formatting from markdown the library must handle locally.

Use `asHtml: true` only as a comparison point when you need to understand how AniList normalizes the same content upstream.

## Sample Shape Observed

The response can contain both plain text and heavily formatted AniList markdown in the same feed. A validated sample included content like:

```text
Imported current MAL list on 2026-04-10

# ~~~Daily Umineko~~~
<hr width=100%>

~~~img(https://i.imgur.com/RdTByeX.png)~~~
```

## Fixture Rules

- Do not commit full live payloads when a reduced snippet will do.
- Preserve line breaks and raw marker syntax when extracting a test fixture.
- Keep at least one scenario that mixes standard markdown or HTML with AniList-specific syntax.