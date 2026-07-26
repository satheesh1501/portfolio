# 🗺️ Portfolio Project Master Roadmap & Phase Plan

This document details the complete 7-phase implementation plan for building and deploying Satheesh's Full Stack Developer Portfolio.

---

## 📌 Phase Overview

- **Phase 1: Setup & Project Scaffolding** [COMPLETED]
  - Monorepo structure setup (rontend, portfolio-service, 
otification-service, .github/workflows).
  - Git repository initialization, .gitignore, docker-compose.yml (PostgreSQL, Redis, Kafka).
  - React 18 + Vite scaffolding and Spring Boot 3.2 pom.xml & pplication.yml setup.

- **Phase 2: Database Schema & Flyway SQL** [NEXT]
  - Flyway migrations (V1__create_contact_messages.sql, V2__create_resume_events.sql, V3__create_projects.sql).
  - JPA entities (ContactMessage, Project, ResumeDownloadEvent) with Lombok annotations.

- **Phase 3: Backend REST APIs & Spring AI**
  - Controllers (ContactController, ProjectController, ChatbotController, AnalyticsController).
  - Spring Security + JWT authentication for admin endpoints.
  - Spring AI + Google Gemini 1.5 Flash streaming response (SSE).
  - Redis cache (@Cacheable) and IP sliding window rate limiting (20 msg/hr).
  - Kafka producer publishing ContactSubmittedEvent.

- **Phase 4: Notification Microservice Implementation**
  - Kafka @KafkaListener consumer in 
otification-service.
  - JavaMailSender email notification triggers on contact form submission.

- **Phase 5: React 18 Frontend Implementation**
  - Glassmorphic CSS design system (index.css).
  - Components (Navbar, Hero, About, Skills, Experience, Projects, Contact, Chatbot).
  - Interactive Civil Platform Case Study drawer.
  - Zustand stores (chatStore, 	hemeStore, 
avStore) & React Query integration.

- **Phase 6: DevSecOps CI/CD Verification & Testing**
  - JUnit 5 + Mockito unit tests & Testcontainers PostgreSQL integration tests.
  - Vitest + Cypress E2E headless tests.
  - GitHub Actions pipelines with SonarCloud & Gitleaks integration.

- **Phase 7: AWS Cloud Deployment**
  - AWS S3 + CloudFront CDN for React Frontend.
  - AWS App Runner + ECR for Spring Boot Microservice containers.
