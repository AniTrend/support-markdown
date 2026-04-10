# Test Scenario Matrix

Use this matrix to turn feed samples into focused tests.

## Baseline Scenarios

| Scenario | Why it matters |
| --- | --- |
| Single-feature happy path | Verifies the owning plugin still recognizes its primary syntax |
| Multiple matches in one body | Existing tests already rely on regex count behavior |
| Leading heading or paragraph text before custom syntax | Real AniList posts often mix prose and formatted blocks |
| Standard markdown link or emphasis adjacent to custom syntax | AniList content is not purely bespoke; CommonMark behavior must keep working |
| Inline HTML near custom syntax | `CorePlugin` explicitly supports HTML handlers and non-closed tags |

## Collision Scenarios

| Scenario | Why it matters |
| --- | --- |
| Custom syntax inside code-like or escaped regions | String-rewrite plugins can over-match |
| Multiple custom plugins in one sample | Center, spoiler, media, and links can overlap in real posts |
| Soft line breaks and empty lines around custom syntax | `CorePlugin` customizes soft-break handling |
| Unicode, entities, or non-English text | Live feed samples already include multilingual and entity-heavy content |

## Query Variants

- Start with `asHtml: false` for parser behavior.
- Compare `asHtml: true` only when validating whether upstream HTML normalization changes the expected input shape.

## Repo Test Style

- Add tests close to the owning plugin package under `markdown/src/test/kotlin/...`.
- Keep fixtures small, but realistic enough to preserve the interaction you are validating.
- For regex-driven plugins, keep count-based detection tests like the existing suite.
- When behavior goes beyond regex detection, add focused transformation or rendering assertions instead of inflating one giant sample.