---
name: support-markdown-build-dependencies
description: "Understand and change support-markdown build logic, Markwon/commonmark-java dependency wiring, module dependencies, version catalog entries, Dokka setup, and shared Gradle conventions. Use for buildSrc edits, new dependencies, parser or renderer dependency changes, or documentation pipeline work."
argument-hint: "Describe the dependency, Gradle change, or build pipeline task you need to make"
---

# Support Markdown Build And Dependencies

## What This Skill Produces

- A safe path for changing module dependencies or shared build logic.
- A map of where versions, plugins, Dokka, Spotless, and Android defaults are defined.
- Clear guidance on whether a change belongs in a module build file, the version catalog, or `buildSrc`.
- A dependency-layer view of the current Markwon surface and where commonmark-java enters parser work.

## When To Use

- Adding or upgrading dependencies.
- Changing module relationships.
- Editing Dokka, Spotless, JDK, Android, publishing, or test conventions.
- Understanding how the shared plugin wires up each module.
- Evaluating whether parser or renderer work needs a new Markwon artifact or a direct commonmark-java dependency.

## Procedure

1. Read the [build map](./references/build-map.md) to find the owning file for the convention you want to change.
2. Inventory the current `:markdown` dependency surface before adding anything new, especially for Markwon parser and renderer features.
3. If the change introduces or upgrades a dependency, add the version and alias in `gradle/libs.versions.toml` first.
4. Prefer the existing Markwon modules already present in `markdown/build.gradle.kts` before adding a new artifact.
5. If the behavior should apply to all modules, implement it in `buildSrc` instead of duplicating it in individual module build files.
6. Keep Dokka, Spotless, and test behavior aligned with the shared configuration in `buildSrc`.
7. If the task is really about plugin implementation or fixture-driven test design, also apply the `support-markdown-plugin-workflow` skill.
8. When running Gradle locally, use the existing `jenv-gradle-low-ram` skill if Java selection or memory pressure becomes an issue.

## References

- [build map](./references/build-map.md)
