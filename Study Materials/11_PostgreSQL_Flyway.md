# PostgreSQL + Flyway — Complete Interview Study Guide

## 1. What is PostgreSQL?
PostgreSQL is an advanced, open-source Object-Relational Database Management System (ORDBMS) known for its robust feature set, extensibility, and strict ACID compliance.

## 2. SQL Fundamentals (for interviews)
- **DDL (Data Definition Language)**: Commands to define database schemas (CREATE TABLE, ALTER TABLE, DROP TABLE).
- **DML (Data Manipulation Language)**: Commands to manipulate data (INSERT, UPDATE, DELETE, SELECT).
- **Joins**: 
  - `INNER JOIN`: Returns records with matching values in both tables.
  - `LEFT JOIN`: Returns all records from the left table, and matched records from the right table.
  - `RIGHT JOIN`: Returns all records from the right table, and matched records from the left table.
  - `FULL OUTER JOIN`: Returns all records when there is a match in either left or right table.
  - `CROSS JOIN`: Returns the Cartesian product of rows from the tables.
- **GROUP BY, HAVING, ORDER BY**: Used to group rows, filter grouped rows, and sort the result set.
- **Subqueries vs JOINs**: Subqueries are queries nested inside another query; JOINs combine tables. JOINs are generally faster and preferred.
- **Window functions**: Perform calculations across a set of table rows related to the current row (e.g., `ROW_NUMBER()`, `RANK()`, `PARTITION BY`).

## 3. PostgreSQL-Specific Features
- **UUID as primary key**: Using `gen_random_uuid()` for unique, non-sequential IDs.
- **JSONB column type**: Efficient storage and querying of JSON data.
- **Full-Text Search**: Built-in functionality using `tsvector`, `tsquery`, `to_tsvector`, and `plainto_tsquery`.
- **Array types**: Storing arrays directly in columns.
- **Sequences and SERIAL**: Used for auto-incrementing integers.
- **EXPLAIN ANALYZE**: Tool to understand how a query is executed and optimized.

## 4. Indexing
- **B-Tree index**: The default index type, suitable for most queries.
- **Hash index**: Useful only for simple equality comparisons.
- **GIN index**: Generalized Inverted Index, great for full-text search and JSONB.
- **When to add indexes**: For columns frequently used in WHERE clauses or JOIN conditions.
- **Composite indexes**: Indexes spanning multiple columns.
- **Index bloat**: Wasted space in indexes due to frequent updates/deletes.

## 5. Transactions and ACID
- **Atomicity, Consistency, Isolation, Durability**: The fundamental properties guaranteeing database reliability.
- **Transaction isolation levels**: READ UNCOMMITTED, READ COMMITTED, REPEATABLE READ, SERIALIZABLE.
- **Read phenomena**: Dirty read, Non-repeatable read, Phantom read.
- **@Transactional in Spring**: Declarative transaction management.

## 6. Database Normalization
- **1NF, 2NF, 3NF**: Progressive steps to eliminate data redundancy and ensure data dependencies make sense.
- **Denormalization**: Intentionally adding redundancy for read performance optimization.

## 7. Flyway Database Migrations
- **What is Flyway?**: Database version control tool.
- **Why use it?**: Ensures reproducible schemas, maintains version history, allows rollback.
- **Naming convention**: `V1__description.sql`, `V2__description.sql`.
- **Auto-run**: Flyway auto-runs on Spring Boot startup to apply pending migrations.
- **Migration table**: `flyway_schema_history` keeps track of applied scripts.
- **Baseline and repair commands**: Used for integrating existing databases and fixing failed migrations.

## 8. Portfolio schemas
- `V1__create_contact_messages.sql`
- `V2__create_resume_download_events.sql`
- `V3__create_projects.sql` (with full SQL structure for projects)

## 9. Top 15 Interview Q&A
1. **What is ACID and why does it matter?** Guarantees transaction reliability.
2. **What is the difference between SQL and NoSQL?** Relational vs non-relational, structured vs flexible schemas.
3. **Explain different types of JOINs with examples.** Inner (match), Left (all left), Right (all right), Full (all), Cross (Cartesian).
4. **What is an index and when should you use one?** A data structure that improves data retrieval speed. Use on frequently queried columns.
5. **What is a foreign key and why is it important?** Maintains referential integrity between tables.
6. **What are database transaction isolation levels?** Define how transactions become visible to each other.
7. **What is normalization? Explain 1NF, 2NF, 3NF.** Process of organizing data to reduce redundancy.
8. **What is Flyway and why use it over running SQL scripts manually?** Automated, version-controlled database migrations.
9. **What is a composite index?** An index on two or more columns.
10. **What is EXPLAIN ANALYZE?** A PostgreSQL command to profile query execution plans.
11. **What is a deadlock and how do you prevent it?** Two transactions waiting on each other. Prevent by consistent lock ordering.
12. **What is the difference between WHERE and HAVING?** WHERE filters rows before grouping; HAVING filters after grouping.
13. **What are window functions? Give an example.** Perform calculations across a set of rows (`ROW_NUMBER() OVER(PARTITION BY dept)`).
14. **How does PostgreSQL full-text search work?** Using `tsvector` to represent documents and `tsquery` to query them.
15. **What is connection pooling and why is HikariCP the default in Spring Boot?** Reuses database connections to reduce overhead; HikariCP is fast and lightweight.
