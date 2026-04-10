---
name: support-markdown-reference-map
description: "Reference map for support-markdown modules, plugin packages, dependency layers, Markwon/commonmark-java ownership boundaries, consumer entry points, and Dokka navigation. Use for questions like which package should own this code, where a class should live, or how the library is organized."
argument-hint: "Describe the feature, plugin, or consumer workflow you are trying to place or understand"
---

# Support Markdown Reference Map

## What This Skill Produces

- A fast package-placement decision for new or existing code.
- A plugin-level map of where to search next.
- A consumer-oriented view of which classes are likely to be imported, observed, or extended.
- A handoff point when the real question is Markwon or commonmark-java behavior instead of package ownership.

## When To Use

- Choosing where a new plugin class, controller, span, or listener belongs.
- Understanding which package a consumer should import from.
- Mapping a downstream use case back to the owning plugin package.
- Explaining repo structure before deeper implementation work.

## Procedure

1. Start with the [module reference map](./references/module-map.md) and identify the plugin package that owns the markdown element.
2. Match the task to a plugin family before picking a file. For example: inline text effects go to a dedicated plugin package, HTML tag handling goes to `html/`, shared contracts go to `common/`.
3. If the task is really about parser hooks, rendering hooks, or fixture-driven markdown behavior, switch to the `support-markdown-plugin-workflow` skill after confirming package ownership.
4. Confirm that the change does not introduce a dependency from `:markdown` on `:app`.
5. Open the Dokka page for the module if you need consumer-facing context or neighboring public types: `https://anitrend.github.io/support-markdown/`.
6. If the task changes a public API, also apply the `support-markdown-kdoc-dokka` skill so the published docs stay aligned.

## Outputs To Aim For

- Package name and module
- Candidate class or file name following existing naming conventions
- Relevant neighboring abstractions
- Dependency layer if Markwon or commonmark-java behavior is part of the decision
- Consumer impact summary

## References

- [module reference map](./references/module-map.md)
