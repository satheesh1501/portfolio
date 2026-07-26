# Phase 1 Documentation and GitHub Push Guide

This document summarizes Phase 1 setup and provides step-by-step instructions for pushing your portfolio codebase to your GitHub account.

---

## Project Architecture Summary

- Frontend: React 18 + Vite + Glassmorphic CSS + Zustand + React Query + Framer Motion (/frontend)
- Portfolio Microservice: Java 21 + Spring Boot 3.2 + Security + JPA + Redis Cache + Kafka Producer (/portfolio-service)
- Notification Microservice: Java 21 + Spring Boot 3.2 + Kafka Consumer + Spring Mail (/notification-service)
- Infrastructure: Docker Compose with PostgreSQL (5432), Redis (6379), Apache Kafka (9092) (docker-compose.yml)
- DevSecOps Pipelines: GitHub Actions workflows for SonarCloud, Gitleaks, JUnit, Cypress, Docker, ECR, AWS (.github/workflows/)

---

## Step-by-Step Guide: Pushing to GitHub

### Step 1: Create a New Repository on GitHub
1. Open your browser and go to: https://github.com/new
2. Enter Repository Name: portfolio (or developer-portfolio)
3. Description: Microservices Developer Portfolio - Spring Boot 3, React 18, Apache Kafka, Redis, AWS
4. Visibility: Select Public
5. IMPORTANT: Do NOT check Add a README file, Add .gitignore, or Choose a license (we already generated them locally).
6. Click Create repository.

---

3## Step 2: Push Your Local Code via Terminal / PowerShell
Open PowerShell in c:\Users\psath\OneDrive\Documents\Portfolio and run these 5 commands:

git add .
git commit -m "feat: initial commit - Phase 1 monorepo scaffolding"
git branch -M main
git remote add origin https://github.com/satheesh1501/portfolio.git
git push -u origin main

---

## Step 3: Setting Up GitHub Repository Protection

Once pushed to GitHub:
1. Go to your repo Settings -> Branches.:2. Click Add branch protection rule.
3. Branch name pattern: main.
4. Check Require a pull request before merging.
5. Check Require status checks to pass before merging.
6. Click Save changes.

Your code is now securely published on GitHub!
