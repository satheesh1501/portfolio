# Lombok + MapStruct — Complete Interview Study Guide

## PART 1: Lombok

### 1. What is Lombok?
Project Lombok is a Java library that automatically plugs into your editor and build tools, acting as an annotation processor that eliminates Java boilerplate code (getters, setters, constructors, etc.) at compile time.

### 2. Core Annotations with Examples
- **`@Getter`, `@Setter`**: Automatically generates standard getter and setter methods for fields.
- **`@ToString`**: Generates a `toString()` method including class fields.
- **`@EqualsAndHashCode`**: Generates `equals()` and `hashCode()` methods based on the fields.
- **`@NoArgsConstructor`, `@AllArgsConstructor`, `@RequiredArgsConstructor`**: Generates constructors with no arguments, all arguments, or only required (final/non-null) arguments respectively.
- **`@Data`**: A shortcut annotation that bundles `@ToString`, `@EqualsAndHashCode`, `@Getter`, `@Setter`, and `@RequiredArgsConstructor`.
- **`@Builder`**: Implements the Builder pattern, allowing for fluent and readable object construction.
- **`@Builder.Default`**: Allows specifying a default value for a field when using `@Builder`.
- **`@Value`**: The immutable variant of `@Data`. Makes all fields `private` and `final` by default, and doesn't generate setters.
- **`@Slf4j`**: Automatically injects a `private static final org.slf4j.Logger log` instance into the class.
- **`@NonNull`**: Adds a null check on the parameter, throwing a `NullPointerException` if it is null.

### 3. Lombok with JPA Entities (Best Practices)
- **Don't use `@EqualsAndHashCode` with `@Entity`**: It can cause issues with Lazy fetching and proxy objects. Only the primary key (ID) should be used for equals/hashCode.
- **Don't use `@Data` on entities**: Because `@Data` includes `@EqualsAndHashCode` and `@ToString` (which can cause infinite recursion with bidirectional relationships).
- **Avoid `@Builder` on entities with JPA**: JPA needs a no-args constructor. If you use `@Builder`, you must manually add `@AllArgsConstructor` and `@NoArgsConstructor`.

### 4. Lombok Pros and Cons
- **Pros:** Massively reduces boilerplate, makes code more readable, saves time, easy to refactor fields.
- **Cons:** Hides what's happening under the hood, requires IDE plugins to work correctly, can be misused (e.g., `@Data` on JPA entities).

### 5. How used in Portfolio
Used extensively in all entity and DTO classes to remove boilerplate and ensure clean POJOs.

---

## PART 2: MapStruct

### 1. What is MapStruct?
MapStruct is a code generator that greatly simplifies the implementation of mappings between Java bean types based on a convention-over-configuration approach. It operates at compile-time.

### 2. Why MapStruct over alternatives?
- **Manual mapping:** Writing `dto.setName(entity.getName())` manually is verbose, tedious, and error-prone.
- **ModelMapper:** Uses reflection at runtime, making it slower and harder to debug when mappings fail. Less type-safe.
- **MapStruct:** Generates plain Java code at compile-time. It is incredibly fast (no reflection), type-safe, and if a mapping is wrong, it fails during the build, not at runtime. You can also inspect the generated implementation class easily.

### 3. Core Annotations
- **`@Mapper(componentModel = "spring")`**: Tells MapStruct to generate an implementation and mark it as a Spring `@Component` so it can be autowired.
- **`@Mapping(source = "field1", target = "field2")`**: Used when field names differ between the source and target objects.
- **`@Mappings`**: Used to group multiple `@Mapping` annotations (optional in newer Java versions).
- **`@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)`**: Useful for partial updates (PATCH requests) to ignore null fields during mapping.
- **`@AfterMapping`**: A method annotated with this will be called after the mapping is finished, useful for custom post-processing logic.

### 4. Generated Code Example
*Definition:*
```java
@Mapper(componentModel = "spring")
public interface ContactMapper {
    @Mapping(source = "phoneNumber", target = "phone")
    ContactMessage toEntity(ContactRequestDTO dto);
}
```
*Generated (simplified):*
```java
@Component
public class ContactMapperImpl implements ContactMapper {
    @Override
    public ContactMessage toEntity(ContactRequestDTO dto) {
        if (dto == null) return null;
        ContactMessage entity = new ContactMessage();
        entity.setPhone(dto.getPhoneNumber());
        // ... sets other matching fields
        return entity;
    }
}
```

### 5. How used in Portfolio
Used in `ContactMapper`, `ProjectMapper`, and others to seamlessly translate between incoming API DTOs and Database JPA Entities.

### 6. Top 10 Interview Q&A
1. **What is Lombok and what problem does it solve?** It's an annotation processor that generates boilerplate code like getters, setters, and constructors at compile time.
2. **What is the difference between `@Data` and `@Value`?** `@Data` creates a mutable class (with setters). `@Value` creates an immutable class (all fields final, no setters).
3. **What is the `@Builder` pattern and when would you use it?** It provides an API for constructing objects step-by-step. Useful for classes with many fields, especially optional ones.
4. **What is `@Slf4j`?** It injects a SLF4J logger instance (`log`) into the class.
5. **What are the pitfalls of using `@Data` with JPA entities?** It includes `@EqualsAndHashCode` and `@ToString` which evaluate all fields. This triggers lazy loading unintentionally and causes stack overflows on bidirectional relationships.
6. **What is MapStruct?** A compile-time code generator for mapping between Java beans.
7. **Why is MapStruct faster than ModelMapper?** MapStruct generates standard Java method calls at compile time. ModelMapper uses reflection at runtime, which is inherently slower.
8. **What is the difference between `@Mapping(source)` and `@Mapping(target)`?** `source` is the field name in the input object, `target` is the field name in the object being created/updated.
9. **How does MapStruct integrate with Spring?** By setting `componentModel = "spring"`, MapStruct annotates the generated implementation with `@Component`, allowing it to be `@Autowired`.
10. **What is an annotation processor?** A plugin for the Java compiler that scans and processes annotations at compile time, allowing for code generation before the final bytecode is produced.
