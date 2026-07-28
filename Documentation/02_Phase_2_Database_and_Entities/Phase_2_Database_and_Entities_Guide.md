# 📄 Phase 2 Documentation — Database Schemas, JPA Entities & Security Layer

## 📌 Executive Summary
Phase 2 established the core data layer for the portfolio-service microservice. It implemented PostgreSQL database schema migrations using Flyway, JPA entity models using Hibernate 6, Java 21 Record DTOs with 4-layer validation (including custom XSS protection), MapStruct object mapping interfaces, and Spring Data JPA repositories.

---

## 🏗️ Architecture & Component Inventory

### 1. Flyway Database Migrations (src/main/resources/db/migration/)
- **V1__create_contact_messages.sql**: Creates contact_messages table for storing contact form submissions with indexes on email, status, and created_at.
- **V2__create_resume_download_events.sql**: Creates 
esume_download_events table for tracking resume download analytics (IP address, User-Agent, Referer).
- **V3__create_projects.sql**: Creates projects table using **JSONB** format for tech stacks (	ech_stack). Pre-seeds two initial projects:
  1. *Portfolio Microservices Platform* (ACTIVE, Public GitHub URL)
  2. *Civil Platform* (IN_PROGRESS, Private repository)

### 2. Domain Enums (com.satheesh.portfolio.enums)
- **ContactStatus**: PENDING, NOTIFIED, FAILED (Tracks contact notification state).
- **ProjectStatus**: ACTIVE, IN_PROGRESS, ARCHIVED (Tracks project visibility state).

### 3. Security & Validation Layer (com.satheesh.portfolio.validation)
- **@NoHtml**: Custom JSR-380 validation annotation to prevent XSS and script injection attacks.
- **NoHtmlValidator**: Regex-based validator implementation that detects and rejects HTML tags (<script>, <iframe>, <img>), JavaScript protocols (javascript:), and event handlers (onclick=, onerror=).

### 4. Data Transfer Objects (com.satheesh.portfolio.dto)
Implemented using **Java 21 Records** for immutability and zero boilerplate:
- **ContactRequestDTO**: Input DTO with 4-layer defense (@NotBlank, @Size, @Pattern whitelist, @NoHtml).
- **ContactResponseDTO**: Safe user-facing response DTO after submission.
- **ProjectResponseDTO**: Response DTO hiding internal DB fields (displayOrder, createdAt).
- **ChatMessageDTO**: Input DTO for Spring AI chatbot interaction with @NoHtml protection.

### 5. JPA Entities (com.satheesh.portfolio.entity)
- **ContactMessage**: Entity mapping to contact_messages. Uses Hibernate @CreationTimestamp and @UpdateTimestamp.
- **Project**: Entity mapping to projects. Uses Hibernate 6 @JdbcTypeCode(SqlTypes.JSON) for JSONB mapping to List<String>.
- **ResumeDownloadEvent**: Entity mapping to 
esume_download_events for append-only download event logs.

### 6. Object Mappers (com.satheesh.portfolio.mapper)
- **ContactMapper**: MapStruct interface mapping between ContactRequestDTO -> ContactMessage -> ContactResponseDTO.
- **ProjectMapper**: MapStruct interface mapping Project -> ProjectResponseDTO and List<Project> -> List<ProjectResponseDTO>.

### 7. Spring Data JPA Repositories (com.satheesh.portfolio.repository)
- **ContactMessageRepository**: Includes indByEmailAndCreatedAtAfter() for 5-minute duplicate submission detection.
- **ProjectRepository**: Includes indByFeaturedTrueAndStatusInOrderByDisplayOrderAsc() for homepage featured project retrieval.
- **ResumeDownloadRepository**: Includes countByDownloadedAtBetween() for date-range analytics.

---

## 🔧 Infrastructure & Local Port Allocations
To avoid conflicts with external services (such as the Civil Platform project), local Docker host port mappings were configured as follows:

| Service | Container Name | Host Port | Internal Port | Status |
|---|---|---|---|---|
| PostgreSQL 16 | portfolio-postgres | **5433** | 5432 | Healthy |
| Redis 7 | portfolio-redis | **6380** | 6379 | Healthy |
| Apache Kafka | portfolio-kafka | **9092** | 9092 | Healthy |
| Zookeeper | portfolio-zookeeper | **2181** | 2181 | Running |

---

## 🛡️ Git & Branch Security Setup
- Implemented **develop** branch workflow for active development.
- Configured GitHub **Branch Protection Rulesets** for both main and develop branches:
  - Restricted force pushes & deletions
  - Required Pull Request before merging
  - Required status check (uild-and-test) to pass before merging
