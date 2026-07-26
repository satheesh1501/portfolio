# Apache Kafka — Complete Interview Study Guide

## 1. What is Kafka?
Apache Kafka is a distributed event streaming platform capable of handling trillions of events a day. It is designed for high-throughput, fault-tolerance, and scalability. It is not a traditional message queue; it acts more like a distributed commit log.

## 2. Core Concepts
- **Event / Message / Record**: A piece of data (key, value, timestamp) sent to Kafka.
- **Producer**: An application that publishes events to a Kafka topic.
- **Consumer**: An application that reads events from a Kafka topic.
- **Topic**: A category or channel to which records are published.
- **Partition**: A topic is split into partitions for horizontal scalability and parallel processing.
- **Offset**: A unique, sequential ID assigned to each message within a partition.
- **Consumer Group**: A set of consumers sharing the same group ID. Each partition is consumed by exactly one consumer in the group, allowing load balancing.
- **Broker**: A single Kafka server. A Kafka cluster consists of multiple brokers.
- **ZooKeeper / KRaft**: Used for managing cluster metadata and controller election. KRaft (Kafka Raft) replaces ZooKeeper in newer versions.
- **Replication Factor**: The number of copies of a partition across the cluster to ensure fault tolerance.

## 3. Message Delivery Semantics
- **At-Most-Once**: Messages may be lost but are never redelivered (fire and forget).
- **At-Least-Once**: Messages are never lost but may be redelivered (duplicates possible). This is the default/most common.
- **Exactly-Once**: Messages are delivered exactly once. Requires idempotent producers and transactional APIs.

## 4. Spring Kafka
- **KafkaTemplate**: A Spring abstraction used by producers to send messages to Kafka.
- **@KafkaListener**: Annotation used on methods to mark them as consumers.
- **@EnableKafka**: Enables detection of `@KafkaListener` annotations.
- **Dead Letter Topic (DLT)**: A special topic to which messages are sent if they cannot be processed successfully after retries.
- **Configuration**: Properties are defined in `application.yml` under `spring.kafka`.

## 5. Kafka vs RabbitMQ
| Feature | Kafka | RabbitMQ |
|---|---|---|
| Architecture | Distributed Commit Log | Message Broker (Smart broker, dumb consumer) |
| Performance | High throughput (Millions/sec) | Moderate throughput (Tens of thousands/sec) |
| Data Retention | Retains messages based on time/size | Deletes message once acknowledged |
| Routing | Publisher to Topic | Publisher to Exchange to Queue (complex routing) |
| Best For | Event sourcing, analytics, stream processing | Complex routing, traditional task queues |

## 6. Kafka vs REST for async tasks
REST is synchronous and tightly couples the caller and callee. If the downstream service is down, the request fails. Kafka decouples services; the producer simply writes to Kafka, and the consumer reads when it is ready.

## 7. How used in Portfolio
- A `ContactSubmittedEvent` is published by the `portfolio-service` when a user submits a contact form.
- The `notification-service` uses `@KafkaListener` to consume this event and send an email notification.
- **Why Kafka**: Decoupling, resilience (if email service is down, messages are queued), and future scalability.

## 8. Top 15 Interview Q&A
1. **What is Kafka and how is it different from a traditional message queue?** Kafka is a distributed append-only log that retains messages for a configured time, whereas traditional queues delete messages once consumed.
2. **What is a Kafka topic? What are partitions?** A topic is a logical channel for messages. Partitions are physical splits of a topic enabling parallel consumption.
3. **What is a consumer group?** A group of consumers that cooperatively consume a topic. Partitions are divided among the members.
4. **What is an offset?** A sequential ID for a message in a partition, indicating a consumer's read position.
5. **Difference between at-least-once and exactly-once?** At-least-once guarantees delivery but risks duplicates. Exactly-once ensures no duplicates and no loss.
6. **How do you configure Kafka in Spring Boot?** Using `application.yml` for broker addresses, serializers, and defining `KafkaTemplate`/`@KafkaListener`.
7. **What is @KafkaListener?** Spring annotation to consume messages from specified topics.
8. **What is a Dead Letter Topic?** A topic for messages that consistently fail processing, preventing consumer blockage.
9. **Why choose Kafka over a simple REST call?** Decoupling, fault tolerance, and handling bursts of traffic asynchronously.
10. **What happens if a Kafka consumer goes down?** The cluster triggers a rebalance, and its partitions are reassigned to other consumers in the group.
11. **What is replication factor?** Number of copies of data across brokers to prevent data loss on broker failure.
12. **How do you ensure message ordering?** By using the same key for messages that must be ordered; Kafka guarantees order within a single partition.
13. **Difference between Kafka and RabbitMQ?** Kafka is log-based and replayable; RabbitMQ is queue-based and deletes after ack.
14. **What is KRaft?** The new consensus protocol replacing ZooKeeper to simplify architecture and improve scalability.
15. **How would you scale Kafka for high throughput?** Add more brokers, increase topic partitions, and add more consumers to the consumer group.
