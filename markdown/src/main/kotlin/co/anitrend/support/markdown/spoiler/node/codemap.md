# markdown/src/main/kotlin/co/anitrend/support/markdown/spoiler/node/

## Responsibility

Defines the custom commonmark AST node for spoiler content.

## Design

`SpoilerNode` extends `CustomNode` and carries no extra fields. Content is represented by child nodes moved under it during delimiter processing.

## Flow

The shared tilde delimiter processor creates the node and appends all nodes between opener and closer after trimming spoiler bang markers.

## Integration

Rendered by `SpoilerPlugin.configureVisitor`, which applies reveal and hide spans to the node's visited children.
