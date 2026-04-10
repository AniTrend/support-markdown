---
description: Use when editing Gradle files, module dependencies, version catalog entries, GitHub workflows, or buildSrc logic in support-markdown.
applyTo: "build.gradle.kts, settings.gradle, gradle/**/*.toml, gradle/**/*.properties, buildSrc/**/*.kt, */build.gradle.kts, .github/workflows/*.yml"
---

# Build Logic Guidance

- Prefer the shared `co.anitrend.support.markdown` plugin and `buildSrc` helpers over duplicating Android, Kotlin, Dokka, Spotless, publishing, or test configuration in individual modules.
- The repo Java toolchain pin is `.java-version = 21.0.8`. Systems are expected to have `jenv` installed, which reads this file automatically to select the correct JDK. Keep all build logic compatible with this pin.
- Source and target compatibility is set to Java 17 in `AndroidConfiguration.kt`. Do not lower this without a clear reason.
- Add or update dependency versions in `gradle/libs.versions.toml` first, then reference the alias from modules or build logic.
- `:markdown` is the sole Android library module and the only distributable artifact. `:app` is a sample and must not be treated as a dependency target.
- Shared Android defaults come from `buildSrc/…/components/AndroidConfiguration.kt`, including SDK levels, source compatibility, lint, test options, and Spotless.
- Publishing configuration (JitPack, sources JAR, classes JAR, POM metadata) lives in `buildSrc/…/components/AndroidOptions.kt`.
- Shared dependency defaults come from `buildSrc/…/strategy/DependencyStrategy.kt`.
- If you need a new convention across modules, prefer adding it once in `buildSrc` instead of repeating it in each `build.gradle.kts` file.
- When validating Gradle changes locally, pair the work with the `jenv-gradle-low-ram` skill if JDK alignment or memory pressure becomes a problem.
