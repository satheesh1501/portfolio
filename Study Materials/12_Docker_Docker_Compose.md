# Docker + Docker Compose — Complete Interview Study Guide

## 1. What is Docker?
Docker is a containerization platform that allows developers to package applications with their dependencies into isolated environments called containers.

## 2. Containers vs Virtual Machines
- **Container**: Shares host OS kernel, lightweight, starts in seconds.
- **VM**: Full OS copy, heavy, slower startup.

## 3. Core Docker Concepts
- **Image**: A blueprint/template for creating a container.
- **Container**: A running instance of an image.
- **Dockerfile**: A script containing instructions to build an image.
- **Docker Hub / ECR**: Image registries for storing and sharing images.
- **Layer caching**: Docker caches layers to speed up builds.
- **Volumes**: Used for persistent storage independent of container lifecycle.
- **Networks**: Bridge, host, overlay networks for container communication.
- **Port mapping**: `-p host:container` to expose container ports to the host.

## 4. Dockerfile Best Practices
- **Multi-stage build**: Builder stage for compilation + runtime stage for execution.
- **Use official base images**: E.g., `eclipse-temurin` for Java.
- **Minimize layers**: Combine RUN commands.
- **Use .dockerignore**: Prevent copying unnecessary files (like `.git` or `node_modules`).
- **Run as non-root user**: For better security.
- **COPY vs ADD**: COPY is preferred for simple file transfers; ADD supports URLs and tar extraction.
- **CMD vs ENTRYPOINT**: CMD provides default arguments; ENTRYPOINT sets the main executable.

## 5. Multi-stage Dockerfile for Spring Boot
```dockerfile
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

## 6. Docker Compose
- **What it is**: Multi-container orchestration tool for local development.
- **docker-compose.yml structure**: Defines services, volumes, and networks.
- **Common commands**: `docker-compose up -d`, `down`, `logs`, `exec`.
- **depends_on and healthcheck**: Control startup order and ensure services are ready.
- **Environment variables**: Managed via `.env` files.

## 7. Portfolio docker-compose.yml
Typical services include:
- PostgreSQL service
- Redis service
- Kafka + Zookeeper service
- Spring Boot services (or pointing to local dev)

## 8. Docker vs Kubernetes
- **Docker**: Single host container runtime.
- **Docker Compose**: Multi-container management on a single host.
- **Kubernetes**: Multi-host container orchestration (production at scale).
- **Why App Runner instead of Kubernetes for portfolio**: App Runner is fully managed, simpler, and cost-effective for portfolio-scale apps.

## 9. Top 15 Interview Q&A
1. **What is Docker and why do we use it?** Containerization for consistent environments across dev and prod.
2. **What is the difference between a container and a VM?** Containers share the OS kernel, VMs have full OS copies.
3. **What is a Docker image vs a container?** Image is a blueprint; container is the running instance.
4. **What is a Dockerfile?** Instructions to build a Docker image.
5. **What is a multi-stage Docker build?** Using multiple FROM statements to separate build tools from the final runtime image.
6. **What is Docker Compose and when do you use it?** Defining and running multi-container Docker applications, mostly for local dev.
7. **What is the difference between COPY and ADD in Dockerfile?** COPY is simple copy; ADD can extract tars and fetch URLs.
8. **What is the difference between CMD and ENTRYPOINT?** ENTRYPOINT is the executable; CMD provides default arguments.
9. **What are Docker volumes?** Persistent storage for containers.
10. **What is a Docker network?** Allows containers to communicate with each other.
11. **What is layer caching in Docker?** Reusing previously built layers if they haven't changed to speed up builds.
12. **How do you make a Docker image smaller?** Use alpine/slim base images, multi-stage builds, and `.dockerignore`.
13. **What is .dockerignore?** Specifies files to exclude from the Docker context.
14. **What is the difference between Docker and Kubernetes?** Docker runs containers; Kubernetes orchestrates them across clusters.
15. **How do you pass environment variables to a Docker container?** Using `-e` flag, `env_file`, or in `docker-compose.yml`.
