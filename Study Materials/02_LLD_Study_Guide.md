# Low-Level Design (LLD) Study Guide

## 1. What is LLD?
**Definition:** Low-Level Design (LLD) is a component-level design process that details the internal logic, algorithms, data structures, and class organization of individual modules defined in the HLD. It provides the "blueprint" for developers to write the code.

**When is it done?** After High-Level Design (HLD) is finalized, and immediately before or during the implementation (coding) phase.

**Who creates it?** Usually created by Senior Developers, Tech Leads, or the developers who will implement the feature.

## 2. The 9 Components of LLD

- **Class Diagram:** Shows classes, interfaces, attributes, methods, and relationships (inheritance, composition, aggregation).
- **Database Schema / ER Diagram:** Details tables, primary keys (PK), foreign keys (FK), indexes, and constraints.
- **API Contract / Interface Design:** Detailed specifications of API endpoints, request/response payloads, and HTTP methods (e.g., OpenAPI/Swagger format).
- **Sequence Diagram:** Visualizes the step-by-step flow of messages/method calls between objects over time.
- **Design Patterns:** Reusable solutions to common software design problems.
  | Category | Patterns |
  | :--- | :--- |
  | **Creational** | Singleton, Factory, Builder, Prototype |
  | **Structural** | Adapter, Decorator, Proxy, Facade |
  | **Behavioral** | Strategy, Observer, Template Method, Chain of Responsibility, Command |
- **State Machine Diagrams:** Describes the various states an object can be in and the transitions between them.
- **Algorithm Design:** Pseudocode or detailed logic flow, evaluating time and space complexity (Big O).
- **Error Handling Strategy:** Standardized exception hierarchies, global exception handlers, and error response formats.
- **Validation Rules:** Input validation constraints (e.g., regex, max length, nullability) applied at the API or domain level.

## 3. Design Patterns Deep Dive with Spring Boot Examples

### Creational
- **Singleton:** Ensures a class has only one instance. (Spring `@Component` and `@Service` are singletons by default).
- **Factory:** Creates objects without specifying the exact class to create. (Spring `BeanFactory`).
- **Builder:** Separates object construction from its representation. (Lombok `@Builder` on DTOs/Entities).
- **Prototype:** Creates new objects by cloning an existing one. (Spring `@Scope("prototype")`).

### Structural
- **Adapter:** Allows incompatible interfaces to work together. (e.g., wrapping a legacy 3rd-party library into a common interface).
- **Decorator:** Adds behavior to an object dynamically. (e.g., Java IO streams, dynamically adding caching to a service).
- **Proxy:** Provides a placeholder to control access to an object. (Spring AOP, `@Transactional` creates a proxy around the target class).
- **Facade:** Provides a simplified interface to a complex subsystem. (A Controller acting as a facade for multiple internal services).

### Behavioral
- **Strategy:** Defines a family of algorithms and makes them interchangeable. (e.g., sorting strategies, payment methods injected via interfaces).
- **Observer:** A subject maintains a list of dependents and notifies them of state changes. (Spring `ApplicationEventPublisher` and `@EventListener`).
- **Template Method:** Defines the skeleton of an algorithm in a base class but lets subclasses override specific steps. (Spring `JdbcTemplate` or `RestTemplate`).
- **Chain of Responsibility:** Passes a request along a chain of handlers. (Spring Security Filter Chain).
- **Command:** Encapsulates a request as an object. (Runnable interface, or CQRS command objects).

## 4. SOLID Principles with Portfolio Examples

- **S: Single Responsibility:** A class should have one reason to change. *Example:* Controllers only handle HTTP routing; Service layers handle business logic.
- **O: Open/Closed:** Software entities should be open for extension, closed for modification. *Example:* Using a Spring AI `ChatClient` interface. You can add new AI providers (OpenAI, Gemini) without changing the core chat service.
- **L: Liskov Substitution:** Subtypes must be substitutable for their base types. *Example:* If an interface `StorageService` has an `upload()` method, both `S3StorageService` and `LocalStorageService` implementations must successfully upload without breaking expectations.
- **I: Interface Segregation:** Clients shouldn't be forced to depend on interfaces they don't use. *Example:* Splitting a monolithic `UserRepository` into `UserReadRepository` and `UserWriteRepository` if some clients only need read access.
- **D: Dependency Inversion:** High-level modules should not depend on low-level modules; both should depend on abstractions. *Example:* Injecting a `NotificationService` interface into a controller, rather than the concrete `EmailNotificationService`.

## 5. LLD for Portfolio Project

### Entity Classes
```java
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String message;
    private LocalDateTime createdAt;
}
```

### Database Schema SQL
```sql
CREATE TABLE contact_message (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_contact_email ON contact_message(email);
```

### API Contract Example
**POST /api/contact**
- **Request:**
  ```json
  { "name": "John", "email": "john@example.com", "message": "Hello!" }
  ```
- **Response (201 Created):**
  ```json
  { "status": "success", "messageId": 123 }
  ```

### Sequence Diagram (Contact Form)
1. Client sends POST /api/contact.
2. `ContactController` receives request, validates DTO.
3. Controller calls `ContactService.saveMessage()`.
4. Service maps DTO to Entity and calls `ContactRepository.save()`.
5. Database returns saved entity.
6. Service publishes `ContactReceivedEvent`.
7. Controller returns 201 Created to Client.

## 6. Top 15 LLD Interview Questions & Answers

**1. What are design patterns?**
Proven, standardized solutions to common software design problems. They represent best practices used by experienced object-oriented software developers.

**2. Singleton pattern and how Spring implements it?**
Singleton restricts instantiation of a class to a single object. Spring creates singleton beans by default per application context, caching the instance and returning it for subsequent requests.

**3. Strategy pattern with real example?**
Strategy allows selecting an algorithm at runtime. Example: An e-commerce app with a `PaymentStrategy` interface and implementations `CreditCardPayment`, `PayPalPayment`, `CryptoPayment`.

**4. @Component vs @Service vs @Repository vs @Controller?**
`@Component` is a generic stereotype. `@Service` denotes business logic. `@Repository` denotes data access and provides exception translation. `@Controller` handles web requests.

**5. What is an ER diagram and what should it show?**
Entity-Relationship diagram illustrates the logical structure of databases. It shows tables (entities), columns (attributes), and relationships (1:1, 1:N, N:M) using foreign keys.

**6. Composition vs aggregation in OOP?**
Composition implies a strict lifecycle dependency (a House "owns" a Room; if House is destroyed, Room is destroyed). Aggregation is a weak relationship (a Department "has" Teachers; Teachers exist without the Department).

**7. What is a sequence diagram?**
A UML diagram that shows object interactions arranged in time sequence. It details the messages passed between objects to execute a scenario.

**8. Explain SOLID principles with examples from your project?**
(Refer to the detailed SOLID section above). S - separate controller/service. O - extend via interfaces. L - interchangeable impls. I - focused interfaces. D - depend on abstractions (interfaces).

**9. Abstract class vs interface?**
Interfaces define a contract (can have default methods in newer Java versions) and support multiple inheritance. Abstract classes can hold state (instance variables) and define common base implementation, but support only single inheritance.

**10. What is MapStruct and why use it?**
A code generator that greatly simplifies mapping between Java bean types (e.g., Entity to DTO). It generates type-safe, high-performance mapping code at compile time.

**11. What is the Proxy pattern in Spring?**
Spring uses proxies (JDK dynamic proxies or CGLIB) to wrap beans and intercept method calls to add cross-cutting concerns like `@Transactional` or `@Async` without modifying the actual class.

**12. What is the Observer pattern? How does Kafka use it?**
A pattern where dependents (observers) are notified of state changes in a subject. Kafka implements a distributed publish-subscribe model, fundamentally acting as the Observer pattern at a macro architectural level.

**13. What is the difference between @Transactional and @Cacheable?**
`@Transactional` ensures a method executes within a database transaction (commit on success, rollback on failure). `@Cacheable` intercepts the method call to return a cached result if available, bypassing the method execution.

**14. What is the Builder pattern? Where have you used it?**
A pattern to construct complex objects step by step. Used heavily with Lombok's `@Builder` annotation to create entities or DTOs fluently without massive constructors.

**15. What is the Template Method pattern? Give a Spring example.**
It defines the skeleton of an operation, deferring some steps to subclasses. Spring's `JdbcTemplate` defines the DB connection boilerplate, while letting the developer provide the specific SQL and row mapping logic.
