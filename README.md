# 🚀 Microservices Developer Portfolio — Satheesh Kumar P

[![Java 21](https://img.shields.io/badge/Java-21-orange.svg?style=for-the-badge&logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot 3.2](https://img.shields.io/badge/Spring_Boot-3.2.4-6DB33F.svg?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![React 18](https://img.shields.io/badge/React-18.2-61DAFB.svg?style=for-the-badge&logo=react)](https://react.dev/)
[![Apache Kafka](https://img.shields.io/badge/Apache_Kafka-Event_Driven-231F20.svg?style=for-the-badge&logo=apachekafka)](https://kafka.apache.org/)
[![Redis](https://img.shields.io/badge/Redis-Cache_%26_RateLimit-DC382D.svg?style=for-the-badge&logo=redis)](https://redis.io/)
[![PostgreSQL 16](https://img.shields.io/badge/PostgreSQL-16-4169E1.svg?style=for-the-badge&logo=postgresql)](https://www.postgresql.org/)
[![AWS Cloud](https://img.shields.io/badge/AWS-S3_%7C_CloudFront_%7C_ECR-232F3E.svg?style=for-the-badge&logo=amazon-aws)](https://aws.amazon.com/)
[![CI/CD](https://img.shields.io/badge/GitHub_Actions-DevSecOps-2088FF.svg?style=for-the-badge&logo=githubactions)](https://github.com/features/actions)

Welcome to the official repository of **Satheesh Kumar P** — Full Stack Software Engineer (2.5+ Years Experience at Profit.co & HCL Technologies). 

This repository showcases an **enterprise-grade, event-driven microservices platform** built with **Java 21**, **Spring Boot 3**, **Apache Kafka**, **Redis**, **PostgreSQL**, **Spring AI**, **React 18**, and **Docker**.

---

## 🏛️ System Architecture Overview

```
                                 ┌──────────────────────────────────────────────┐
                                 │             React 18 + Vite SPA              │
                                 │        (Vanilla CSS, Zustand Store)          │
                                 └──────────────────────┬───────────────────────┘
                                                        │
                                                        ▼ REST / HTTP & SSE Stream
                                 ┌──────────────────────────────────────────────┐
                                 │       portfolio-service (Port: 8080)         │
                                 │  (Spring Boot 3.2, Java 21, JPA, Security)   │
                                 └──────────┬───────────────────┬───────────────┘
                                            │                   │
                        ┌───────────────────┴──┐             ┌──┴────────────────────┐
                        │ Apache Kafka Cluster │             │  Spring AI Gemini Engine │
                        │ (Topic: contact-evt) │             │ (Contextual NLP Assistant)│
                        └───────────┬──────────┘             └───────────────────────┘
                                    │
                                    ▼ Async Event Consumption
                                ┌──────────────────────────────────────────────┐
                                │     notification-service (Port: 8081)        │
                                │      (Spring Kafka + JavaMailSender)         │
                                └──────────────────────────────────────────────┘
```

---

## 🛠️ Microservices Breakdown

| Service / Layer | Technology | Port | Architectural Responsibilities |
| :--- | :--- | :--- | :--- |
| **`frontend`** | React 18, Vite, Zustand | `5173` | Interactive dark-mode UI, glassmorphic design, dynamic experience timeline, private project case studies drawer, AI Chatbot drawer, and contact form. |
| **`portfolio-service`** | Java 21, Spring Boot 3.2, JPA | `8080` | Core REST API gateway, Spring Security, Redis caching for project data, IP-based Redis sliding-window rate limiting, Kafka event publishing, and Spring AI prompt resolution. |
| **`notification-service`** | Java 21, Spring Boot 3.2, Kafka | `8081` | Asynchronous Kafka event listener (`ContactSubmittedEvent`) consuming contact notifications and sending HTML emails via JavaMailSender & Thymeleaf templates. |
| **`common-library`** | Java 21 Shared Module | — | Shared DTO records, custom `@NoHtml` XSS validation annotations, global error handling constants, and standardized loggers. |

---

## ✨ Key Technical Highlights

1. **🤖 Tokenized AI Portfolio Assistant**: Context-aware assistant built with Spring AI and fallback intent matching to answer queries about experience, skills, and LeetCode milestones.
2. **⚡ Distributed Caching & Rate Limiting**: Redis-backed `@Cacheable` project response caching and sliding-window IP rate limiting (3 requests/15 mins for contact, 10 requests/5 mins for chat).
3. **📨 Decoupled Event-Driven Messaging**: Apache Kafka decouples email notification sending from HTTP response threads, ensuring zero user-facing latency.
4. **🔒 4-Layer Input Security Validation**: Enforces `@NotBlank`, `@Size`, whitelist `@Pattern`, and custom `@NoHtml` annotations to block XSS and payload injections.
5. **🧪 Dual-Suite Automated Testing**: 100% automated test coverage powered by **Vitest** (Unit & Zustand state tests) and **Cypress** (Headless browser E2E tests).
6. **🚀 DevSecOps CI/CD Pipelines**: Automated GitHub Actions workflows for continuous build, test, and Docker image packaging on every push.

---

## 🛠️ Tech Stack & Tools

- **Core Backend**: Java 21, Spring Boot 3.2.4, Spring Security, Spring Data JPA, Spring AI, Spring Kafka, Spring Cache, Flyway, MapStruct, Lombok.
- **Frontend**: React 18, Vite, Zustand, Vanilla CSS Design System, Axios, Lucide Icons.
- **Database & Caching**: PostgreSQL 16 (Relational DB), Redis 7 (In-Memory Cache & Rate Limiting).
- **Messaging**: Apache Kafka 7.5 + Zookeeper.
- **Testing**: Vitest (Unit & State Tests), Cypress (Browser E2E Tests), JUnit 5, Mockito.
- **DevOps & Cloud**: Docker, Docker Compose, GitHub Actions CI/CD, AWS S3, AWS CloudFront, AWS App Runner.

---

## 🚀 Local Quickstart Guide

### Prerequisites
- Java 21 JDK installed
- Node.js 20+ & npm installed
- Docker & Docker Compose running

### 1. Clone Repository & Start Infrastructure
```bash
git clone https://github.com/satheesh1501/portfolio.git
cd portfolio

# Start PostgreSQL, Redis, and Apache Kafka containers
docker-compose up -d
```

### 2. Start Portfolio Backend Service
```bash
cd portfolio-service
mvn clean spring-boot:run
```
*(Runs on `http://localhost:8080` — Swagger UI: `http://localhost:8080/swagger-ui.html`)*

### 3. Start Notification Microservice
```bash
cd notification-service
mvn clean spring-boot:run
```
*(Runs on `http://localhost:8081` listening to Kafka events)*

### 4. Start React 18 Frontend
```bash
cd frontend
npm install
npm run dev
```
*(Open `http://localhost:5173` in your browser)*

---

## ⚙️ How to Verify GitHub Actions CI/CD Pipelines

To verify that the automated **Frontend** and **Backend** GitHub Actions CI/CD workflows run cleanly:

1. **Commit and Push Changes to GitHub**:
   ```bash
   git add .
   git commit -m "docs: update enterprise README with clean architecture diagram"
   git push origin develop
   ```

2. **Open GitHub Actions Tab**:
   - Navigate to your repository URL: [github.com/satheesh1501/portfolio](https://github.com/satheesh1501/portfolio)
   - Click on the **Actions** tab at the top.

3. **Observe Automated Workflow Executions**:
   - You will see two active pipelines running:
     - 🟢 **Frontend CI/CD Pipeline**: Installs Node 20 dependencies, executes **Vitest** unit/state tests, builds Vite bundle, and packages `dist/` artifact.
     - 🟢 **Backend Microservices CI/CD Pipeline**: Installs Java 21, builds `common-library`, compiles `portfolio-service` & `notification-service`, and verifies Docker container image builds.

---

## 📄 Contact & Professional Links

- **Author**: Satheesh Kumar P
- **Role**: Full Stack Software Engineer (2.5+ Years Exp)
- **Email**: psatheeshkumar89@gmail.com
- **Degree**: B.Tech IT | Mepco Schlenk Engineering College (CGPA: 7.56 / 10)
- **LeetCode**: [leetcode.com/u/satheeshkumar1501](https://leetcode.com/u/satheeshkumar1501/) (138+ DSA Solved)
- **GitHub**: [github.com/satheesh1501](https://github.com/satheesh1501)
- **LinkedIn**: [linkedin.com/in/satheeshkumar89](https://linkedin.com/in/satheeshkumar89)
