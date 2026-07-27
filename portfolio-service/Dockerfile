# Multi-stage Docker build for portfolio-service using Java 21 Distroless image
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy root pom.xml and modules for monorepo dependency caching
COPY pom.xml .
COPY common-library ./common-library
COPY portfolio-service ./portfolio-service
COPY notification-service ./notification-service

# Build common-library and portfolio-service
RUN mvn clean package -pl common-library,portfolio-service -am -DskipTests

FROM gcr.io/distroless/java21-debian12:nonroot
WORKDIR /app

COPY --from=builder /app/portfolio-service/target/portfolio-service-1.0.0.jar app.jar

EXPOSE 8080
USER nonroot:nonroot

ENTRYPOINT ["java", "-jar", "app.jar"]
