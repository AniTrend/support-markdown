# app/src/main/kotlin/co/anitrend/support/markdown/sample/component/

## Responsibility

Hosts the sample app activity shell.

## Design

`MainActivity` extends the shared `AbstractActivity` base with `ActivityMainBinding`. It controls a Material bottom drawer, configures the bottom app bar menu, and installs the feed fragment into the content container.

## Flow

On creation, the activity inflates binding, sets the content view, and hides the bottom drawer. After creation, it wires the navigation click to expand the drawer, replaces the menu, and commits `FeedFragment` with fade animations.

## Integration

Uses core activity support, generated bindings and resources, AndroidX fragment transactions, Koin fragment factory setup inherited from the base class, and Material bottom app bar and bottom sheet components.
