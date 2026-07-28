# AWS Cloud Deployment & Container Push Script for Portfolio Monorepo
param (
    [string]$AwsRegion = "ap-south-1",
    [string]$AwsAccountId = "540716654721"
)

$ErrorActionPreference = "Stop"

Write-Host "============================================================"
Write-Host " Portfolio Monorepo AWS Deployment Pipeline "
Write-Host "============================================================"

# Step 1: Build Java Monorepo Jars
Write-Host "`n[1/2] Compiling Java 21 Microservices Monorepo..."
Push-Location "$PSScriptRoot\.."
try {
    mvn clean package -DskipTests
    Write-Host "Microservices JAR artifacts built successfully!"
} finally {
    Pop-Location
}

# Step 2: Containerize Microservices if AWS Account ID provided
if ($AwsAccountId) {
    Write-Host "`n[2/2] Authenticating with Amazon ECR and Pushing Docker Images..."
    $EcrUri = "$AwsAccountId.dkr.ecr.$AwsRegion.amazonaws.com"
    
    aws ecr get-login-password --region $AwsRegion | docker login --username AWS --password-stdin $EcrUri
    
    # Portfolio Service
    Write-Host "Building portfolio-service container image..."
    docker build -t "$EcrUri/portfolio-service:latest" -f "$PSScriptRoot\..\portfolio-service\Dockerfile" "$PSScriptRoot\.."
    docker push "$EcrUri/portfolio-service:latest"
    Write-Host "portfolio-service pushed to ECR!"
    
    # Notification Service
    Write-Host "Building notification-service container image..."
    docker build -t "$EcrUri/notification-service:latest" -f "$PSScriptRoot\..\notification-service\Dockerfile" "$PSScriptRoot\.."
    docker push "$EcrUri/notification-service:latest"
    Write-Host "notification-service pushed to ECR!"
}

Write-Host "`n============================================================"
Write-Host " AWS Deployment Step Complete! "
Write-Host "============================================================"
