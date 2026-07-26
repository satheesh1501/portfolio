# Satheesh Kumar P — Developer Portfolio
## Low-Level Design (LLD)

### 1. Document Info
| Attribute | Details |
| :--- | :--- |
| **Author** | Satheesh Kumar P |
| **Version** | 1.0 |
| **Date** | 2026-07-26 |
| **Status** | Approved |

### 2. Overview
This Low-Level Design (LLD) document details the internal architecture, class structures, database schemas, and API contracts for the portfolio application. It specifically focuses on the internals of `portfolio-service` and the event-driven interaction with `notification-service`, as outlined in the High-Level Design (HLD).

### 3. Package Structure
```text
com.satheesh.portfolio/
├── PortfolioApplication.java
├── config/
│   ├── SecurityConfig.java
│   ├── CacheConfig.java
│   ├── KafkaProducerConfig.java
│   └── OpenApiConfig.java
├── controller/
│   ├── ContactController.java
│   ├── ChatController.java
│   ├── ProjectController.java
│   ├── ResumeController.java
│   └── AnalyticsController.java
├── service/
│   ├── ContactService.java
│   ├── ChatService.java
│   ├── ProjectService.java
│   ├── ResumeService.java
│   └── RateLimiterService.java
├── repository/
│   ├── ContactMessageRepository.java
│   ├── ProjectRepository.java
│   └── ResumeDownloadEventRepository.java
├── entity/
│   ├── ContactMessage.java
│   ├── Project.java
│   └── ResumeDownloadEvent.java
├── dto/
│   ├── request/
│   │   ├── ContactRequestDTO.java
│   │   └── ChatRequestDTO.java
│   └── response/
│       ├── ContactResponseDTO.java
│       ├── ProjectResponseDTO.java
│       └── ApiErrorResponse.java
├── mapper/
│   ├── ContactMapper.java
│   └── ProjectMapper.java
├── kafka/
│   ├── event/
│   │   └── ContactSubmittedEvent.java
│   └── producer/
│       └── ContactEventProducer.java
├── ai/
│   └── ChatService.java
├── security/
│   ├── JwtUtil.java
│   ├── JwtAuthenticationFilter.java
│   └── AdminUserDetailsService.java
└── exception/
    ├── GlobalExceptionHandler.java
    ├── RateLimitExceededException.java
    └── ResourceNotFoundException.java
```

### 4. Entity Class Designs

**ContactMessage Entity**
```java
@Entity
@Table(name = "contact_messages")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(nullable = false, length = 255)
    private String email;
    
    @Column(length = 200)
    private String subject;
    
    @Column(length = 2000)
    private String message;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(length = 45)
    private String ipAddress;
}
```

**Project Entity**
```java
@Entity
@Table(name = "projects")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false, length = 200)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(length = 50)
    private String status;
    
    @Column(columnDefinition = "JSONB")
    private String techStack;
    
    @Column(length = 500)
    private String githubUrl;
    
    @Column(length = 500)
    private String demoUrl;
    
    @Column(nullable = false)
    private Integer displayOrder;
    
    @Column(nullable = false)
    private Boolean isFeatured;
}
```

**ResumeDownloadEvent Entity**
```java
@Entity
@Table(name = "resume_download_events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeDownloadEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDateTime downloadedAt;
    
    @Column(length = 45)
    private String ipAddress;
    
    @Column(length = 100)
    private String country;
}
```

### 5. DTO Designs
Using Java 14+ Records for immutable DTOs:

```java
public record ContactRequestDTO(
    @NotBlank(message = "Name is required") String name,
    @NotBlank(message = "Email is required") @Email String email,
    String subject,
    @NotBlank(message = "Message is required") String message
) {}

public record ChatRequestDTO(
    @NotBlank(message = "Message cannot be empty") String message,
    String sessionId
) {}

public record ProjectResponseDTO(
    String id,
    String title,
    String description,
    String status,
    List<String> techStack,
    String githubUrl,
    String demoUrl,
    Integer displayOrder,
    Boolean isFeatured
) {}

public record ApiErrorResponse(
    LocalDateTime timestamp,
    int status,
    String error,
    String message,
    String path
) {}
```

### 6. Flyway Database Schema

**V1__create_contact_messages.sql**
```sql
CREATE TABLE contact_messages (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    subject VARCHAR(200),
    message TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    ip_address VARCHAR(45)
);

CREATE INDEX idx_contact_created_at ON contact_messages(created_at);
```

**V2__create_resume_download_events.sql**
```sql
CREATE TABLE resume_download_events (
    id BIGSERIAL PRIMARY KEY,
    downloaded_at TIMESTAMP NOT NULL DEFAULT NOW(),
    ip_address VARCHAR(45),
    country VARCHAR(100)
);

CREATE INDEX idx_resume_download_at ON resume_download_events(downloaded_at);
```

**V3__create_projects.sql**
```sql
CREATE TABLE projects (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(200) NOT NULL,
    description TEXT,
    status VARCHAR(50),
    tech_stack JSONB,
    github_url VARCHAR(500),
    demo_url VARCHAR(500),
    display_order INT NOT NULL DEFAULT 0,
    is_featured BOOLEAN NOT NULL DEFAULT false
);

CREATE INDEX idx_projects_order ON projects(display_order);
```

### 7. API Contracts (Full OpenAPI-style)

**1. Health Check**
- **Method:** `GET /actuator/health`
- **Response 200 OK:** `{"status": "UP"}`

**2. Submit Contact Form**
- **Method:** `POST /api/contact`
- **Body:** `ContactRequestDTO` JSON
- **Response 201 Created:** `ContactResponseDTO` JSON
- **Error 400:** `ApiErrorResponse` (Validation failed)
- **Error 500:** `ApiErrorResponse` (Server error)

**3. Download Resume**
- **Method:** `GET /api/resume/download`
- **Response 200 OK:** Binary PDF attachment (`Content-Type: application/pdf`)

**4. AI Chat Stream**
- **Method:** `POST /api/chat`
- **Body:** `ChatRequestDTO` JSON
- **Response 200 OK:** `text/event-stream` (SSE tokens)
- **Error 429:** `ApiErrorResponse` (Rate limit exceeded)

**5. Get Projects**
- **Method:** `GET /api/projects`
- **Response 200 OK:** JSON Array of `ProjectResponseDTO`

**6. Get Analytics**
- **Method:** `GET /api/analytics/downloads`
- **Headers:** `Authorization: Bearer <JWT>`
- **Response 200 OK:** JSON analytics data
- **Error 401/403:** Unauthorized / Forbidden

### 8. Sequence Diagrams

**Sequence 1: POST /api/contact**
1. Browser sends `POST /api/contact` payload.
2. `ContactController` receives request, `@Valid` triggers Bean Validation.
3. Controller passes DTO to `ContactService`.
4. `ContactService` uses MapStruct to map DTO to `ContactMessage` entity.
5. `ContactService` calls `ContactMessageRepository.save()`.
6. Entity is persisted in PostgreSQL.
7. `ContactService` calls `ContactEventProducer.publish()`.
8. Producer pushes `ContactSubmittedEvent` to Kafka topic `portfolio.contact.submitted`.
9. Controller returns mapped `ContactResponseDTO` with HTTP 201.
10. Browser receives response.

**Sequence 2: POST /api/chat (AI streaming)**
1. Browser sends `POST /api/chat`.
2. `ChatController` calls `RateLimiterService`.
3. `RateLimiterService` performs INCR on Redis key `rate_limit:chat:{ip}`.
4. If rate limit exceeded, throws `RateLimitExceededException` (caught by GlobalExceptionHandler → 429).
5. If allowed, Controller passes message to `ChatService`.
6. `ChatService` invokes Spring AI `ChatClient.prompt().stream()`.
7. Request is sent to Google Gemini API.
8. Gemini streams tokens back via SSE.
9. Controller returns `Flux<String>` causing Spring WebFlux/MVC to stream SSE back to the Browser.

**Sequence 3: GET /api/projects (cached)**
1. Browser sends `GET /api/projects`.
2. `ProjectController` calls `ProjectService.getAllProjects()`.
3. Spring AOP intercepts due to `@Cacheable("projects")`.
4. Checks Upstash Redis for existing key.
5. **(Cache HIT):** Returns cached JSON immediately to Controller → Browser.
6. **(Cache MISS):** Invokes `ProjectRepository.findAll()`, queries PostgreSQL.
7. Maps entities to DTOs.
8. Spring AOP stores resulting JSON in Redis.
9. Returns payload to Controller → Browser.

### 9. Design Patterns Used
| Pattern | Class | How Used |
| :--- | :--- | :--- |
| **Builder** | `ContactMessage`, DTOs | Used via Lombok `@Builder` for clean, fluent object instantiation. |
| **Singleton** | `@Service`, `@Repository` | Spring IoC container ensures single, stateless instances of beans. |
| **Proxy** | `@Transactional`, `@Cacheable`| Spring AOP wraps services to inject caching and transaction boundaries dynamically. |
| **Chain of Responsibility**| Filter Chain | `JwtAuthenticationFilter` intercepts requests before passing to the `SecurityFilterChain`. |
| **Strategy** | Spring AI `ChatModel` | Chat logic relies on interfaces, easily swapping Gemini for OpenAI without refactoring `ChatService`. |
| **Observer** | Kafka Producer/Consumer | `ContactEventProducer` (Subject) pushes events; `notification-service` (Observer) reacts. |
| **Facade** | `ContactService` | Hides the complexity of interacting with repositories, mappers, and messaging from the controller. |
| **Template Method** | `JpaRepository` | Spring Data provides boilerplate implementations for standard CRUD queries. |

### 10. Error Handling Strategy
Implemented via `@ControllerAdvice` in `GlobalExceptionHandler`:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleValidation(MethodArgumentNotValidException ex, WebRequest req) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                          .map(e -> e.getField() + ": " + e.getDefaultMessage())
                          .collect(Collectors.joining(", "));
        return new ApiErrorResponse(LocalDateTime.now(), 400, "Validation Failed", errors, req.getDescription(false));
    }

    @ExceptionHandler(RateLimitExceededException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public ApiErrorResponse handleRateLimit(RateLimitExceededException ex, WebRequest req) {
        return new ApiErrorResponse(LocalDateTime.now(), 429, "Too Many Requests", ex.getMessage(), req.getDescription(false));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNotFound(ResourceNotFoundException ex, WebRequest req) {
        return new ApiErrorResponse(LocalDateTime.now(), 404, "Not Found", ex.getMessage(), req.getDescription(false));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleGeneric(Exception ex, WebRequest req) {
        return new ApiErrorResponse(LocalDateTime.now(), 500, "Internal Server Error", "An unexpected error occurred", req.getDescription(false));
    }
}
```

### 11. MapStruct Mapper Design
```java
@Mapper(componentModel = "spring")
public interface ContactMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "ipAddress", ignore = true)
    ContactMessage toEntity(ContactRequestDTO dto);
    
    ContactResponseDTO toResponseDTO(ContactMessage entity);
}
```

### 12. Redis Rate Limiter Design
**Service Logic (`RateLimiterService`):**
```java
@Service
@RequiredArgsConstructor
public class RateLimiterService {
    private final StringRedisTemplate redisTemplate;
    private static final int MAX_REQUESTS_PER_HOUR = 20;

    public void checkRateLimit(String ipAddress) {
        String key = "rate_limit:chat:" + ipAddress;
        Long count = redisTemplate.opsForValue().increment(key);
        
        if (count != null && count == 1) {
            // First request, set expiration to 1 hour (fixed window)
            redisTemplate.expire(key, Duration.ofHours(1));
        }
        
        if (count != null && count > MAX_REQUESTS_PER_HOUR) {
            throw new RateLimitExceededException("You have exceeded the maximum AI chat limit for this hour.");
        }
    }
}
```
*Note:* A fixed window is chosen for simplicity and low Redis memory footprint (one integer key per IP).

### 13. Spring AI ChatService Design
```java
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatClient chatClient;
    private static final String SYSTEM_PROMPT = """
        You are an AI assistant for Satheesh Kumar P. 
        Your goal is to answer questions about his skills, experience, and projects.
        Be professional, concise, and helpful.
        """;
    
    public Flux<String> chat(String userMessage) {
        return chatClient.prompt()
            .system(SYSTEM_PROMPT)
            .user(userMessage)
            .stream()
            .content();
    }
}
```

### 14. Kafka Event Design
**Event Payload:**
```java
public record ContactSubmittedEvent(
    String id,
    String name,
    String email,
    String subject,
    LocalDateTime timestamp
) {}
```
**Producer Config:**
- Topic: `portfolio.contact.submitted`
- `ContactEventProducer` leverages `KafkaTemplate<String, ContactSubmittedEvent>`.
- The event handles serialization to JSON seamlessly via Spring Boot defaults.

### 15. notification-service Package Structure
```text
com.satheesh.notification/
├── NotificationApplication.java
├── config/
│   └── KafkaConsumerConfig.java
├── consumer/
│   └── ContactEventConsumer.java
├── service/
│   └── EmailNotificationService.java
└── model/
    └── ContactSubmittedEvent.java
```
**Consumer Logic:**
```java
@Service
@RequiredArgsConstructor
public class ContactEventConsumer {
    private final EmailNotificationService emailService;

    @KafkaListener(topics = "portfolio.contact.submitted", groupId = "notification-group")
    public void consume(ContactSubmittedEvent event) {
        emailService.sendAdminNotification(event);
        emailService.sendUserAutoReply(event);
    }
}
```
