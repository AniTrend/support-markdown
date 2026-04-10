# KDoc Checklist

Use these prompts when documenting public APIs in support-markdown.

## Markwon Plugin Class

- Which AniList markdown element or syntax does this plugin handle?
- How does it register itself with the `Markwon` builder?
- Does it require a controller, visitor, renderer, or span?
- What configuration, if any, does a consumer need to provide?

Template:

```kotlin
/**
 * Short summary of the markdown element this plugin handles.
 *
 * Explain how it integrates with [io.noties.markwon.Markwon] and when consumers
 * should include it. Mention required collaborators with KDoc links.
 *
 * @see IMarkdownPlugin
 */
```

## Controller Class

- What nodes does the controller visit?
- What spans or rendering behavior does it apply?
- Are there ordering assumptions or context requirements?

Template:

```kotlin
/**
 * Short summary of the rendering contract.
 *
 * Explain which nodes are visited and what spans or effects are applied.
 *
 * @param ...
 */
```

## Listener Interface

- When is the callback invoked?
- What do the arguments represent?
- What is the implementor expected to do or return?

Template:

```kotlin
/**
 * Callback invoked when [describe the event].
 *
 * Implementors should [describe expected behavior].
 *
 * @param ...
 */
```

## Span Or Node Type

- What does this type represent in the rendered output?
- When is it created or applied?
- Are there Android view context or threading assumptions?

Template:

```kotlin
/**
 * Explains what this span or node represents and when it is applied.
 */
```

## Extension Function Or Property

- Document the receiver explicitly.
- Explain hidden dependencies such as context, lifecycle owner, or thread.
- Call out side effects and mutations.

## Repo-Specific Reminders

- The published Dokka site is `https://anitrend.github.io/support-markdown/`. Documentation is consumer-facing, not optional.
- Use `@since` only when the version is known from `gradle/version.properties` or adjacent code.
- If the behavior changed, update the KDoc in the same patch.
- `CorePlugin` is the entry point consumers most likely read first; keep its documentation accurate.
