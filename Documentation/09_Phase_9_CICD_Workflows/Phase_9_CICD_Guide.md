# Phase 9: GitHub Actions CI/CD Pipeline Documentation Guide

## 1. Overview & Architecture

The **Portfolio Event-Driven Platform** utilizes GitHub Actions to enforce automated Continuous Integration (CI) and Continuous Deployment (CD). The pipelines build, test, scan, and package both the React 18 frontend and the Java 21 Spring Boot microservices automatically on every push or pull request to `develop` or `main`.

---

## 2. Workflow Specifications

### A. Frontend CI/CD Workflow (`.github/workflows/frontend-ci-cd.yml`)
- **Triggers**: Pushes or Pull Requests affecting `frontend/**` on `main` or `develop` branches.
- **Node Environment**: Node.js v20 (LTS) with `npm` dependency caching.
- **Pipeline Steps**:
  1. **Checkout Code**: Fetches repository contents using `actions/checkout@v4`.
  2. **Install Dependencies**: Executes `npm ci` for deterministic package installation.
  3. **Unit & State Tests**: Runs Vitest suite (`npx vitest run`) verifying state transitions and validation rules.
  4. **Production Build**: Compiles Vite bundle into `frontend/dist/`.
  5. **Artifact Upload**: Packages `dist/` directory as `frontend-dist` artifact retained for 7 days.

---

### B. Backend Microservices CI/CD Workflow (`.github/workflows/backend-ci-cd.yml`)
- **Triggers**: Pushes or Pull Requests affecting `common-library/**`, `portfolio-service/**`, or `notification-service/**`.
- **JDK Environment**: Java 21 (Eclipse Temurin) with `maven` dependency caching.
- **Pipeline Steps**:
  1. **Checkout Code**: Deep fetch (`fetch-depth: 0`).
  2. **Common Library Build**: Compiles and installs `common-library-1.0.0.jar` into local runner `.m2` repository (`mvn clean install -DskipTests`).
  3. **Portfolio Service Compile**: Verifies Java 21 source compilation (`mvn clean compile -DskipTests`).
  4. **Notification Service Compile**: Verifies Java 21 source compilation (`mvn clean compile -DskipTests`).
  5. **Docker Multi-Stage Verification**: Validates production Docker container image builds for both `portfolio-service` and `notification-service`.

---

## 3. GitHub Secrets Configuration (For AWS Deployment)

When connecting GitHub Actions to AWS Cloud in Phase 10, configure the following secrets under **Repository Settings → Secrets and variables → Actions**:

| Secret Name | Description | Used By |
| :--- | :--- | :--- |
| `AWS_ACCESS_KEY_ID` | AWS IAM User Programmatic Access Key | ECR & S3 Upload |
| `AWS_SECRET_ACCESS_KEY` | AWS IAM User Secret Key | ECR & S3 Upload |
| `AWS_REGION` | AWS Target Deployment Region (e.g. `ap-south-1`) | AWS CLI & App Runner |
| `S3_BUCKET_NAME` | AWS S3 Bucket Name for Static Web Hosting | CloudFront Deployment |
| `CLOUDFRONT_DISTRIBUTION_ID` | AWS CloudFront CDN Distribution ID for Cache Invalidation | Frontend Deployment |
