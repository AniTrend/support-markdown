---
description: Use when adding or changing public Kotlin APIs, KDoc, Dokka output, class docs, function docs, or property docs in the markdown module.
applyTo: "markdown/src/main/**/*.kt"
---

# KDoc And Dokka Guidance

- Treat KDoc as consumer documentation. The generated Dokka site at `https://anitrend.github.io/support-markdown/` is how downstream apps discover and understand the library surface.
- Document every new or changed public or protected class, interface, object, enum, annotation, function, and property that a consumer may touch.
- Write documentation for someone outside this repo who does not already know the architecture. Explain what the API is for, when to use it, and which plugin or workflow it belongs to.
- For Markwon plugin classes, document what markdown element the plugin handles, how it integrates with the `Markwon` builder, and any configuration or lifecycle expectations.
- For controller classes, document the visitor or rendering contract: what nodes are visited, what spans are applied, and any important ordering or context assumptions.
- For listener interfaces (such as `OnMentionTextAddedListener`), document when the callback fires, what the arguments represent, and what the implementor is expected to do.
- For span and node types, document what they represent in the rendered output and any assumptions about the Android view context.
- For extension functions or properties, document the receiver, side effects, threading or lifecycle assumptions, and any important nullability or mutation behavior.
- Link adjacent types with KDoc references so Dokka helps consumers move through the API surface.
- Preserve the existing house style: a short summary first, then focused detail, with `@param`, `@property`, `@return`, `@throws`, and `@see` where they add value.
- Do not invent version history. Only add `@since` when the version is already known from adjacent code.
- Avoid placeholder KDoc that only restates the type name. Explain behavior, expectations, and integration points.
- If behavior changes, update the docs in the same patch so the published site stays trustworthy.
