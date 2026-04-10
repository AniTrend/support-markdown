# GitHub Copilot Chat Customization

This directory contains GitHub Copilot customization files for the `support-markdown` library.

## Structure

- `instructions/` - Repository-specific context and guidance automatically applied by Copilot
- `skills/` - Reusable procedural skills Copilot can invoke for recurring tasks
- `workflows/` - GitHub Actions workflows including `copilot-setup-steps.yml`

## Usage

Files in `instructions/` are automatically picked up by GitHub Copilot to provide context about this repository. Files in `skills/` provide step-by-step procedures for common tasks such as running Gradle builds with low memory pressure or understanding module structure.
