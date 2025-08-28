---
applyTo: **
description: This file describes the architecture and structure of the AniTrend support-markdown library project.
---

# Support Markdown Library Overview

The **support-markdown** library is an Android markdown parsing library that follows the [AniList markdown specification](https://files.kiniro.uk/anilist-flavored-markdown-v1.md) and is powered by [Markwon](https://noties.github.io/Markwon/).

## Project Structure

This is a multi-module Android library project with the following structure:

### Core Modules
- **`:app`** - Sample application demonstrating the library usage with UI components
- **`:markdown`** - Core library module containing the markdown parsing logic and utilities
- **`:buildSrc`** - Build configuration and shared build logic for all modules

### Key Components

#### Markdown Module (`:markdown`)
The main library module that provides:
- Markdown parsing functionality using Markwon
- AniList-flavored markdown support
- Integration with Android UI components
- Image loading support via Coil
- Link handling with BetterLinkMovementMethod

#### App Module (`:app`) 
The sample application that demonstrates:
- How to integrate the markdown library
- Text rendering with markdown support
- Image loading in markdown content
- Link interaction handling

#### BuildSrc Module (`:buildSrc`)
Contains centralized build configuration:
- Gradle plugin configurations
- Dependency management strategies
- Android build options and configurations
- Publishing setup for JitPack distribution

## Architecture Patterns

The library follows standard Android library patterns:
- **Modular design** - Separation between library code and sample app
- **Gradle-based build system** - Using Kotlin DSL for build configuration
- **Publishing support** - Configured for JitPack distribution
- **Testing support** - Unit tests and Android instrumentation tests

## Key Technologies

- **Markwon** - Core markdown rendering engine
- **Coil** - Image loading for markdown images
- **BetterLinkMovementMethod** - Enhanced link handling
- **Android SDK** - Target platform with minimum SDK 21
- **Kotlin** - Primary programming language
- **Gradle** - Build system with Kotlin DSL

## Distribution

The library is distributed via JitPack, allowing easy integration:
```gradle
allprojects {
    repositories {
        maven { url 'https://www.jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.anitrend:support-markdown:{latest_version}'
}
```

## Documentation

Full API documentation is available at: https://anitrend.github.io/support-markdown/

## Commit Conventions

The project uses conventional commits with generic scopes:
- `feat:` - New features
- `fix:` - Bug fixes  
- `docs:` - Documentation changes
- `style:` - Code style changes
- `refactor:` - Code refactoring
- `test:` - Test additions/changes
- `chore:` - Build/tooling changes

Scopes should be kept generic and focus on the type of change rather than specific modules.