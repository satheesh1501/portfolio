# 📄 Phase 3 Documentation — REST API, Business Services, Rate Limiting & Kafka Producer

## 📌 Executive Summary
Phase 3 completed the full application business logic and REST API layer for `portfolio-service`. It integrated Redis sliding-window rate limiting, Kafka event publishing, structured enterprise logging (`AppLogger`), OpenAPI Swagger documentation, and unified exception handling.

---

## 🏗️ Architecture & Component Inventory

### 1. Centralized System & Message Constants (`com.satheesh.portfolio.constants`)
- **`AppConstants`**: Encapsulates API routes (`/api/v1/contact`, `/api/v1/projects`, `/api/v1/resume`, `/api/v1/chat`), Kafka topic names (`contact-notifications-topic`), Redis rate limiting keys, threshold limits (Contact: 3 req / 15 min; Chat: 10 req / 5 min), and tracing header names.
- **`MessageConstants`**: Manages all user-facing success messages, exception descriptions, and log action tags.

### 2. Enterprise Structured Logging Utility (`com.satheesh.portfolio.util`)
- **`AppLogger`**: Standardizes log layout across all layers using template:
  `"[Portfolio-Service] [TraceID: {}] [IP: {}] [Class: {}] [Method: {}] - {}: {}"`
  Integrates MDC tracing for end-to-end correlation across microservices.

### 3. Security & Rate Limiting (`com.satheesh.portfolio.security`)
- **`RateLimiterService`**: Redis Sorted Set (ZSET) sliding-window rate limiter preventing API abuse per client IP.
- **`SecurityConfig`**: Spring Security 6 stateless filter chain permitting public access to portfolio APIs, Swagger UI, and Actuator endpoints while enforcing strict CORS headers for the frontend (`http://localhost:5173`).

### 4. Event Streaming & Kafka (`com.satheesh.portfolio.kafka`)
- **`KafkaProducerConfig`**: Auto-provisions `contact-notifications-topic` on broker startup.
- **`ContactSubmittedEvent`**: Immutable event record sent to Kafka.
- **`ContactEventProducer`**: Asynchronous Kafka producer publishing events with completion callbacks.

### 5. Services & Business Workflow (`com.satheesh.portfolio.service`)
- **`ContactServiceImpl`**: Orchestrates rate limit check -> 5-minute duplicate submission check -> PostgreSQL save -> Kafka event publish -> status update.
- **`ProjectServiceImpl`**: Queries featured and individual projects with Redis caching (`@Cacheable`).
- **`ResumeServiceImpl`**: Captures client IP, User-Agent, and Referer for resume download analytics.
- **`ChatServiceImpl`**: Handles Spring AI Gemini Chatbot interaction with rate limiting.

### 6. REST Controllers (`com.satheesh.portfolio.controller`)
- **`ContactController`**: `POST /api/v1/contact` (Contact submission).
- **`ProjectController`**: `GET /api/v1/projects`, `GET /api/v1/projects/{id}`.
- **`ResumeController`**: `POST /api/v1/resume/download`, `GET /api/v1/resume/stats`.
- **`ChatController`**: `POST /api/v1/chat` (AI Assistant interface).

### 7. Exception Handling & API Documentation
- **`GlobalExceptionHandler`**: Intercepts JSR-380 validation failures, rate limit breaches, duplicates, and missing resources, returning clean HTTP 400, 404, 429, and 500 JSON payloads.
- **`OpenApiConfig`**: Configures live Swagger UI dashboard at `http://localhost:8080/swagger-ui.html`.

---

## 🛡️ GitHub Branch Protection Status
The `develop` branch rule successfully enforced Pull Request requirements during git push, confirming that branch protection is fully active on remote.
