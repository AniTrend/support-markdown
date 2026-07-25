# buildSrc/src/main/java/co/anitrend/support/markdown/buildSrc/plugin/components/

## Responsibility

Contains the concrete Gradle configuration steps invoked by `CorePlugin`: plugin application, Android defaults, publishing options, dependency entry point, and release property loading.

## Design

Configuration is organized as internal `Project` extension functions. `PropertiesReader` loads `gradle/version.properties` using `PropertyTypes` keys. Module branching uses `isSampleModule()` and `isLibraryModule()` to separate app sample behavior from markdown library behavior.

## Flow

`configurePlugins()` applies Spotless, Android application or library plugin, Kotlin Android, optional Kapt, Dokka, and Maven Publish. `configureAndroid()` sets SDK levels, version metadata, build types, source sets, test options, Java 21, Kotlin JVM toolchain, vector drawable support, sample app id, and library consumer ProGuard files. `configureOptions()` registers source and class jars and creates the Maven publication for the library module. `configureDependencies()` adds local jars and delegates catalog dependency selection to `DependencyStrategy`.

## Integration

Consumes helpers from `common`, Gradle extension accessors from `plugin/extensions`, and dependency rules from `plugin/strategy`. Reads repository version metadata from `gradle/version.properties` and configures Android, Dokka, Spotless, Kotlin, publication artifacts, and dependency handlers.
