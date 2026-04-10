---
name: support-markdown-build-dependencies
description: "Understand and change support-markdown build logic, module dependencies, version catalog entries, Dokka setup, and shared Gradle conventions. Use for buildSrc edits, new dependencies, or documentation pipeline work."
argument-hint: "Describe the dependency, Gradle change, or build pipeline task you need to make"
---

# Support Markdown Build And Dependencies

## What This Skill Produces

- A safe path for changing module dependencies or shared build logic.
- A map of where versions, plugins, Dokka, Spotless, and Android defaults are defined.
- Clear guidance on whether a change belongs in a module build file, the version catalog, or `buildSrc`.

## When To Use

- Adding or upgrading dependencies.
- Changing module relationships.
- Editing Dokka, Spotless, JDK, Android, publishing, or test conventions.
- Understanding how the shared plugin wires up each module.

## Procedure

1. Read the [build map](./references/build-map.md) to find the owning file for the convention you want to change.
2. If the change introduces or upgrades a dependency, add the version and alias in `gradle/libs.versions.toml` first.
3. If the behavior should apply to all modules, implement it in `buildSrc` instead of duplicating it in individual module build files.
4. Keep Dokka, Spotless, and test behavior aligned with the shared configuration in `buildSrc`.
5. When running Gradle locally, use the existing `jenv-gradle-low-ram` skill if Java selection or memory pressure becomes an issue.

## References

- [build map](./references/build-map.md)
