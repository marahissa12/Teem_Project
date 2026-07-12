# Quantom Team Charter

## 1. Team Roles
* **Workflow Lead (Member 1):** Repository governance, branching model maintenance, and commit message enforcement.
* **Clean Standards Lead (Member 2):** Definition of coding styles, variable naming conventions, and linting standards.
* **Communication & SLA Lead (Member 3):** Management of internal protocols, meeting cadences, and Peer Review response times.
* **Architecture & Ignores Lead (Member 4):** Project directory structure, dependency management, and security/environment configuration.

## 2. Branching Model: Feature-Branch Workflow
We will use a centralized Feature-Branch model to ensure stable releases:
* **`main` Branch:** Protected. Holds production-ready code. No direct commits allowed.
* **`feature/` Branches:** All development must occur in branches named `feature/[task-name]`.
* **PR Requirement:** Every change must be submitted via a Pull Request (PR) and requires **two (2) peer approvals** before merging into `main`.

## 3. Commit Message Standards
To maintain a clean and searchable history, all commits must follow the **Conventional Commits** specification: `<type>(<scope>): <short description>`

### Types:
* **feat:** New feature addition.
* **fix:** Bug correction.
* **docs:** Changes to documentation.
* **style:** Formatting, missing semi-colons, whitespace (no code change).
* **refactor:** Restructuring code without changing behavior.
* **test:** Adding or updating tests.
* **chore:** Build process or auxiliary tool changes.

*Example: `feat(workflow): establish team roles and branching model`*


To ensure the highest quality of our Smart Systems project and to maintain consistency across the team's codebase, all members must strictly adhere to the following clean code and style standards.

## 1. Naming Conventions (Kotlin Standards)
All code must follow the standard Kotlin naming conventions:
* **Variables (val/var):** Use `camelCase`. Names must be descriptive (e.g., `userRegistrationDate` instead of `d`).
* **Functions:** Use `camelCase` starting with a verb that describes the action (e.g., `calculateTotalScore()`, `fetchSensorData()`).
* **Classes & Objects:** Use `PascalCase` (e.g., `SmartSafeLock`, `DataHandler`).
* **Constants:** Use `UPPER_SNAKE_CASE` (e.g., `MAX_RETRY_ATTEMPTS = 3`).
* **Booleans:** Must be prefixed with `is`, `has`, or `should` (e.g., `isLoggedIn`, `hasPermissions`, `isValid`).

## 2. Function & Parameter Rules
* **Single Responsibility Principle (SRP):** Each function must perform only one action. If a function does more, it must be split into smaller sub-functions.
* **Limit Parameters:** Functions should ideally have 3 or fewer parameters. If more are required, encapsulate them into a `data class` or an object to improve readability.
* **Meaningful Names:** Parameters must have clear, intention-revealing names to avoid the need for external documentation.

## 3. Code Style & Layout
* **Indentation:** Use a consistent **4-space** indentation.
* **Line Length:** Maintain a maximum of **120 characters** per line to ensure code remains readable without horizontal scrolling.
* **Whitespace:** Use empty lines to group logically related blocks of code; avoid overcrowded functions.
* **Comments:** Explain the **"Why"**, not the **"What"**. If the code is clean, it should explain itself. Use comments only for complex logic or specific algorithm requirements.

## 4. General Best Practices
* **Avoid Magic Numbers:** Replace raw numbers (e.g., `50`, `1000`) with named constants.
* **Error Handling:** Never swallow exceptions. Log errors appropriately or provide meaningful user feedback.
* **Consistency:** Always follow the existing style of the file you are modifying.
* **Git Workflow:** * Always work on a separate feature branch.
  * Perform `Pull` before starting any work.
  * Use clear and descriptive commit messages (e.g., "Add: implement sensor logic" rather than "fix").

---
*Maintained by: Clean Standards Member*