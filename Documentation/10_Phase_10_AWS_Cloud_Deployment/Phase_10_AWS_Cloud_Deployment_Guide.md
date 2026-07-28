# Phase 10: AWS Cloud Infrastructure & Deployment Preparation Guide

## 1. Overview & Cloud Architecture

This guide prepares the **Portfolio Event-Driven Microservices Platform** for production deployment on **AWS (Amazon Web Services)** and cloud-native serverless infrastructure.

```
                                  [ USER BROWSER ]
                                         |
                                         v
                         [ AWS CloudFront CDN (HTTPS / Edge) ]
                                         |
                                         +---> [ AWS S3 Bucket (React 18 Static Web Build) ]
                                         |
                                         v
                         [ AWS Application Load Balancer / App Runner ]
                                         |
                       +-----------------+-----------------+
                       |                                   |
                       v                                   v
         [ Portfolio Microservice (App Runner) ]  [ Notification Microservice (App Runner) ]
                       |                                   |
           +-----------+-----------+                       |
           |                       |                       |
           v                       v                       v
[ Neon PostgreSQL (DB) ]  [ Upstash Redis (Cache) ]  [ Upstash Kafka (Event Bus) ]
```

---

## 2. Infrastructure Component Allocation & Services

| Service Layer | Cloud Provider & Tool | Description | Cost Tier |
| :--- | :--- | :--- | :--- |
| **Frontend CDN** | **AWS CloudFront** | Edge-accelerated HTTPS distribution with global caching | Free Tier |
| **Frontend Storage** | **AWS S3** | Static website hosting bucket for React 18 production bundle | Free Tier |
| **Backend Compute** | **AWS App Runner** | Fully managed containerized service running Dockerized Java 21 microservices | Pay-as-you-go |
| **Container Registry**| **Amazon ECR** | Elastic Container Registry storing Docker images | Free Tier |
| **Relational DB** | **Neon PostgreSQL** | Serverless PostgreSQL 16 database with instant autoscaling | Free Tier |
| **Cache & Limiter** | **Upstash Redis** | Serverless low-latency Redis cache for rate limiting & sessions | Free Tier |
| **Event Bus** | **Upstash Kafka** | Serverless Kafka topic cluster for async notification streaming | Free Tier |

---

## 3. Environment Variables Configuration for AWS App Runner

When creating your App Runner service instances, pass these environment variables:

### Portfolio Service (`portfolio-service`):
```properties
SPRING_PROFILES_ACTIVE=prod
SERVER_PORT=8080
SPRING_DATASOURCE_URL=jdbc:postgresql://<NEON_HOST>:5432/portfolio_db?sslmode=require
SPRING_DATASOURCE_USERNAME=<NEON_USER>
SPRING_DATASOURCE_PASSWORD=<NEON_PASSWORD>
SPRING_DATA_REDIS_HOST=<UPSTASH_REDIS_HOST>
SPRING_DATA_REDIS_PORT=6379
SPRING_DATA_REDIS_PASSWORD=<UPSTASH_REDIS_PASSWORD>
SPRING_KAFKA_BOOTSTRAP_SERVERS=<UPSTASH_KAFKA_BROKER>:9092
```

### Notification Service (`notification-service`):
```properties
SPRING_PROFILES_ACTIVE=prod
SERVER_PORT=8081
SPRING_KAFKA_BOOTSTRAP_SERVERS=<UPSTASH_KAFKA_BROKER>:9092
SPRING_MAIL_HOST=smtp.gmail.com
SPRING_MAIL_PORT=587
SPRING_MAIL_USERNAME=psatheeshkumar89@gmail.com
SPRING_MAIL_PASSWORD=<GMAIL_APP_PASSWORD>
```

---

## 4. Deployment Execution Plan

1. **Database Setup**: Initialize tables on Neon PostgreSQL using Flyway migrations (`V1__init_schema.sql`).
2. **Docker Build & Push**: Push images to Amazon ECR:
   ```bash
   aws ecr get-login-password --region ap-south-1 | docker login --username AWS --password-stdin <ACCOUNT_ID>.dkr.ecr.ap-south-1.amazonaws.com
   docker tag portfolio-service:latest <ACCOUNT_ID>.dkr.ecr.ap-south-1.amazonaws.com/portfolio-service:latest
   docker push <ACCOUNT_ID>.dkr.ecr.ap-south-1.amazonaws.com/portfolio-service:latest
   ```
3. **Frontend Deployment**: Upload React `dist/` build to S3 bucket and invalidate CloudFront cache:
   ```bash
   aws s3 sync frontend/dist/ s3://satheesh-portfolio-bucket --delete
   aws cloudfront create-invalidation --distribution-id <DIST_ID> --paths "/*"
   ```
