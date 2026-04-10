---
name: support-markdown-kdoc-dokka
description: "Write or improve KDoc for public APIs in support-markdown. Use for Dokka updates, plugin class docs, controller docs, listener docs, span docs, and explaining how downstream apps should use or configure Markwon and commonmark-java backed library behavior."
argument-hint: "Describe the public API, plugin, or documentation gap you need to cover"
---

# Support Markdown KDoc And Dokka

## What This Skill Produces

- Consumer-facing KDoc that reads well on the published Dokka site.
- Documentation that explains plugin contracts, rendering lifecycle, and how downstream apps integrate each markdown feature.
- A repeatable checklist for updating docs whenever public behavior changes.

## When To Use

- Adding or changing a public plugin class, controller, listener interface, span, node, or renderer.
- Explaining how a downstream app should configure or extend a support-markdown plugin.
- Tightening documentation before a release or after a behavior change.

## Procedure

1. Identify the public or protected surface that changed.
2. Read the [KDoc checklist](./references/kdoc-checklist.md) and match the API shape to the closest template.
3. Document what the API does, when to use it, and what a consumer is expected to provide or observe.
4. For plugin classes, explain which markdown element is handled and whether behavior is driven by `processMarkdown`, `configureParser`, `configureVisitor`, `configure(registry)`, or `beforeSetText`.
5. For controllers, explain the visitor or rendering contract and any important ordering or span assumptions.
6. For listener interfaces, explain when the callback fires, what the arguments represent, and what the implementor must do.
7. Distinguish AniList-specific syntax from standard CommonMark or GFM behavior when both can affect the same surface.
8. Link adjacent types with KDoc references so Dokka helps consumers move through the API surface.
9. If the type belongs to a new plugin package, consider whether `CorePlugin` or `IMarkdownPlugin` docs also need an update.

## Quality Bar

- Summary first, details second.
- Avoid tautologies such as repeating the class name without explaining behavior.
- Keep docs aligned with real behavior in the code, not intended behavior from an older implementation.
- State the dependency layer clearly when behavior depends on Markwon or commonmark-java extension points.

## References

- [KDoc checklist](./references/kdoc-checklist.md)
