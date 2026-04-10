---
description: Use when making architecture changes, module graph changes, package ownership changes, public API shifts, build logic updates, documentation workflow changes, or editing repository customizations in support-markdown. Keeps instructions, skills, and consumer-facing context aligned with current repository behavior.
applyTo: "build.gradle.kts, settings.gradle, gradle/**/*.toml, buildSrc/**/*.kt, */build.gradle.kts, markdown/src/main/**/*.kt, .github/instructions/*.md, .github/skills/**, README.md"
---

# Context Maintenance Guidance

- When a change materially alters repository reality, update the relevant repo guidance in the same patch instead of leaving instructions and skills stale.
- Treat the following as context-bearing assets that may need maintenance after major changes: `.github/instructions/*.md`, `.github/skills/**`, and `README.md`.
- Audit repo guidance when you change module boundaries, dependency direction, package ownership, shared build conventions, Dokka behavior, or consumer-facing extension points.
- Audit KDoc and Dokka guidance when public APIs, plugin extension contracts, or downstream integration patterns change.
- Audit the `support-markdown-reference-map` and `support-markdown-build-dependencies` skills when a package gains a new responsibility, a new plugin is added, or the build and publishing workflow changes.
- Remove or rewrite contradictory guidance instead of layering new instructions on top of obsolete ones.
- Prefer updating an existing instruction or skill when the workflow still fits; add a new instruction or skill only when a genuinely new recurring concern appears.
- Keep changes specific and low-churn: update only the files whose guidance is no longer true.
- If a change affects how downstream apps should discover, import, extend, or configure support-markdown plugins, update the relevant consumer-facing guidance in the same change.
