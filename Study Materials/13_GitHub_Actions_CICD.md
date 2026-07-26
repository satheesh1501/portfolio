# GitHub Actions — CI/CD Complete Interview Study Guide

## 1. What is CI/CD?
- **CI (Continuous Integration)**: Automate build and test on every code push.
- **CD (Continuous Delivery)**: Automate deployment to a staging environment.
- **CD (Continuous Deployment)**: Automate deployment straight to production.

## 2. What is GitHub Actions?
- Event-driven automation platform built directly into GitHub.
- Free for public repos, with a generous free tier for private repos.

## 3. Core Concepts
- **Workflow**: Defined in `.github/workflows/*.yml`.
- **Event**: Triggers the workflow (e.g., `push`, `pull_request`, `schedule`, `workflow_dispatch`).
- **Job**: A group of steps that run on a runner.
- **Step**: Individual command or action within a job.
- **Runner**: The server executing the workflow (GitHub-hosted: `ubuntu-latest`, `windows-latest`, `macos-latest` or self-hosted).
- **Action**: A reusable step from the GitHub Marketplace.
- **Secrets**: Encrypted variables stored in GitHub settings.
- **Environment**: Logical targets (e.g., staging, production) with protection rules.
- **Matrix strategy**: Run the same job concurrently on multiple OS/language versions.

## 4. Workflow YAML structure with full example
- **Frontend CI**: Install dependencies, run lint, execute tests, build production bundle, deploy to S3.
- **Backend CI**: Run `mvn test`, perform SonarCloud analysis, build Docker image, push to ECR, trigger App Runner deploy.

## 5. Key GitHub Actions used in Portfolio
- `actions/checkout@v4`: Checks out repository code.
- `actions/setup-java@v4`: Configures Java environment.
- `actions/setup-node@v4`: Configures Node.js environment.
- `aws-actions/configure-aws-credentials@v4`: Authenticates with AWS.
- `aws-actions/amazon-ecr-login@v2`: Logs into Amazon ECR.
- `SonarSource/sonarcloud-github-action`: Runs SonarCloud static analysis.
- `github/codeql-action`: Performs CodeQL security scanning.

## 6. Branch Protection Rules
- Require PR before merging.
- Require status checks to pass (CI must be green).
- Require at least 1 reviewer.
- Use `CODEOWNERS` file to enforce specific reviewers.

## 7. Dependabot
- Auto-creates PRs to update outdated npm and Maven dependencies.
- Configurable via weekly scan schedules.
- Managed via `dependabot.yml` configuration.

## 8. Top 12 Interview Q&A
1. **What is CI/CD and why is it important?** Continuous Integration/Deployment automates testing and releases, reducing manual errors.
2. **What is GitHub Actions?** GitHub's native CI/CD and automation tool.
3. **What is the difference between CI and CD?** CI is building/testing; CD is deploying.
4. **What is a GitHub Actions workflow?** A configurable automated process made up of jobs.
5. **What are secrets in GitHub Actions and how do you use them?** Encrypted variables accessed via `${{ secrets.MY_SECRET }}`.
6. **What is a matrix strategy in GitHub Actions?** Running tests across combinations of OSs and runtime versions.
7. **How do you deploy to AWS from GitHub Actions?** Configure AWS credentials using OIDC/access keys, then run AWS CLI commands.
8. **What is branch protection?** Rules preventing direct pushes or unreviewed code from entering main branches.
9. **What is Dependabot?** An automated tool that creates PRs to update dependencies.
10. **How do you trigger a workflow only on changes to specific files?** Using `paths:` or `paths-ignore:` under `on: push/pull_request`.
11. **What is a self-hosted runner?** A machine you manage yourself to run GitHub Actions jobs.
12. **How do you cache dependencies in GitHub Actions?** Using `actions/cache` to speed up workflow execution times.
