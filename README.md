# 🚀 Microservices Developer Portfolio — Satheesh Kumar P

[![Java](https://img.shields.io/badge/Java-21-orange.svg?style=for-the-badge&logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.4-6DB33F.svg?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18.2-61DAFB.svg?style=for-the-badge&logo=react)](https://react.dev/)
[![Apache Kafka](https://img.shields.io/badge/Apache_Kafka-Event_Driven-231F20.svg?style=for-the-badge&logo=apachekafka)](https://kafka.apache.org/)
[![Redis](https://img.shields.io/badge/Redis-Cache_%26_RateLimit-DC382D.svg?style=for-the-badge&logo=redis)](https://redis.io/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1.svg?style=for-the-badge&logo=postgresql)](https://www.postgresql.org/)
[![AWS](https://img.shields.io/badge/AWS-S3_%7C_CloudFront_%7C_ECR-232F3E.svg?style=for-the-badge&logo=amazon-aws)](https://aws.amazon.com/)
[![CI/CD](https://img.shields.io/badge/GitHub_Actions-DevSecOps-2088FF.svg?style=for-the-badge&logo=githubactions)](https://github.com/features/actions)

Welcome to the repository of **Satheesh Kumar P** — Full Stack Java Developer (2.5 Years Experience). This project demonstrates an enterprise-grade, event-driven microservices architecture built with modern software design principles, distributed caching, AI integrations, automated DevSecOps pipelines, and cloud-native deployment practices.

---

## 🏛️ System Architecture Overview

`
                        ┌────────────────────────────────────────────────┐
                        │             React 18 + Vite SPA               │
                        │      (Tailwind CSS, Zustand, React Query)      │
                        └───────────────────────┬────────────────────────┘
                                                │
                                                ▼ HTTP / REST & SSE Stream
                        ┌────────────────────────────────────────────────┐
                        │       portfolio-service (Port: 8080)           │
                        │    (Spring Boot 3.2, Security, JPA, Redis)     │
                        └───────────┬────────────────────┬───────────────┘
                                    │                    │
                ┌───────────────────┴──┐              ┌──┴───────────────────┐
                │ Apache Kafka Cluster │              │ Google Gemini Flash  │
                │ (Topic: contact-evt) │              │   (AI Chat Engine)   │
                └───────────┬──────────┘              └──────────────────────┘
                            │
                            ▼ Async Event Consumption
                        ┌────────────────────────────────────────────────┐
                        │     notification-service (Port: 8081)          │
                        │      (Spring Kafka + JavaMailSender)           │
                        └────────────────────────────────────────────────┘
`

---

## 🛠️ Microservices Breakdown

| Service Name | Technology | Port | Responsibilities |
| :--- | :--- | :--- | :--- |
| **rontend/** | React 18, Vite, Framer Motion | 5173 | Interactive Glassmorphic UI, Dynamic Experience Timeline, Project Case Studies, AI Chatbot widget, Contact Form. |
| **portfolio-service/** | Spring Boot 3.2, Java 21, JPA | 8080 | Core REST APIs, Spring Security + JWT, Redis Cache for project data, Redis Rate Limiter (20 msg/hr for AI), Kafka Event Producer, Flyway DB migrations. |
| **
otification-service/** | Spring Boot 3.2, Spring Kafka | 8081 | Asynchronous Kafka Event Consumer (ContactSubmittedEvent), automated email notifications to recipient via JavaMailSender. |

---

## ✨ Key Features & Technical Highlights

1. **🤖 Gemini 1.5 Flash AI Assistant**: Context-aware portfolio chatbot powered by Spring AI with SSE (Server-Sent Events) streaming responses.
2. **⚡ Distributed Caching & Rate Limiting**: Redis-backed Spring Cache for instant project responses and IP-based sliding window rate limiting to protect LLM endpoints.
3. **📨 Event-Driven Messaging**: Apache Kafka decouples email notifications from main web transactions, guaranteeing resilience and high throughput.
4. **📂 Private Project Architecture Drawer**: Interactive UI case study drawer showcasing architecture diagrams, database schemas, and technical highlights for private enterprise projects (e.g. Civil Platform).
5. **🔒 DevSecOps CI/CD Pipelines**: Automated GitHub Actions testing, Gitleaks secret scanning, SonarCloud static code analysis, and AWS cloud deployment.

---

## 🛠️ Tech Stack & Tools

- **Backend**: Java 21, Spring Boot 3.2.4, Spring Security, Spring Data JPA, Spring AI, Spring Kafka, Spring Cache, Flyway, MapStruct, Lombok, JJWT.
- **Frontend**: React 18, Vite, Zustand, TanStack React Query v5, Framer Motion, Axios, Lucide Icons, Vanilla CSS Design System.
- **Database & Storage**: PostgreSQL 16 (Relational DB), Redis 7 (In-Memory Cache & Rate Limiting).
- **Messaging**: Apache Kafka 7.5 + Zookeeper.
- **Testing**: JUnit 5, Mockito, Testcontainers (Real PostgreSQL testing), Vitest, Cypress E2E.
- **DevSecOps & Cloud**: GitHub Actions, SonarCloud, CodeQL, Gitleaks, Docker, Docker Compose, AWS S3, CloudFront, ECR, App Runner.

---

## 🚀 Local Quickstart Guide

### Prerequisites
- Java 21 SDK
- Node.js 20+ & npm
- Docker & Docker Compose

### 1. Clone Repository & Start Infrastructure Containers
`ash
git clone https://github.com/satheesh1501/portfolio.git
cd portfolio

# Start PostgreSQL, Redis, and Apache Kafka containers
docker-compose up -d
`

### 2. Run Portfolio Backend Service
`ash
cd portfolio-service
mvn clean spring-boot:run
`
Backend will start on http://localhost:8080 (OpenAPI Swagger UI: http://localhost:8080/swagger-ui.html).

### 3. Run Notification Microservice
`ash
cd notification-service
mvn clean spring-boot:run
`
Notification service will listen to Kafka events on http://localhost:8081.

### 4. Run React 18 Frontend
`ash
cd frontend
npm install
npm run dev
`
Open http://localhost:5173 in your browser.

---

## 📄 License & Contact

- **Author**: Satheesh Kumar P
- **Role**: Full Stack Java Developer
- **Email**: psatheeshkumar89@gmail.com
- **Location**: Aruppukottai, Tamil Nadu, India
- **GitHub**: [github.com/satheesh1501](https://github.com/satheesh1501)
- **LinkedIn**: [linkedin.com/in/satheeshkumar89](https://linkedin.com/in/satheeshkumar89)
