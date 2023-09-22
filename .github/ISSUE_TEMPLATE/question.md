---
title: "[Question]: "
name: Question
about: Ask any general question regarding the project, also if you use this if you don't know what category to use
labels: ["question"]
---

- type: markdown
  attributes:
    value: |
    # Issue Guidelines

    Before opening a new issue, please take a moment to review our [**community guidelines**](https://github.com/AniTrend/support-markdown/blob/develop/CONTRIBUTING.md) to make the contribution process easy and effective for everyone involved.

    **You may find an answer in already closed issues**:
    https://github.com/AniTrend/support-markdown/issues?q=is%3Aissue+is%3Aclosed

- type: textarea
  id: question
  attributes:
    label: Clearly and explicitly explain the details about your question
  validations:
    required: true

- type: textarea
  id: context
  attributes:
    label: Additional context
    description: Any additional information regarding your question, you may also add screenshots 
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
