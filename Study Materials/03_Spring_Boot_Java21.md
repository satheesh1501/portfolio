# Spring Boot 3.x + Java 21 — Complete Interview Study Guide

## 1. What is Spring Boot?
Spring Boot is an extension of the Spring framework that eliminates boilerplate configurations required to set up a Spring application.
- **Convention over Configuration**: It provides sensible defaults, reducing the need for explicit configuration.
- **Auto-configuration**: Automatically configures your Spring application based on the jar dependencies present on the classpath.
- **Embedded Server**: Includes embedded Tomcat, Jetty, or Undertow, eliminating the need to deploy WAR files to an external web server.

## 2. Core Spring Boot Concepts
- **@SpringBootApplication**: A convenience annotation that combines `@Configuration`, `@EnableAutoConfiguration`, and `@ComponentScan`.
- **Beans and Dependency Injection**: 
  - `@Component`: Generic stereotype for any Spring-managed component.
  - `@Service`: Service layer.
  - `@Repository`: Data access layer.
  - `@Controller`: Presentation layer (MVC).
- **Application Context vs BeanFactory**: `BeanFactory` is the simplest container providing DI. `ApplicationContext` adds more enterprise-specific functionality (event publishing, AOP, etc.).
- **Bean Scopes**: Singleton (default), Prototype, Request, Session, Global-session.
- **@Autowired vs Constructor Injection**: Constructor injection is preferred because it allows for immutable fields (`final`), ensures dependencies are not null, and makes testing easier without Spring context.
- **Auto-configuration mechanism**: Uses `@Conditional` annotations to register beans only if certain conditions are met (e.g., class is present).
- **application.properties vs application.yml**: Both hold properties. YAML supports hierarchical configuration and is often more readable.
- **Profiles**: Using `@Profile` and `spring.profiles.active` allows you to segregate parts of your application configuration and make it available only in certain environments.

## 3. Spring Boot REST
- **@RestController vs @Controller**: `@RestController` combines `@Controller` and `@ResponseBody`, meaning returned objects are automatically serialized to JSON/XML.
- **Mapping Annotations**: `@RequestMapping`, `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`.
- **Parameters**: 
  - `@PathVariable`: Extracts values from the URI path.
  - `@RequestParam`: Extracts query parameters.
  - `@RequestBody`: Binds the HTTP request body to a domain object.
- **Validation**: `@Valid` triggers Bean Validation (Hibernate Validator).
- **ResponseEntity**: Represents the entire HTTP response (status code, headers, and body).
- **Exception Handling**: `@ControllerAdvice` provides global exception handling, and `@ExceptionHandler` handles specific exceptions.

## 4. Spring Data JPA
- **Annotations**: `@Entity` (maps class to DB table), `@Table` (specifies table name), `@Column`, `@Id` (primary key), `@GeneratedValue` (auto-generation strategy).
- **Relationships**: `@OneToMany`, `@ManyToOne`, `@ManyToMany`, `@OneToOne`.
- **JpaRepository**: Extends `PagingAndSortingRepository` and `CrudRepository`. Provides methods like `save()`, `findById()`, `findAll()`, `delete()`.
- **Custom queries**: Derived queries (`findByLastName`), `@Query` (JPQL), and native queries.
- **Pagination**: Use `Pageable` in repository methods to get a `Page<T>` or `Slice<T>`.

## 5. Java 21 Key Features
- **Records**: Concise syntax for immutable data carriers. Excellent for DTOs.
- **Sealed Classes**: Restrict which other classes or interfaces may extend or implement them.
- **Pattern Matching for instanceof**: Eliminates the need for explicit casting after `instanceof` checks.
- **Text Blocks**: Multi-line string literals.
- **Virtual Threads (Project Loom)**: Lightweight threads managed by the JVM rather than the OS. High throughput for blocking I/O applications. Highly relevant for Spring Boot 3.2+ as it enables scaling without reactive programming.
- **Switch Expressions**: Can return values and use arrow syntax (`->`) to avoid fall-through.

## 6. How used in Portfolio
- **Java Records**: Used for Data Transfer Objects (DTOs) like `ContactRequestDTO`, `ChatRequestDTO` to ensure immutability and reduce boilerplate.
- **Virtual threads**: Enabled for handling concurrent I/O-bound AI API calls efficiently without exhausting the OS thread pool.
- **Constructor injection**: Consistently used throughout services and controllers for better testability and immutability.

## 7. Top 15 Interview Q&A
1. **What is Spring Boot and how is it different from Spring Framework?** Spring Framework provides comprehensive infrastructure support, while Spring Boot is an extension that simplifies the setup and development of Spring apps via auto-configuration and embedded servers.
2. **What is auto-configuration?** Spring Boot guesses and configures beans you likely need based on classpath dependencies.
3. **Explain the Spring Bean lifecycle**: Instantiation -> Populate Properties -> InitializingBean (afterPropertiesSet) -> Custom Init -> Ready for use -> DisposableBean (destroy) -> Custom Destroy.
4. **What is @SpringBootApplication?** Combines `@Configuration`, `@EnableAutoConfiguration`, and `@ComponentScan`.
5. **Difference between @Component, @Service, @Repository, @Controller?** All are `@Component`s. Service denotes business logic, Repository denotes data access (adds persistence exception translation), Controller handles web requests.
6. **Why prefer constructor injection over @Autowired?** Ensures mandatory dependencies are present, allows `final` fields, easier unit testing.
7. **What are Spring Profiles?** A way to separate configurations for different environments (dev, test, prod).
8. **What is @ControllerAdvice?** An interceptor that allows global exception handling across all `@RequestMapping` methods.
9. **Difference between @RestController and @Controller?** `@RestController` automatically adds `@ResponseBody` to all methods.
10. **What is ResponseEntity?** An object representing the full HTTP response (headers, status, body).
11. **What are Java Records?** Immutable data carriers introduced in Java 14/16, reducing boilerplate for classes holding state.
12. **What are Virtual Threads?** Lightweight, JVM-managed threads introduced in Java 21 that drastically improve throughput for blocking I/O workloads.
13. **Difference between JpaRepository and CrudRepository?** `JpaRepository` adds JPA-specific methods like flushing the persistence context and batch deletion.
14. **What is @Transactional?** Declares that a method or class should be executed within a database transaction.
15. **What is the N+1 query problem and how do you solve it?** When loading an entity fetches its associations individually, resulting in N additional queries. Solved using `JOIN FETCH` in JPQL or EntityGraphs.
