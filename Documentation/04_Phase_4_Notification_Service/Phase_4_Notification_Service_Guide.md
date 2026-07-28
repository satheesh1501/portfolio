# 📄 Phase 4 Documentation — Shared Library & Notification Microservice

## 📌 Executive Summary
Phase 4 transformed the portfolio repository into a production-grade **Maven Multi-Module Monorepo**. It introduced the **`common-library`** shared module to eliminate cross-microservice code duplication, built the **`notification-service`** event-driven consumer microservice, integrated **Mailpit** for local email catching, and designed responsive Thymeleaf HTML email templates.

---

## 🏗️ Monorepo Architecture & Modules

```
Portfolio/
├── pom.xml                             [Parent Monorepo POM]
├── common-library/                     [Shared Maven Module]
│   └── src/main/java/com/satheesh/common/
│       ├── constants/AppConstants.java
│       ├── constants/MessageConstants.java
│       ├── util/AppLogger.java
│       └── validation/NoHtml.java
├── portfolio-service/                  [Core Portfolio API Microservice]
└── notification-service/               [Email Notification Microservice]
```

### 1. `common-library` Shared Module
- **`AppLogger`**: Enterprise structured logging utility supporting dynamic service names:
  `"[Notification-Service] [TraceID: {}] [IP: {}] [Class: {}] [Method: {}] - {}: {}"`
- **`AppConstants` & `MessageConstants`**: Single source of truth for Kafka topics (`contact-notifications-topic`), Redis keys, API paths, and target notification emails (`psatheesh1501@gmail.com`).
- **`@NoHtml` & `NoHtmlValidator`**: Shared JSR-380 XSS security validator.

### 2. Local Email Mocking Infrastructure (`docker-compose.yml`)
- Added **Mailpit** container:
  - SMTP Port: `1025`
  - Web UI Dashboard: **`http://localhost:8025`**
  - Messages are caught locally without needing internet or cloud credentials.

### 3. `notification-service` Microservice
- **`ContactSubmittedEvent`**: Consumer record payload.
- **`KafkaConsumerConfig`**: Spring Kafka container factory configured with `ErrorHandlingDeserializer` and JSON deserialization.
- **`ContactEventConsumer`**: `@KafkaListener` listening to topic `contact-notifications-topic` under group `notification-service-group`.
- **`EmailServiceImpl`**: Uses Thymeleaf template engine to render `contact-notification.html` and sends emails via `JavaMailSender`.
- **`contact-notification.html`**: Styled responsive HTML email template featuring gradient headers, dark theme, and structured message metadata.

---

## 🧪 Verification Matrix

- **Parent Monorepo Build**: `mvn clean install` -> **SUCCESS** across all 4 reactor modules:
  1. `portfolio-monorepo` (SUCCESS)
  2. `common-library` (SUCCESS)
  3. `portfolio-service` (SUCCESS)
  4. `notification-service` (SUCCESS)
