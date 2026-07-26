# High-Level Design (HLD) Study Guide

## 1. What is HLD?
**Definition:** High-Level Design (HLD) provides a comprehensive overview of the system architecture, explaining how various components of a system interact to solve a specific problem. It focuses on the macroscopic view, including system architecture, data flow, database selection, integration points, and overall scalability/reliability strategies.

**When is it done?** During the initial phase of the software development lifecycle (SDLC), immediately after requirement gathering and analysis, and before Low-Level Design (LLD).

**Who creates it?** Typically created by Software Architects, Principal/Lead Engineers, or Senior Developers.

## 2. The 12 Components of HLD

### Architecture Style
| Feature | Monolith | Microservices | Serverless |
| :--- | :--- | :--- | :--- |
| **Concept** | All-in-one unified unit | Independent, loosely coupled services | Cloud provider manages execution |
| **Deployment** | Entire app deployed at once | Independent deployments | Function-level deployments |
| **Scaling** | Scale entire app | Scale specific services | Auto-scales by request |
| **Complexity** | Simple initially | High (network, data consistency) | Low infrastructure management |
| **Best For** | Small teams, MVP | Large teams, complex domains | Event-driven, bursty traffic |

### Component/System Diagram
A visual representation showing the major modules, subsystems, external systems, and user interactions.

### Technology Stack Selection + Justification
| Layer | Technology | Justification |
| :--- | :--- | :--- |
| **Frontend** | React / Next.js | Component-based, rich ecosystem, SSR capabilities |
| **Backend** | Spring Boot / Java | Strongly typed, enterprise-ready, vast library support |
| **Database** | PostgreSQL | Relational integrity, ACID compliance, JSON support |
| **Message Broker**| Kafka | High throughput, scalable, durable messaging |

### Data Flow Diagram
Illustrates how data moves through the system, identifying data sources, processing nodes, and destinations.

### Database Design Strategy
- **SQL (Relational):** Use when data is highly structured, relationships are complex, and ACID properties are mandatory (e.g., financial transactions, user accounts).
- **NoSQL (Non-Relational):** Use when data is unstructured/semi-structured, schema flexibility is needed, or horizontal scalability is paramount (e.g., document stores, key-value stores for caching).

### Caching Strategy
- **Cache Aside:** App checks cache; if miss, fetches from DB and updates cache.
- **Write-Through:** App writes to cache, which synchronously writes to DB.
- **Write-Behind (Write-Back):** App writes to cache, which asynchronously writes to DB.
- **TTL (Time to Live):** Ensures stale data is evicted after a specific duration.

### API Design
| Feature | REST | GraphQL | gRPC |
| :--- | :--- | :--- | :--- |
| **Format** | JSON/XML | JSON | Protobuf (Binary) |
| **Protocol** | HTTP/1.1 | HTTP | HTTP/2 |
| **Fetching** | Multiple endpoints | Single endpoint, client specifies data | Strongly typed RPC |
| **Best For** | Public APIs, CRUD | Complex UI needs, minimizing over-fetching | Internal microservice communication |

### Security Design
- **JWT (JSON Web Tokens):** Stateless authentication.
- **RBAC:** Role-Based Access Control.
- **HTTPS/TLS:** Data encryption in transit.
- **XSS & SQL Injection Prevention:** Input validation, parameterized queries, sanitization.

### Non-Functional Requirements (NFRs)
| Requirement | Description |
| :--- | :--- |
| **Availability** | System uptime (e.g., 99.99%). |
| **Performance** | Latency and throughput (e.g., <100ms response time). |
| **Scalability** | Ability to handle increased load (Horizontal vs Vertical). |
| **Reliability** | System performs its intended function consistently. |
| **Security** | Protection against unauthorized access and attacks. |
| **Maintainability** | Ease of modifying or updating the system. |
| **Portability** | Ability to run across different environments. |
| **Observability** | Ability to understand system state through logs, metrics, traces. |

### Deployment Architecture
How the system is deployed (e.g., AWS/GCP, Kubernetes clusters, CI/CD pipelines, regions).

### Monitoring & Observability
- **Logs:** Discrete events (e.g., ELK stack, Splunk).
- **Metrics:** Aggregated numeric data (e.g., Prometheus, Grafana).
- **Traces:** Request flow across microservices (e.g., Jaeger, Zipkin).

### Disaster Recovery
- **RTO (Recovery Time Objective):** Maximum acceptable downtime.
- **RPO (Recovery Point Objective):** Maximum acceptable data loss (time-based).
- **MTTR (Mean Time to Recovery):** Average time taken to recover from a failure.
- **MTBF (Mean Time Between Failures):** Average time between system breakdowns.

## 3. CAP Theorem
**CAP Theorem** states that a distributed data store can only guarantee two out of the following three properties simultaneously:
- **Consistency (C):** Every read receives the most recent write or an error.
- **Availability (A):** Every request receives a non-error response, without guarantee it contains the most recent write.
- **Partition Tolerance (P):** The system continues to operate despite arbitrary message loss or failure of part of the network.

| Database | AP/CP/CA | Explanation |
| :--- | :--- | :--- |
| **PostgreSQL** | CA / CP | Typically CA (single node). If clustered synchronously, it's CP. |
| **Redis** | AP / CP | Redis Cluster is AP (eventual consistency). Redlock/Raft-based setups can be CP. |
| **Cassandra** | AP | Highly available and partition tolerant; offers eventual/tunable consistency. |

## 4. HLD for the Portfolio Project
*Interview Answer:* "For my portfolio project, I designed a monolithic application utilizing Spring Boot for the backend and React for the frontend, hosted on cloud infrastructure. I selected a monolithic architecture because the domain complexity is relatively low, making it easier to develop, test, and deploy as a single unit. The frontend communicates with the backend via RESTful APIs. For data persistence, I chose PostgreSQL due to its robust relational capabilities and ACID compliance, which ensures integrity for user messages and project data. To optimize performance and reduce database load, I implemented caching for frequently accessed static project data. Security is handled via Spring Security using JWT for stateless authentication. The application is containerized using Docker to ensure environment consistency and deployed behind an Nginx reverse proxy to handle SSL termination and load balancing."

## 5. Top 15 HLD Interview Questions & Answers

**1. HLD vs LLD difference?**
HLD focuses on system architecture, component interaction, database selection, and scalability (the "what" and "where"). LLD focuses on class diagrams, database schemas, algorithms, and design patterns (the "how").

**2. What is microservices architecture?**
An architectural style that structures an application as a collection of loosely coupled, independently deployable services organized around business capabilities.

**3. When NOT to use microservices?**
When the application is small, the team is small, the domain is not complex, or the overhead of managing distributed systems (networking, CI/CD, eventual consistency) outweighs the benefits.

**4. What is CAP theorem?**
In a distributed system, you can only have two of Consistency, Availability, and Partition Tolerance. Since network partitions (P) are inevitable, you must choose between Consistency (CP) and Availability (AP).

**5. Horizontal vs vertical scaling?**
Vertical (Scaling Up) means adding more power (CPU, RAM) to an existing machine. Horizontal (Scaling Out) means adding more machines to the pool of resources.

**6. What is a load balancer?**
A device or software that distributes network or application traffic across a cluster of servers to improve responsiveness and availability.

**7. What is a CDN and why CloudFront?**
A Content Delivery Network (CDN) is a geographically distributed group of servers that caches content close to end-users to reduce latency. CloudFront is AWS's CDN, integrating well with S3 and offering DDoS protection.

**8. Synchronous vs asynchronous communication?**
Synchronous communication blocks the caller until a response is received (e.g., REST, gRPC). Asynchronous allows the caller to continue processing without waiting for a response (e.g., Kafka, RabbitMQ).

**9. Why Kafka over REST for notifications?**
Kafka decouples the sender and receiver, handles traffic spikes by buffering messages, provides durability, and allows multiple independent consumers to process the same notification event asynchronously without slowing down the core service.

**10. What is circuit breaker pattern?**
A design pattern used to detect failures and encapsulate the logic of preventing a failure from constantly recurring (e.g., stopping requests to a failing downstream service and returning a fallback response).

**11. How to ensure high availability?**
By eliminating single points of failure through redundancy, using load balancers, deploying across multiple availability zones, implementing database replication, and utilizing CDNs.

**12. Message queue vs message broker?**
A message queue (e.g., SQS) is a simple FIFO queue for point-to-point communication. A message broker (e.g., Kafka, RabbitMQ) is more complex, supporting pub/sub, routing, and message transformation.

**13. What is event sourcing?**
A pattern where the state of a system is determined by a sequence of events rather than just the current state. Every change is appended to an event log.

**14. Stateless vs stateful service?**
A stateless service does not store client session data between requests; any server can handle any request. A stateful service remembers client data across requests, requiring sticky sessions or distributed caching.

**15. How would you scale to 1M requests/day?**
1M requests/day is ~12 requests/second. A single standard server can easily handle this. However, for redundancy, I would use a load balancer, two web servers, a managed database with a read replica, and a CDN for static assets.
