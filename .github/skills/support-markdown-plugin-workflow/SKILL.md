---
name: support-markdown-plugin-workflow
description: "Implement or debug support-markdown plugins using real AniList feed fixtures, FeedList.graphql curl validation, and Markwon/commonmark-java extension points. Use for new plugin work, parser or rendering bugs, markdown test design, and deciding whether behavior belongs in processMarkdown, configureParser, configureVisitor, HtmlPlugin handlers, or beforeSetText."
argument-hint: "Describe the plugin, syntax, or markdown behavior you need to implement or verify"
---

# Support Markdown Plugin Workflow

## What This Skill Produces

- A grounded implementation plan for the correct layer of the stack.
- Live-fixture guidance for pulling representative AniList text activity samples.
- Test scenarios that cover both AniList-specific shorthand and the standard markdown features mixed into real feed content.

## When To Use

- Adding a new markdown plugin under `:markdown`.
- Fixing parser, rendering, or HTML pass-through bugs in an existing plugin.
- Designing tests for AniList feed content that mixes standard markdown with custom syntax.
- Deciding whether to rewrite text before parsing or extend Markwon/commonmark-java directly.

## Procedure

1. If package ownership is unclear, apply the `support-markdown-reference-map` skill first.
2. Read the [dependency stack](./references/dependency-stack.md) and place the problem at the right layer before editing code.
3. Pull representative live samples with the [FeedList curl workflow](./references/feedlist-curl.md). Start with `asHtml: false` so you inspect the raw markdown the library is expected to parse.
4. Reduce any harvested feed text into minimal local fixtures. Keep the original sample nearby in notes while trimming unrelated content.
5. Map the change to one primary extension point:
   - `processMarkdown` for deterministic text-to-text rewrites before parsing.
   - `configureParser` when syntax should become part of the commonmark AST.
   - `configureVisitor` or `configureSpansFactory` when parsed nodes need custom rendering.
   - `configure(registry)` with `HtmlPlugin` when custom HTML tags or tag handlers are involved.
   - `beforeSetText` only for final `TextView`-level cleanup or adjustments.
6. Build scenarios from the [test scenario matrix](./references/test-scenarios.md), with at least one case grounded in a real feed sample and one collision case against existing markdown behavior.
7. Keep changes within `:markdown`; only touch `CorePlugin` when the behavior is intentionally cross-cutting.
8. Add or update targeted unit tests in `markdown/src/test/kotlin/...` next to the owning plugin package.

## Quality Bar

- Use real AniList feed content to inform at least one test case.
- Treat standard CommonMark and GFM constructs as first-class scenarios because AniList posts mix them with custom markup.
- Prefer the smallest Markwon/commonmark hook that solves the problem without hard-coding sample-app behavior into the library.
- Avoid adding a new dependency when an existing Markwon module already exposes the needed hook.

## References

- [dependency stack](./references/dependency-stack.md)
- [FeedList curl workflow](./references/feedlist-curl.md)
- [test scenario matrix](./references/test-scenarios.md)