# 🚀 Phase 1: Setup, Monorepo Scaffolding & Infrastructure Guide

This document records the exact steps completed during Phase 1 of the portfolio project.

---

## 🛠️ Components Initialized

1. **Root Configuration**:
   - .gitignore (ignoring Java target, Node modules, env files, temporary files).
   - docker-compose.yml (PostgreSQL 16, Redis 7, Apache Kafka 7.5 + Zookeeper).

2. **Frontend Service (/frontend)**:
   - React 18 + Vite SPA setup.
   - package.json with React Query, Zustand, Framer Motion, Axios.
   - ite.config.js proxying /api requests to http://localhost:8080.

3. **Core Portfolio Service (/portfolio-service)**:
   - Spring Boot 3.2.4 + Java 21 SDK.
   - Dependencies: Security, JPA, Redis, Kafka, Flyway, MapStruct, Lombok, JJWT, OpenAPI.
   - Package structure: config, controller, dto, entity, service, 
epository, mapper, kafka, db/migration.

4. **Notification Service (/notification-service)**:
   - Spring Boot 3.2.4 + Java 21 SDK.
   - Dependencies: Spring Kafka, Spring Mail, Lombok.
   - Application configuration: Kafka 
otification-group & SMTP Mail settings.

5. **DevSecOps GitHub Actions Workflows (/.github/workflows)**:
   - rontend-ci-cd.yml (React Build, Cypress E2E, S3 Sync, CloudFront Invalidation).
   - ackend-ci-cd.yml (Java Build, Gitleaks scan, SonarCloud analysis, Docker ECR push).
