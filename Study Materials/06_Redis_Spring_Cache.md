# Redis + Spring Cache — Complete Interview Study Guide

## 1. What is Redis?
Redis (Remote Dictionary Server) is an open-source, in-memory data structure store used as a database, cache, message broker, and streaming engine. It offers sub-millisecond latency.

## 2. Redis Data Structures
- **String**: Basic key-value pair. Good for caching JSON, HTML, or counters (`INCR`).
- **List**: Linked list of strings. Good for queues or recent items (`LPUSH`, `RPOP`).
- **Set**: Unordered collection of unique strings. Good for tags, unique visitors.
- **Sorted Set**: Similar to Sets but every element is associated with a score. Good for leaderboards.
- **Hash**: Maps between string fields and string values. Good for storing objects.
- **Stream**: Append-only log data structure. Good for event sourcing.

## 3. Cache Patterns
- **Cache Aside (Lazy Loading)**: Application checks cache; if miss, reads DB, writes to cache, returns data. Most common pattern.
- **Write-Through**: Application writes data to cache, cache writes to DB synchronously.
- **Write-Behind (Write-Back)**: Application writes to cache; cache writes to DB asynchronously.
- **Read-Through**: Application asks cache for data; cache fetches from DB if missing.

## 4. Spring Cache Annotations
- **@EnableCaching**: Placed on a `@Configuration` class to enable Spring's annotation-driven cache management.
- **@Cacheable**: Placed on a method. Spring checks if the result is cached; if not, executes the method and caches the result. (e.g., `@Cacheable(value = "projects", key = "#id")`)
- **@CacheEvict**: Removes data from the cache (e.g., `@CacheEvict(value = "projects", allEntries = true)`). Useful on update/delete operations.
- **@CachePut**: Updates the cache without interfering with the method execution.
- **@Caching**: Used when multiple annotations of the same type need to be applied.
- **CacheManager**: The core interface in Spring providing cache configuration and integration.

## 5. Redis for Rate Limiting
- **Pattern**: `INCR` + `EXPIRE` for basic IP-based rate limiting per minute/hour.
- **Atomic Operations**: Using Redis `MULTI`/`EXEC` or Lua scripts to ensure read-and-update operations are atomic.
- **Algorithms**: 
  - Fixed Window (simple, but has edge spikes).
  - Sliding Window (more accurate, uses Sorted Sets).

## 6. Redis Persistence
- **RDB (Redis Database Backup)**: Takes point-in-time snapshots of your dataset at specified intervals.
- **AOF (Append-Only File)**: Logs every write operation. More durable but larger file size.
- **No Persistence**: Pure in-memory cache, data lost on restart.

## 7. Redis vs Memcached
| Feature | Redis | Memcached |
|---|---|---|
| Data Types | Strings, Lists, Sets, Hashes, etc. | Strings (Key-Value) only |
| Persistence | RDB Snapshots, AOF | None (purely in-memory) |
| Replication | Master-Replica replication | None natively |
| Transactions | Supported (MULTI/EXEC) | Not supported |
| Use Case | Advanced caching, queues, leaderboards | Simple, highly concurrent string caching |

## 8. Upstash Redis
Used in the portfolio as a serverless Redis offering. It's cost-effective for low-traffic personal projects and provides a REST API.

## 9. How used in Portfolio
- **Caching**: `@Cacheable` applied to `getProjects()`. The first API call queries the DB and caches it. Subsequent calls serve from Redis.
- **Rate Limiting**: Protects the AI chatbot endpoint (e.g., max 20 messages per hour per IP) using the `INCR` + `EXPIRE` pattern to prevent API abuse.

## 10. Top 15 Interview Q&A
1. **What is Redis and when do you use it?** An in-memory key-value store used for caching, session management, real-time analytics, and rate limiting.
2. **What are Redis data structures?** Strings, Lists, Sets, Sorted Sets, Hashes, Streams, Bitmaps.
3. **Difference between cache-aside and write-through?** Cache-aside is managed by the application (lazy load); write-through is when writes go through the cache to the DB synchronously.
4. **What are Spring Cache annotations?** `@Cacheable`, `@CacheEvict`, `@CachePut`, `@Caching`, `@EnableCaching`.
5. **What is @Cacheable and how does it work?** Checks cache before method execution; if hit, returns cached value; if miss, executes method and caches result.
6. **What is cache invalidation?** Removing stale data from the cache. Achieved via TTL (Time-To-Live) or explicit eviction (like `@CacheEvict`).
7. **How would you implement rate limiting using Redis?** Using an IP as a key with `INCR` and setting an `EXPIRE` time, or using Sorted Sets for a sliding window.
8. **Difference between Redis and a database?** Redis stores data in RAM for extremely fast access but has limited storage and different persistence guarantees compared to disk-based RDBMS.
9. **What is Redis persistence?** Mechanisms to save memory state to disk (RDB snapshots or AOF append-only logs).
10. **What is a Redis TTL?** Time-To-Live; automatically expires/deletes a key after a specified duration.
11. **How do you handle cache stampede?** Occurs when a highly accessed key expires and many threads hit the DB simultaneously. Solved by pre-warming, locking, or randomized TTLs.
12. **Difference between Redis and Memcached?** Redis supports multiple data structures, persistence, and replication. Memcached is simpler, string-only, and lacks persistence.
13. **What is the INCR command?** Atomically increments the integer value of a key by one. Useful for counters and rate limiting.
14. **How does Spring Boot auto-configure Redis?** If `spring-boot-starter-data-redis` is on the classpath, it creates `RedisConnectionFactory` and `RedisTemplate` beans automatically.
15. **What happens if Redis goes down?** The application should gracefully fallback to the primary database (catch connection errors) or degrade gracefully without crashing.
