# Spring Security + JWT — Complete Interview Study Guide

## 1. What is Spring Security?
Spring Security is a framework that focuses on providing both authentication and authorization to Java applications.
- **Authentication**: Verifying who the user is.
- **Authorization**: Verifying what the user is allowed to do.

## 2. Core Concepts
- **SecurityFilterChain**: A chain of filters that Spring Security uses to intercept requests and apply security rules.
- **Principal**: The currently authenticated user.
- **GrantedAuthority**: A permission or role granted to the principal.
- **SecurityContext**: Holds the security details (including the `Authentication` object) of the current thread.
- **CSRF**: Cross-Site Request Forgery. Usually disabled for stateless REST APIs (where tokens are used instead of session cookies).
- **CORS**: Cross-Origin Resource Sharing. Must be configured if the frontend and backend run on different domains/ports.
- **Password encoding**: Never store plain text passwords. Use `BCryptPasswordEncoder` to hash passwords securely.
- **UserDetailsService**: Core interface to load user-specific data during authentication.

## 3. JWT Deep Dive
- **What is JWT?**: JSON Web Token. A compact, URL-safe means of representing claims to be transferred between two parties.
- **Structure**: `Header.Payload.Signature`
- **Payload (Claims)**: Contains statements about an entity. Common claims: `sub` (subject), `iat` (issued at), `exp` (expiration), `roles`.
- **Signing algorithm**: `HS256` (symmetric - same key to sign and verify) vs `RS256` (asymmetric - private key to sign, public key to verify).
- **Stateless flow**: User logs in -> Server verifies and issues JWT -> Client sends JWT in `Authorization: Bearer <token>` header -> Server verifies signature.
- **Access token vs Refresh token**: Access tokens are short-lived. Refresh tokens are long-lived and used to obtain new access tokens.
- **Expiry strategy**: Keep access tokens short-lived (e.g., 15 mins). If expired, client uses refresh token.

## 4. Implementation in Spring Boot
- **JWT filter**: Create a custom filter (e.g., `JwtAuthenticationFilter extends OncePerRequestFilter`) to intercept requests, extract JWT, validate it, and set the `SecurityContext`.
- **JwtUtil class**: A utility class to generate, validate, and extract claims from the JWT.
- **SecurityFilterChain configuration**: Use `http.csrf(AbstractHttpConfigurer::disable)`, `http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))`, and add the JWT filter before `UsernamePasswordAuthenticationFilter`.
- **Securing endpoints**: `http.authorizeHttpRequests(auth -> auth.requestMatchers("/api/admin/**").hasRole("ADMIN").anyRequest().permitAll())`.

## 5. Common Security Vulnerabilities
- **Broken Authentication**: Improper session management or credential stuffing.
- **Injection**: SQL injection (prevented by JPA/Hibernate), XSS (prevented by sanitizing inputs and React/Angular auto-escaping).
- **Security Misconfiguration**: Default passwords, exposed error traces.
- **Sensitive Data Exposure**: Not using HTTPS, logging sensitive data.

## 6. How used in Portfolio
- **Secured Analytics**: The Admin endpoint `/api/analytics/downloads` is protected requiring a valid JWT.
- **Public endpoints**: All other endpoints (viewing projects, sending messages) are public.

## 7. Top 15 Interview Q&A
1. **Difference between authentication and authorization?** Authentication is identity verification; authorization is checking permissions.
2. **What is Spring Security and how does it work?** It's a security framework that uses a chain of servlet filters to intercept requests and apply security rules.
3. **What is JWT and how is it structured?** JSON Web Token used for stateless authentication. Structured as Header, Payload, Signature.
4. **How does stateless authentication work with JWT?** The server does not store session state. The token contains all necessary user information and is verified via signature.
5. **What is stored in the JWT payload?** Claims like user ID (subject), roles, issued-at time, and expiration time.
6. **Access token vs refresh token?** Access token is for API access (short-lived). Refresh token is used to get a new access token (long-lived).
7. **Why disable CSRF for REST APIs?** Because REST APIs are typically stateless and use tokens (not session cookies), making them naturally immune to CSRF.
8. **How do you secure specific endpoints?** Using `authorizeHttpRequests` in the `SecurityFilterChain` bean.
9. **What is BCrypt?** A password hashing function that incorporates a salt to protect against rainbow table attacks.
10. **What is CORS?** A browser security feature restricting cross-origin HTTP requests. Handled in Spring via `CorsConfigurationSource`.
11. **What is the SecurityFilterChain?** The core component that defines which security filters are applied to which requests.
12. **What is OncePerRequestFilter?** A base class that guarantees the filter is executed exactly once per request dispatch.
13. **How do you handle token expiry?** Client catches 401 Unauthorized, uses refresh token to get a new access token, and retries.
14. **Difference between HS256 and RS256?** HS256 uses a single secret key; RS256 uses a private/public key pair.
15. **Common JWT vulnerabilities?** Weak signing keys, ignoring token expiration, accepting tokens signed with "none" algorithm.
