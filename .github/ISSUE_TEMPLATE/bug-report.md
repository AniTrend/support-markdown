---
title: "[Bug]: "
name: Bug Report
about: Report any issues regarding the project and help us identify bugs quicker
labels: ["bug"]
---

- type: markdown
  attributes:
    value: |
    # Issue Guidelines

    Before opening a new issue, please take a moment to review our [**community guidelines**](https://github.com/AniTrend/support-markdown/blob/develop/CONTRIBUTING.md) to make the contribution process easy and effective for everyone involved.

    **You may find an answer in already closed issues**:
    https://github.com/AniTrend/support-markdown/issues?q=is%3Aissue+is%3Aclosed

- type: textarea
  id: description
  attributes:
    label: Description of bug
    description: A clear and concise description of what the bug is. In other words what is the behaviour
  validations:
    required: true

- type: textarea
  id: expectation
  attributes:
    label: Expected behavior
    description: A clear and concise description of what you expect to happen. This is the intented behaviour
  validations:
    required: false

- type: textarea
  id: context
  attributes:
    label: Additional context
    description: What are you trying to accomplish? Providing context helps us come up with a solution that is most useful in the real world.
  validations:
    required: false

- type: textarea
  id: logs
  attributes:
    label: Relevant log output
    description: Please copy and paste any relevant log output. This will be automatically formatted into code, so no need for backticks.
    render: shell
- type: checkboxes
  id: guidelines
  attributes:
    label: Community guidelines
    description: Please take a moment to review our [community guidelines](https://github.com/AniTrend/support-markdown/blob/develop/CONTRIBUTING.md)
    options:
      - label: I have read the community guidelines
        required: true
