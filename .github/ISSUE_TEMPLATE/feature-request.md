---
title: "[Feature]: "
name: Feature Request
about: Got a suggestion? Then this is what you should use
labels: [":star2: enhancement"]
---

- type: markdown
  attributes:
    value: |
    # Issue Guidelines

    Before opening a new issue, please take a moment to review our [**community guidelines**](https://github.com/AniTrend/support-markdown/blob/develop/CONTRIBUTING.md) to make the contribution process easy and effective for everyone involved.

    **You may find an answer in already closed issues**:
    https://github.com/AniTrend/support-markdown/issues?q=is%3Aissue+is%3Aclosed

- type: textarea
  id: information
  attributes:
    label: Description of bug
    description: Is your feature request related to a problem? Please describe.
  validations:
    required: true

- type: textarea
  id: context
  attributes:
    label: Additional context
    description: What are you trying to accomplish? Providing context helps us come up with a solution that is most useful in the real world, also include images or links if you have any in this section
  validations:
    required: false

- type: checkboxes
  id: guidelines
  attributes:
    label: Community guidelines
    description: Please take a moment to review our [community guidelines](https://github.com/AniTrend/support-markdown/blob/develop/CONTRIBUTING.md)
    options:
      - label: I have read the community guidelines
        required: true
