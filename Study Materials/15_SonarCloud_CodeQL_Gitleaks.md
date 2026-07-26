# Code Quality & Security — SonarCloud + CodeQL + Gitleaks + Dependabot

## 1. Why DevSecOps?
- **Shift Security Left:** Integrate security checks early in the Software Development Life Cycle (SDLC) rather than waiting until the end.
- **Catch Issues Early:** Identifying vulnerabilities, bugs, and code smells before code reaches production is cheaper and easier to fix.

## 2. SonarCloud
- **What it is:** A cloud-based code quality and security platform that automatically analyzes code.
- **Quality Gate:** A set of conditions (pass/fail threshold) that code must meet. If the conditions fail (e.g., code coverage is too low, or a critical vulnerability is found), the PR is blocked from merging.
- **Metrics:**
  - **Bugs:** Coding errors that will cause failure.
  - **Code Smells:** Maintainability issues that make the code confusing or difficult to maintain.
  - **Vulnerabilities:** Security issues that can be exploited.
  - **Duplications:** Repeated blocks of code.
  - **Coverage:** Percentage of code covered by automated tests.
  - **Maintainability Rating:** A scale (e.g., A to E) representing the effort to fix all code smells.
- **`sonar-project.properties`:** A configuration file defining project key, organization, sources, and exclusions.
- **Integration with GitHub Actions:** Typically uses the `SonarSource/sonarcloud-github-action`.
- **Branch analysis and PR decoration:** SonarCloud analyzes branches and pull requests, adding comments directly in GitHub (PR decoration) pointing out issues.
- **Coverage Reports:** Requires external tools to generate coverage data, such as JaCoCo for Java and lcov for frontend, which SonarCloud then ingests.

## 3. CodeQL
- **What it is:** GitHub's semantic code analysis engine and Static Application Security Testing (SAST) tool.
- **Analyzes code for OWASP Top 10 vulnerabilities:** Queries the codebase as data to find security flaws.
- **Languages supported:** Broad support, notably including Java and JavaScript.
- **Integration:** Runs automatically on PRs via GitHub Actions.
- **What it catches:** SQL injection, Cross-Site Scripting (XSS), path traversal, insecure deserialization, etc.

## 4. Gitleaks
- **What it is:** A fast, lightweight, and open-source secret detection tool.
- **Scans for:** Hardcoded API keys, passwords, tokens, and private keys accidentally committed to git repositories.
- **Usage:** Can be run as a pre-commit hook (to prevent secrets from being committed locally) and in CI mode (to scan PRs and history).
- **`.gitleaks.toml`:** Configuration file to define custom rules, allowlists, or exclusions.
- **Why this matters:** Committing AWS keys or Database passwords to public (or even private) GitHub repos can lead to immediate account compromise and massive data breaches.

## 5. Dependabot
- **What it is:** An automated dependency vulnerability scanner built into GitHub.
- **Functionality:** Scans dependency manifests (like `pom.xml` for Maven, `package.json` for npm) and creates PRs automatically to update dependencies with known vulnerabilities to safe versions.
- **`dependabot.yml`:** Configuration file to schedule checks, target ecosystems, and set reviewers.
- **Security Advisories:** Uses the GitHub Advisory Database to track known vulnerabilities (CVEs).

## 6. OWASP Top 10
A brief overview of the 10 most critical web application security risks:
- **A01: Broken Access Control** – Users acting outside of their intended permissions.
- **A02: Cryptographic Failures** – Failures related to cryptography leading to sensitive data exposure.
- **A03: Injection (SQL, XSS)** – Untrusted data sent to an interpreter as part of a command or query.
- **A04: Insecure Design** – Missing or ineffective control design.
- **A05: Security Misconfiguration** – Insecure default settings, open cloud storage, misconfigured HTTP headers.
- **A06: Vulnerable and Outdated Components** – Using libraries/frameworks with known vulnerabilities (Dependabot covers this).
- **A07: Authentication Failures** – Incorrectly configured authentication and session management.
- **A08: Software and Data Integrity Failures** – Code and infrastructure that does not protect against integrity violations (e.g., unsigned firmware, insecure CI/CD pipelines).
- **A09: Security Logging and Monitoring Failures** – Failing to log, monitor, or respond to active breaches.
- **A10: Server-Side Request Forgery (SSRF)** – A web application fetching a remote resource without validating the user-supplied URL.

## 7. How all tools work together in Portfolio CI/CD pipeline
1. **Pre-commit:** Gitleaks ensures no secrets are committed.
2. **Push/PR created:** GitHub Actions triggers.
3. **Build & Test:** Maven builds the Java app, tests run, JaCoCo generates a coverage report.
4. **Code Quality (SAST):** SonarCloud runs, analyzing code quality, ingesting the JaCoCo report, and validating against the Quality Gate.
5. **Security (SAST):** CodeQL runs deep semantic analysis for vulnerabilities.
6. **Secret Scanning:** Gitleaks CI action verifies no secrets exist in the commit history.
7. **Continuous Dependency Scanning:** Dependabot runs on a schedule checking for outdated/vulnerable libraries.
8. **Result:** If any tool fails or the Quality Gate is missed, the PR is blocked.

## 8. Top 10 Interview Q&A
1. **What is DevSecOps?** Integrating security practices within the DevOps process; shifting security "left" to catch issues early.
2. **What is SonarCloud and what is a quality gate?** A cloud-based code quality tool. A quality gate is a threshold (like >80% coverage) that code must pass to be merged.
3. **What is static analysis (SAST)?** Analyzing source code without executing it to find bugs and security vulnerabilities.
4. **What is CodeQL and what vulnerabilities does it catch?** GitHub's SAST engine that queries code like data. It catches SQL injection, XSS, insecure deserialization, etc.
5. **What is Gitleaks and why is it important?** A tool that scans for hardcoded secrets. Essential to prevent exposing API keys or passwords.
6. **What is Dependabot?** GitHub's tool that automatically creates PRs to update vulnerable dependencies.
7. **What are the OWASP Top 10?** A standard awareness document representing the top 10 most critical security risks to web applications.
8. **What is the difference between SAST and DAST?** SAST analyzes static source code (white-box). DAST analyzes the running application by attacking it (black-box).
9. **How do you measure code coverage in a Java project?** Using tools like JaCoCo, which instruments the bytecode and tracks execution during testing.
10. **What is a code smell?** Code that isn't necessarily a bug but indicates a deeper problem, making it hard to maintain or understand.
