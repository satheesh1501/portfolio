# Complete Testing Strategy — JUnit 5 + Mockito + Testcontainers + Cypress

## PART 1: Testing Pyramid
- **Unit tests (70%)**: JUnit 5 + Mockito (fast, isolated, tests individual classes).
- **Integration tests (20%)**: Spring Boot Test + Testcontainers (tests components interacting, real databases).
- **E2E tests (10%)**: Cypress (tests the entire system from the browser).

## PART 2: JUnit 5
1. **What is JUnit 5?** The modern standard for testing in Java, comprising the Jupiter API and platform.
2. **Key annotations**:
   - `@Test`, `@BeforeEach`, `@AfterEach`, `@BeforeAll`, `@AfterAll`
   - `@ParameterizedTest`, `@ValueSource`, `@CsvSource`: Run tests multiple times with different inputs.
   - `@DisplayName`, `@Nested`, `@Tag`: Test organization.
   - `@Disabled`: Skip a test.
3. **Assertions**: `assertEquals`, `assertThrows`, `assertNotNull`, `assertAll`.
4. **Test lifecycle**: Instantiate test classes per method (default) to ensure isolation.

## PART 3: Mockito
1. **What is Mockito?** A mocking framework for unit tests in Java.
2. `@Mock` vs `@InjectMocks` vs `@Spy`:
   - `@Mock`: Creates a dummy object.
   - `@InjectMocks`: Instantiates the object being tested and injects the mocks into it.
   - `@Spy`: Partially mocks a real object (calls real methods unless stubbed).
3. `when().thenReturn()` and `doReturn()`: Stubbing method behavior.
4. `verify()`: Verifies that a specific method was called on a mock with specific arguments.
5. `ArgumentCaptor`: Captures arguments passed to mocked methods for further assertions.
6. `@ExtendWith(MockitoExtension.class)`: Integrates Mockito with JUnit 5.
7. **Mocking void methods**: Use `doNothing().when(mock).method()`.
8. **Spy vs Mock difference**: Mock is fully fake; Spy is a real object where you can override specific methods.

## PART 4: Spring Boot Test Slices
1. `@SpringBootTest`: Loads the full application context (heavy).
2. `@WebMvcTest`: Loads only the web layer (Controllers) for faster API testing.
3. `@DataJpaTest`: Loads only JPA/database components.
4. `@MockBean`: Replaces a Spring bean with a Mockito mock inside the application context.
5. `TestRestTemplate` and `MockMvc`: Utilities for calling endpoints in tests.

## PART 5: Testcontainers
1. **What is Testcontainers?** A Java library that provides lightweight, throwaway instances of common databases inside Docker containers.
2. **Why**: Tests run against real databases (like PostgreSQL) instead of in-memory H2, eliminating discrepancies between test and production.
3. `@Testcontainers`: Marks a test class to be managed by Testcontainers.
4. `@Container`: Defines a container lifecycle (e.g., `PostgresqlContainer`).
5. `@DynamicPropertySource`: Dynamically maps container ports/URLs to Spring properties (e.g., `spring.datasource.url`).
6. **How used in Portfolio**: Spins up a real PostgreSQL container for integration testing the contact service layer.

## PART 6: Cypress
1. **What is Cypress?** A modern, JavaScript-based End-to-End testing framework that runs directly in the browser.
2. **Why Cypress over Selenium**:
   - Auto-waiting (no explicit waits or Thread.sleep).
   - Time-travel debugging with snapshots.
   - Runs in the real browser, not via WebDriver.
   - Easy API.
   - Built-in network intercepting.
3. **Core commands**: `cy.visit()`, `cy.get()`, `cy.click()`, `cy.type()`, `cy.intercept()`.
4. **Fixtures and test data**: Loading mock JSON data for tests.
5. **Intercepting API calls**: Mocking backend responses using `cy.intercept()`.
6. **Cypress vs Playwright**: Cypress is older and simpler for JS devs; Playwright is newer, faster, supports multi-language and multiple tabs.
7. **How used in Portfolio**:
   - Test contact form submission.
   - Test AI chat widget opens/closes.
   - Test resume download.
   - Test nav section scrolling.

## 8. Top 15 Interview Q&A on testing
1. **What is the testing pyramid?** A concept showing that you should have many unit tests, fewer integration tests, and very few E2E tests.
2. **What is the difference between unit and integration tests?** Unit tests isolate a single class; integration tests check how multiple components work together.
3. **What is Mockito and how do you use @Mock vs @InjectMocks?** Framework to create fake objects; @Mock creates the fake, @InjectMocks injects fakes into the class under test.
4. **What is Testcontainers and why use it over H2?** Spins up real Docker databases, ensuring compatibility with production (unlike H2 which has syntax differences).
5. **What is @WebMvcTest?** A Spring Boot slice test that only loads the controller layer.
6. **What is MockMvc?** A class used to simulate HTTP requests in Spring tests without starting an actual server.
7. **What is Cypress and why is it better than Selenium?** E2E testing tool that runs in the browser, offering auto-waiting and easier debugging.
8. **What is the difference between Cypress and Playwright?** Playwright supports multiple languages, native parallel execution, and multiple browser tabs, while Cypress is JS-only and single-tab.
9. **How do you test async code in JUnit?** Using libraries like Awaitility or `CompletableFuture.join()`.
10. **What is a test fixture?** A fixed state of a set of objects used as a baseline for running tests (e.g., mock JSON data).
11. **What is code coverage and what percentage is good?** The percentage of code executed by tests; usually >80% is considered good.
12. **What is TDD (Test-Driven Development)?** Writing tests before writing the actual implementation code (Red-Green-Refactor).
13. **How do you mock a Kafka producer in tests?** Use `@MockBean` for the `KafkaTemplate` or use Embedded Kafka.
14. **What is the difference between @Mock and @Spy?** @Mock is completely fake; @Spy is real but allows specific method stubbing.
15. **What is BDD (Behavior-Driven Development)?** An extension of TDD using natural language (Given-When-Then) via tools like Cucumber.
