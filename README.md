# 🚀 Microservices Developer Portfolio Platform

[![Java 21](https://img.shields.io/badge/Java-21-orange.svg?style=for-the-badge&logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot 3](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F.svg?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![React 18](https://img.shields.io/badge/React-18.2-61DAFB.svg?style=for-the-badge&logo=react)](https://react.dev/)
[![Apache Kafka](https://img.shields.io/badge/Apache_Kafka-Event_Driven-231F20.svg?style=for-the-badge&logo=apachekafka)](https://kafka.apache.org/)
[![Redis](https://img.shields.io/badge/Redis-Cache_%26_RateLimit-DC382D.svg?style=for-the-badge&logo=redis)](https://redis.io/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Neon_Serverless-4169E1.svg?style=for-the-badge&logo=postgresql)](https://www.postgresql.org/)
[![AWS ECS Fargate](https://img.shields.io/badge/AWS-ECS_Fargate_%7C_CloudFront-232F3E.svg?style=for-the-badge&logo=amazon-aws)](https://aws.amazon.com/)
[![CI/CD](https://img.shields.io/badge/GitHub_Actions-DevOps-2088FF.svg?style=for-the-badge&logo=githubactions)](https://github.com/features/actions)

An enterprise-grade, event-driven microservices platform built by **Satheesh Kumar P** (Full Stack Software Engineer). Featuring real-time AI conversation capabilities, serverless database persistence, distributed caching, rate-limiting, and automated cloud deployments.

---

## 🛠️ Complete Tech Stack

| Category | Technology | Usage in Platform |
| :--- | :--- | :--- |
| **Frontend UI** | **React 18**, **Vite**, **Zustand** | Dynamic glassmorphic single-page web app, state management, modal drawers, and responsive UI. |
| **Backend Core** | **Java 21**, **Spring Boot 3.x** | High-performance microservices, REST APIs, dependency injection, and JPA repository abstractions. |
| **AI Assistant** | **Spring AI (Google Gemini API)** | Context-aware NLP chatbot for technical QA, skills exploration, and career experience queries. |
| **Messaging & Events** | **Apache Kafka (Aiven Cloud)** | Decoupled event streaming (`contact-notifications-topic`) for event-driven async notification processing. |
| **Caching & Rate Limiting** | **Upstash Serverless Redis** | In-memory `@Cacheable` project response caching and sliding-window IP rate limiting to prevent DDoS. |
| **Database & Migration** | **Neon PostgreSQL**, **Flyway** | Serverless relational database persistence with automated Flyway database schema migrations. |
| **Security & Mail** | **Spring Security**, **Gmail SMTP** | XSS sanitization (`@NoHtml`), CORS origin pattern filtering, MDC trace logs (`X-Trace-Id`), and JavaMailSender. |
| **Container & Cloud** | **Docker**, **AWS ECS Fargate**, **S3 + CloudFront** | Multi-stage distroless Docker builds, serverless container hosting, global CDN distribution, and HTTPS edge proxying. |
| **CI/CD Automation** | **GitHub Actions**, **AWS ECR** | Automated path-filtered selective builds, container image registry uploads, and zero-downtime cloud deployments. |

---

## 🏛️ Updated System Architecture

```
                                    ┌──────────────────────────────────────────────┐
                                    │         AWS CloudFront Global CDN            │
                                    │     (HTTPS Edge Proxy & Cache Behaviors)     │
                                    └──────────────┬────────────────┬──────────────┘
                                                   │                │
                                       ┌───────────┘                └───────────┐
                                       ▼                                        ▼
                        ┌──────────────────────────────┐        ┌──────────────────────────────┐
                        │   AWS S3 Static Web Host     │        │   AWS ECS Fargate Backend    │
                        │    (React 18 + Vite SPA)     │        │ (portfolio-service :8080)    │
                        └──────────────────────────────┘        └──────┬──────────────┬────────┘
                                                                       │              │
                                                ┌──────────────────────┘              └──────────────────────┐
                                                ▼                                                            ▼
                                ┌──────────────────────────────┐                            ┌──────────────────────────────┐
                                │     Neon PostgreSQL DB       │                            │    Upstash Redis Cache       │
                                │   (PostgreSQL 16 Engine)     │                            │ (Rate Limiter & CacheStore)  │
                                └──────────────────────────────┘                            └──────────────────────────────┘
                                                │                                                            │
                                                ▼                                                            ▼
                                ┌──────────────────────────────┐                            ┌──────────────────────────────┐
                                │    Aiven Apache Kafka        │                            │   AWS ECS Fargate Consumer   │
                                │ (contact-notifications-topic)│                            │(notification-service :8081)  │
                                └──────────────┬───────────────┘                            └──────────────┬───────────────┘
                                               │                                                           │
                                               └───────────────────────────┬───────────────────────────────┘
                                                                           ▼
                                                            ┌──────────────────────────────┐
                                                            │   Gmail SMTP Mail Server     │
                                                            │  (Direct / Async Email Alert)│
                                                            └──────────────────────────────┘
```

---

## 🧩 Microservices Breakdown

### 1. `frontend` (React 18 + Vite SPA)
- **Port**: `5173` (Local) / S3 + CloudFront (Production)
- **Responsibilities**: Interactive dark-mode portfolio interface, dynamic experience timeline, project showcase drawers, real-time Gemini AI chatbot drawer, contact submission form, and responsive glassmorphic UI.

### 2. `portfolio-service` (Spring Boot Core Microservice)
- **Port**: `8080` (AWS ECS Fargate)
- **Responsibilities**: REST API gateway, Spring Security CORS filter, Redis sliding-window IP rate limiting (3 calls/15 min for contact, 10 calls/5 min for chat), `@Cacheable` project endpoints, PostgreSQL database persistence, Spring AI Google Gemini engine integration, Kafka event producer, and direct async email fallback dispatch.

### 3. `notification-service` (Spring Kafka Email Consumer)
- **Port**: `8081` (AWS ECS Fargate)
- **Responsibilities**: Asynchronous Kafka event listener (`ContactSubmittedEvent`) consuming contact notification events from `contact-notifications-topic`, rendering Thymeleaf HTML email templates, and sending alerts via Gmail SMTP (`smtp.gmail.com:587`).

### 4. `common-library` (Shared Monorepo Java Module)
- **Responsibilities**: Shared DTO records, custom `@NoHtml` XSS validation annotations, global exception handler constants, and standardized MDC logging utilities used across all backend microservices.

---

## 👤 Author & Maintainer

- **Satheesh Kumar P** — Full Stack Software Engineer
- **Email**: psatheeshkumar1501@gmail.com
- **LinkedIn**: [linkedin.com/in/satheeshkumar89](https://linkedin.com/in/satheeshkumar89)
- **GitHub**: [github.com/satheesh1501](https://github.com/satheesh1501)
