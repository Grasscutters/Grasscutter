# Contributing 

Please note we have a code of conduct, please follow it in all your interactions with the project. If you have any further questions please create an issue or ask in the Discord server.

- Only fix/add the functionality in question OR address wide-spread whitespace/style issues, not both.
- Address a single concern in the least number of changed lines as possible.

**Do not make a pull request to merge into stable unless it is a hotfix. Use the development branch instead.**

## Pull Request Process

1. Ensure any install or build dependencies are removed before the end of the layer when doing a build.
2. Update the README.md and wiki with details of changes to the interface, this includes new environment variables, exposed ports, useful file locations and container parameters.
3. Write with detail on your pull request description what you have committed, to make it easier for the collaborators to make a changelog.

## Code Content

Your code should follow the standards set below:

- Your code can be run purely using a Java 17 JVM.
    - Dependencies that use a separate language (ex. Kotlin) that can run on a JVM are acceptable.
    - Dependencies that require the use of native code (ex. JNI) are prohibited.
    - Directly adding foreign code (other languages, ex. Kotlin) into the main codebase is prohibited.
- Certain files and folders are forbidden to modify in Pull Requests and may only be changed by Write-Access members. The current list for this can be found in `.github/workflows/protect_files.yml`.

## Intellectual Property

Please avoid adding any direct references to the following, whenever possible:
- Game names
- Character names
- Notable weapon names
