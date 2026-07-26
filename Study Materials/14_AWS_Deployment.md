# AWS Deployment (S3, CloudFront, App Runner, ECR, IAM) — Complete Interview Study Guide

## 1. AWS Overview
AWS provides cloud computing services on a pay-as-you-go model with a massive global infrastructure.

## 2. AWS S3 (Simple Storage Service)
- **Object storage**: Store any file, any size.
- **Bucket and object concepts**: Buckets are directories; objects are files.
- **Static website hosting**: Can serve static HTML/JS/CSS directly.
- **Bucket policies**: Used to grant public read access for static assets.
- **Versioning**: Keep multiple variants of an object.
- **Lifecycle rules**: Automatically transition objects to cheaper storage.
- **Storage classes**: Standard, Intelligent-Tiering, Glacier.

## 3. AWS CloudFront (CDN)
- **What is a CDN?**: Content Delivery Network; edge locations cache content globally.
- **CloudFront + S3 architecture**: Standard pattern for fast React app delivery.
- **Origin and Distribution**: S3 is the origin; CloudFront manages the distribution.
- **Cache invalidation**: Clearing cached files (e.g., on every deploy).
- **HTTPS via SSL/TLS**: Managed through AWS Certificate Manager (ACM).
- **Custom domain**: Configured via Route 53.
- **How it reduces latency**: Serves users from the closest edge location.

## 4. AWS ECR (Elastic Container Registry)
- **Private Docker image registry**: AWS equivalent of Docker Hub.
- **Repository lifecycle policies**: E.g., keep only the latest 5 images to save costs.
- **Image scanning**: Automated scanning for vulnerabilities.
- **Authentication**: Using `docker login` via the AWS CLI.

## 5. AWS App Runner
- **Fully managed container deployment**: PaaS for running web apps/APIs.
- **Integration**: Pulls images directly from ECR automatically.
- **Handles automatically**: Load balancing, auto-scaling, SSL termination, health checks.
- **Configuration**: CPU, memory, concurrency, environment variables.
- **Comparison**: Simpler than ECS/Fargate; abstracts away infrastructure unlike EC2.

## 6. AWS IAM (Identity and Access Management)
- **Core concepts**: Users, Groups, Roles, Policies.
- **Principle of least privilege**: Granting only the permissions necessary for a task.
- **IAM policy for GitHub Actions**: Needs only S3 write, ECR push, and App Runner deploy permissions.
- **Access keys**: Used for programmatic access (CLI/APIs).
- **Root account**: Avoid using it for daily tasks; secure it with MFA.

## 7. AWS Route 53 (DNS)
- **DNS record types**: A (IPv4), CNAME (canonical name), ALIAS (AWS specific mapping).
- **Connecting custom domain**: Mapping domains to CloudFront distributions.

## 8. Portfolio Deployment Flow
- **Step-by-step**: Create S3 bucket -> Setup CloudFront -> Push to ECR -> Deploy via App Runner -> Configure IAM user/role -> Set GitHub Secrets.
- **Environment variables**: Managed within App Runner configurations.
- **Cost breakdown**: Understanding AWS free tier and expected costs.

## 9. Top 12 Interview Q&A
1. **What is AWS S3 and what can you store in it?** Object storage service; can store images, backups, static websites, etc.
2. **How does CloudFront improve performance?** Caches content at edge locations worldwide.
3. **What is the difference between App Runner, ECS, and EC2?** App Runner is fully managed (PaaS); ECS manages containers (CaaS); EC2 manages VMs (IaaS).
4. **What is ECR?** AWS's private registry for Docker images.
5. **What is IAM and what is the principle of least privilege?** Access management; granting only the minimum permissions required.
6. **What is a CDN?** A distributed network of servers delivering content based on user location.
7. **How do you deploy a Spring Boot app to AWS?** Dockerize it, push to ECR, run via App Runner/ECS.
8. **How do you host a React app on AWS?** Build static files, upload to S3, serve via CloudFront.
9. **What is cache invalidation in CloudFront?** Forcing CloudFront to clear cached files and fetch fresh ones from the origin.
10. **What are AWS availability zones and regions?** Regions are physical locations (e.g., us-east-1); AZs are isolated data centers within a region.
11. **How do you manage secrets in AWS?** Secrets Manager, Systems Manager Parameter Store, or App Runner environment variables.
12. **What is the difference between S3 and EBS?** S3 is object storage (internet accessible); EBS is block storage (attached to EC2).
