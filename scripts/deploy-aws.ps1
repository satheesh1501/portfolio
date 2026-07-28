# ==============================================================================
# AWS Cloud Deployment & Container Push Script for Portfolio Monorepo
# Author: Satheesh Kumar P
# ==============================================================================

param (
    [string]$AwsRegion = "ap-south-1",
    [string]$AwsAccountId = "",
    [string]$S3BucketName = "satheesh-portfolio-web",
    [string]$CloudFrontDistId = ""
)

$ErrorActionPreference = "Stop"

Write-Host "============================================================" -ForegroundColor Cyan
Write-Host " 🚀 Portfolio Monorepo AWS Deployment Pipeline " -ForegroundColor Cyan
Write-Host "============================================================" -ForegroundColor Cyan

# Step 1: Build React 18 Production Bundle
Write-Host "`n[1/4] 📦 Building React 18 Frontend Production Bundle..." -ForegroundColor Yellow
Push-Location "$PSScriptRoot\..\frontend"
try {
    npm run build
    Write-Host "  ✅ Frontend production bundle created successfully in frontend/dist/" -ForegroundColor Green
} finally {
    Pop-Location
}

# Step 2: Build Java Monorepo Jars
Write-Host "`n[2/4] ☕ Compiling Java 21 Microservices Monorepo..." -ForegroundColor Yellow
Push-Location "$PSScriptRoot\.."
try {
    mvn clean package -DskipTests
    Write-Host "  ✅ Microservices JAR artifacts built successfully!" -ForegroundColor Green
} finally {
    Pop-Location
}

# Step 3: Containerize Microservices if AWS Account ID provided
if ($AwsAccountId -ne "") {
    Write-Host "`n[3/4] 🐳 Authenticating with Amazon ECR & Pushing Docker Images..." -ForegroundColor Yellow
    $EcrUri = "$AwsAccountId.dkr.ecr.$AwsRegion.amazonaws.com"
    
    aws ecr get-login-password --region $AwsRegion | docker login --username AWS --password-stdin $EcrUri
    
    # Portfolio Service
    Write-Host "  Building portfolio-service container image..." -ForegroundColor Gray
    docker build -t "$EcrUri/portfolio-service:latest" -f "$PSScriptRoot\..\portfolio-service\Dockerfile" "$PSScriptRoot\.."
    docker push "$EcrUri/portfolio-service:latest"
    Write-Host "  ✅ portfolio-service pushed to ECR!" -ForegroundColor Green
    
    # Notification Service
    Write-Host "  Building notification-service container image..." -ForegroundColor Gray
    docker build -t "$EcrUri/notification-service:latest" -f "$PSScriptRoot\..\notification-service\Dockerfile" "$PSScriptRoot\.."
    docker push "$EcrUri/notification-service:latest"
    Write-Host "  ✅ notification-service pushed to ECR!" -ForegroundColor Green
} else {
    Write-Host "`n[3/4] ℹ️ Skipping ECR image push (No -AwsAccountId parameter specified)." -ForegroundColor Yellow
    Write-Host "      Run: .\scripts\deploy-aws.ps1 -AwsAccountId <YOUR_ACCOUNT_ID>" -ForegroundColor Gray
}

# Step 4: Sync Frontend to AWS S3 & Invalidate CloudFront
if ($S3BucketName -ne "") {
    Write-Host "`n[4/4] ☁️ Deploying Frontend Build to AWS S3 Bucket ($S3BucketName)..." -ForegroundColor Yellow
    aws s3 sync "$PSScriptRoot\..\frontend\dist\" "s3://$S3BucketName" --delete
    Write-Host "  ✅ S3 Bucket synced!" -ForegroundColor Green
    
    if ($CloudFrontDistId -ne "") {
        Write-Host "  Invalidating CloudFront CDN cache ($CloudFrontDistId)..." -ForegroundColor Gray
        aws cloudfront create-invalidation --distribution-id $CloudFrontDistId --paths "/*"
        Write-Host "  ✅ CloudFront cache invalidated!" -ForegroundColor Green
    }
}

Write-Host "`n============================================================" -ForegroundColor Cyan
Write-Host " 🎉 AWS Deployment Step Complete! " -ForegroundColor Cyan
Write-Host "============================================================" -ForegroundColor Cyan
