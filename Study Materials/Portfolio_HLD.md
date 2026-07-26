# Satheesh Kumar P — Developer Portfolio
## High-Level Design (HLD)

### 1. Document Info Table
| Attribute | Details |
| :--- | :--- |
| **Author** | Satheesh Kumar P |
| **Version** | 1.0 |
| **Date** | 2026-07-26 |
| **Status** | Approved |

### 2. Executive Summary
This document outlines the High-Level Design (HLD) for Satheesh Kumar P's personal developer portfolio application. The system serves as a professional online presence, showcasing projects, skills, and experience while offering an interactive AI chatbot and a contact form. It is designed to be highly available, performant, and secure, utilizing modern cloud-native architectural patterns.

The key architectural decisions include adopting a microservices architecture with an event-driven approach for asynchronous tasks (like email notifications), leveraging serverless database and caching solutions to minimize operational overhead, and utilizing Spring AI for advanced chatbot capabilities. A robust CI/CD pipeline ensures automated testing, security scanning, and seamless deployments.

### 3. System Goals and Non-Functional Requirements
| NFR Category | Requirement | Target | How Achieved |
| :--- | :--- | :--- | :--- |
| **Availability** | System uptime | 99.9% | AWS App Runner (managed), CloudFront CDN |
| **Performance** | API latency & Page Load | <500ms API, <1.5s FCP | Upstash Redis cache, Edge caching via CDN |
| **Scalability** | Handle traffic spikes | Auto-scale | App Runner auto-scaling, CloudFront |
| **Security** | Data protection | Secure communication | HTTPS everywhere, JWT, OWASP practices |
| **Maintainability**| Code quality & reliability | 80% coverage, clean code | SonarCloud, JUnit, Testcontainers |
| **Observability**| Monitoring & tracing | Full visibility | Spring Boot Actuator, Micrometer |
| **Portability** | Deployment flexibility| Cloud-agnostic deployment | Docker containerization |

### 4. Architecture Decision: Microservices
A microservices architecture was chosen over a monolithic design for this portfolio to provide a scalable and decoupled system, particularly separating the core API logic from asynchronous background tasks.

**Services:**
- **portfolio-service (core):** Handles synchronous HTTP traffic (Projects, Resume, AI Chat).
- **notification-service (Kafka consumer):** Listens for events (like contact form submissions) and asynchronously sends email notifications.

**Pros:**
- **Independent Scaling:** The notification service can scale independently of the core web service.
- **Fault Tolerance:** If the email provider is down, the core portfolio remains unaffected, and Kafka retains the messages for later processing.
- **Technology Diversity:** Allows adopting different technologies per service if needed in the future.

**Cons:**
- **Complexity:** Higher deployment and operational complexity compared to a monolith.
- **Data Consistency:** Requires eventual consistency handling.

### 5. System Architecture Diagram
```text
[Browser]
   │
   ├──> (Static Assets) ──> [CloudFront CDN] ──> [S3 Bucket (React SPA)]
   │
   └──> (API Requests)  ──> [CloudFront CDN] ──> [AWS App Runner (portfolio-service)]
                                                        │
                                                        ├──> [Neon PostgreSQL] (Primary DB)
                                                        ├──> [Redis / Upstash] (Caching & Rate Limiting)
                                                        └──> [Kafka / Confluent Cloud] (Event Bus)
                                                                    │
                                                                    └──> [notification-service] ──> [Email Provider (SMTP)]

GitHub Actions CI/CD:
[GitHub Push] ──> [SonarCloud / CodeQL] ──> [Tests] ──> [Docker Build] ──> [AWS ECR] ──> [App Runner Deploy]
```

### 6. Technology Stack Table with Justifications
| Component | Technology | Version | Justification |
| :--- | :--- | :--- | :--- |
| **Frontend Framework** | React + Vite | 18.x | High performance SPA, fast builds with Vite. |
| **Build Tool** | Vite | Latest | Significantly faster HMR and build times than Webpack. |
| **Animations** | Framer Motion | 11.x | Declarative and smooth animations for a premium feel. |
| **State Management** | Zustand | 4.x | Lightweight, boilerplate-free state management. |
| **Data Fetching** | React Query | 5.x | Excellent caching, background updates, and state handling. |
| **HTTP Client** | Axios | 1.x | Promise-based, interceptors for auth and error handling. |
| **Styling** | Tailwind CSS | 3.x | Utility-first CSS for rapid UI development. |
| **Backend Framework** | Spring Boot | 3.x | Robust, enterprise-grade Java framework. |
| **Language** | Java | 21 | Virtual threads, record classes, pattern matching. |
| **Security** | Spring Security | 6.x | Comprehensive auth/authz framework. |
| **Cache** | Redis | 7.x | Fast, in-memory data store for API caching & rate limits. |
| **AI Integration** | Spring AI + Gemini 1.5 | Latest | Seamless integration with LLMs via Spring abstraction. |
| **Messaging** | Apache Kafka | 3.x | Durable, scalable event streaming. |
| **DB Migration** | Flyway | Latest | Version-controlled database schema migrations. |
| **API Docs** | Springdoc OpenAPI | 2.x | Auto-generated Swagger UI for API documentation. |
| **Logging** | SLF4J + Logback | Latest | Structured JSON logging for easy parsing. |
| **Testing (Unit)** | JUnit 5 + Mockito | 5.x | Industry standard for Java unit testing. |
| **Testing (Integration)** | Testcontainers | Latest | Reliable integration testing with real Docker DBs/Brokers. |
| **Testing (E2E)** | Cypress | Latest | Reliable browser-based end-to-end testing. |
| **Containerization** | Docker | Latest | Consistent environments across dev, CI, and prod. |
| **CI/CD** | GitHub Actions | Latest | Integrated workflows right next to the code. |
| **Code Quality** | SonarCloud | Latest | Static code analysis and quality gates. |
| **Security Scan** | CodeQL | Latest | GitHub's native SAST tool. |
| **Secret Scan** | Gitleaks | Latest | Prevents accidental secret commits. |
| **Dependency Updates** | Dependabot | Latest | Automated dependency vulnerability patching. |
| **Frontend Hosting** | AWS S3 + CloudFront | Latest | Cheap, scalable, globally distributed edge delivery. |
| **Backend Hosting** | AWS App Runner | Latest | Fully managed container application service. |
| **DB Hosting** | Neon PostgreSQL | 16 | Serverless Postgres, scales to zero, built-in pooling. |
| **Cache Hosting** | Upstash Redis | Latest | Serverless Redis, pay-per-request model. |

### 7. Component Diagram
- **React Frontend (Vite SPA):** The client-facing application. Responsible for UI rendering, state management, and API communication. Uses React Query for data fetching and Framer Motion for aesthetic transitions.
- **portfolio-service (Spring Boot 3.x):** The core backend monolith exposing REST APIs. Layered architecture:
  - *Controller:* HTTP endpoint definitions, DTO validation.
  - *Service:* Business logic orchestration.
  - *Repository:* Spring Data JPA interfaces.
  - *Kafka Producer:* Sends events to topics.
  - *Spring AI:* Interacts with Gemini API for the chatbot.
- **notification-service (Spring Boot 3.x):** A lightweight background worker. Contains a Kafka Consumer listening to the `portfolio.contact.submitted` topic and an Email sender to dispatch emails.
- **AWS S3 + CloudFront:** S3 holds the compiled React static files. CloudFront caches these files at global edge locations, providing low latency and SSL termination.
- **AWS App Runner + ECR:** ECR stores the Docker images. App Runner pulls these images and runs them in a fully managed, auto-scaling environment.
- **Neon PostgreSQL:** Stores persistent data (Contact Messages, Projects, Resume Download tracking).
- **Redis (Upstash):** Caches the `projects` list and maintains Rate Limiting counters for the AI Chat endpoint.
- **Kafka (Confluent Cloud):** Event bus decoupling the core API from the notification system. Contains topics like `portfolio.contact.submitted`.
- **GitHub Actions:** CI/CD orchestrator executing tests, scans, builds, and deployments.

### 8. Data Flow Diagrams
**Use Case 1: Visitor views the portfolio**
1. Visitor navigates to the domain.
2. CloudFront serves the `index.html` and static assets from its edge cache (or fetches from S3 if miss).
3. React app initializes and makes a `GET /api/projects` request.
4. CloudFront routes API request to App Runner (portfolio-service).
5. `portfolio-service` checks Redis for cached projects.
6. If hit, returns JSON; if miss, queries Neon PostgreSQL, updates Redis, and returns JSON.
7. React renders the projects UI.

**Use Case 2: Contact form submission**
1. User fills the contact form and clicks submit.
2. React performs client-side validation.
3. React makes `POST /api/contact` request.
4. `portfolio-service` performs server-side Bean Validation.
5. MapStruct maps the incoming DTO to a JPA Entity.
6. Entity is saved to Neon PostgreSQL.
7. `ContactSubmittedEvent` is published to Confluent Cloud Kafka.
8. API returns HTTP 200 OK to the browser.
9. `notification-service` consumes the event from Kafka.
10. `notification-service` connects to the SMTP server and sends an email notification.

**Use Case 3: AI Chatbot interaction**
1. User types a message in the chat UI.
2. `portfolio-service` intercepts request and checks Upstash Redis for rate limits (IP-based).
3. If allowed, Spring AI calls the Gemini 1.5 Flash API with the user message and system prompt.
4. Gemini API responds via SSE (Server-Sent Events) stream.
5. `portfolio-service` streams tokens back to the React frontend.
6. React UI updates dynamically, displaying the response token-by-token.

**Use Case 4: Resume download**
1. User clicks the "Download Resume" button.
2. Browser makes `GET /api/resume/download` request.
3. `portfolio-service` logs the download by saving a `ResumeDownloadEvent` to PostgreSQL.
4. The service reads the PDF file (from S3 or local resources) and streams it back as an HTTP attachment (`Content-Disposition: attachment`).

**Use Case 5: CI/CD deployment**
1. Developer pushes code to GitHub `main` branch.
2. GitHub Actions pipeline triggers.
3. Pipeline runs SonarCloud analysis, CodeQL, and Gitleaks scans.
4. Unit and Integration tests run (using Testcontainers).
5. Docker image is built using a multi-stage Dockerfile.
6. Image is pushed to AWS ECR.
7. AWS App Runner is triggered to deploy the new image / S3 syncs frontend files.
8. CloudFront cache is invalidated to serve new assets.

### 9. API Design Overview
| Method | Endpoint | Description | Auth Required | Caching |
| :--- | :--- | :--- | :--- | :--- |
| GET | `/actuator/health` | Health check | No | No |
| POST | `/api/contact` | Submit contact form | No | No |
| GET | `/api/resume/download` | Download PDF | No | No |
| POST | `/api/chat` | AI Chat (SSE stream) | No | Rate Limited (Redis) |
| GET | `/api/projects` | Get projects list | No | Yes (Redis `@Cacheable`) |
| GET | `/api/analytics/downloads`| Admin stats | Yes (JWT) | No |

### 10. Database Design Overview
**1. contact_messages**
- `id` (UUID, PK)
- `name` (VARCHAR)
- `email` (VARCHAR)
- `subject` (VARCHAR)
- `message` (TEXT)
- `created_at` (TIMESTAMP)
- `ip_address` (VARCHAR)

**2. resume_download_events**
- `id` (BIGSERIAL, PK)
- `downloaded_at` (TIMESTAMP)
- `ip_address` (VARCHAR)
- `country` (VARCHAR)

**3. projects**
- `id` (UUID, PK)
- `title` (VARCHAR)
- `description` (TEXT)
- `status` (VARCHAR)
- `tech_stack` (JSONB or VARCHAR)
- `github_url` (VARCHAR)
- `demo_url` (VARCHAR)
- `display_order` (INT)
- `is_featured` (BOOLEAN)

### 11. Caching Strategy
- **What is cached:** The projects list and static skills data.
- **Cache pattern:** Cache-Aside pattern utilizing Spring's `@Cacheable` abstraction.
- **Cache store:** Upstash Redis.
- **TTL:** 1 hour for projects data to ensure eventual freshness without manual invalidation.
- **Invalidation:** `@CacheEvict` is applied on admin endpoints (e.g., PUT/POST/DELETE `/api/projects`) to immediately clear stale data upon updates.

### 12. Security Design
| Concern | Solution |
| :--- | :--- |
| **Authentication** | JWT Bearer token required for admin analytics endpoints. |
| **Authorization** | Spring Security RBAC ensuring only `ROLE_ADMIN` accesses sensitive paths. |
| **Transport** | End-to-end HTTPS via CloudFront and AWS App Runner SSL termination. |
| **Input validation** | Spring Boot Bean Validation (`@Valid`, `@NotBlank`, `@Email`) on DTOs. |
| **SQL Injection** | Mitigated natively by Spring Data JPA using prepared statements. |
| **XSS** | React automatically escapes HTML; Content Security Policy (CSP) headers applied. |
| **Secret Management** | Secrets are injected securely via AWS App Runner environment variables. |
| **Rate Limiting** | Redis `INCR` + `EXPIRE` window limits AI chatbot usage (e.g., 20 msg/hr/IP). |
| **Code Scanning** | GitHub CodeQL (SAST) and Gitleaks (secrets) running on every PR. |
| **Dependency Scanning** | GitHub Dependabot active to patch vulnerable libraries automatically. |

### 13. Deployment Architecture
- **Frontend:** The React build output (`dist` folder) is synced to an AWS S3 bucket configured for static website hosting. An AWS CloudFront distribution sits in front of the bucket, providing HTTPS, edge caching, and DDoS protection via AWS Shield.
- **Backend:** The Spring Boot application is containerized using a multi-stage Dockerfile (building with Maven, running on an Alpine JRE). The image is stored in AWS ECR. AWS App Runner runs the container, providing automatic scaling based on concurrent requests and managing health checks and load balancing.
- **Database:** Neon PostgreSQL provides a serverless SQL database with built-in connection pooling (PgBouncer), ensuring the application doesn't exhaust connections during scale-out.
- **Cache:** Upstash Redis provides a serverless, pay-per-request Redis instance.
- **Messaging:** Confluent Cloud provides a managed, serverless Kafka cluster on its free tier.

### 14. CI/CD Pipeline
**Frontend Pipeline (`frontend-ci-cd.yml`):**
1. **Trigger:** Push to `main` branch with changes in the `frontend/` directory.
2. Setup Node.js and run `npm install`.
3. Execute `Vitest` unit tests.
4. Execute `Cypress` E2E tests against a staged build.
5. Build the React application for production (`npm run build`).
6. Sync the `dist/` folder contents to the target AWS S3 bucket.
7. Issue a cache invalidation request to CloudFront to serve the latest assets.

**Backend Pipeline (`backend-ci-cd.yml`):**
1. **Trigger:** Push to `main` branch with changes in the `portfolio-service/` or `notification-service/` directories.
2. Set up JDK 21 environment.
3. Run `mvn test` (executes JUnit tests and Testcontainers for DB/Kafka).
4. Run SonarCloud analysis (fails the build if the Quality Gate fails).
5. Execute CodeQL for security analysis.
6. Execute Gitleaks to ensure no hardcoded secrets exist.
7. Build the Docker image using a multi-stage build process.
8. Authenticate and push the Docker image to AWS ECR.
9. Trigger AWS App Runner to deploy the new image revision.

### 15. Monitoring and Observability
- **Health Checks:** `/actuator/health` exposes the status of the DB, Redis, and Kafka connections for App Runner's load balancer.
- **Metrics:** `/actuator/metrics` exposes application metrics (JVM memory, HTTP request counts, durations) configured via Micrometer.
- **Logs:** SLF4J + Logback is configured to output structured JSON logs to stdout. MDC (Mapped Diagnostic Context) is used to inject a `requestId` into every log line for a given HTTP request, simplifying tracing.
- **Future:** OpenTelemetry integration for distributed tracing across `portfolio-service` and `notification-service`.

### 16. Disaster Recovery
| Scenario | Impact | Recovery Strategy | RTO | RPO |
| :--- | :--- | :--- | :--- | :--- |
| **App Runner instance failure** | Backend unavailable | App Runner detects failure via health check and auto-restarts container. | <2 min | 0 |
| **Neon PostgreSQL outage** | Data inaccessible | Redis cache serves read requests for projects. Writes fail gracefully. | <5 min | <1 hour |
| **Kafka unavailable** | Contact notifications delayed | Spring Kafka buffers locally / events retried when broker available. | N/A (async)| 0 |
| **S3/CloudFront outage** | Frontend unavailable | CloudFront serves cached version from edge nodes while S3 recovers. | <1 min | 0 |
