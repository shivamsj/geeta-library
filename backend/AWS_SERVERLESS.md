# Geeta Library AWS Serverless Backend Plan

Recommended AWS setup for this project:

1. API layer: Amazon API Gateway.
2. Backend compute: AWS Lambda, or AWS App Runner if you want simpler Spring Boot hosting first.
3. Database: Amazon Aurora Serverless v2, PostgreSQL-compatible on this AWS free-plan account.
4. Secrets: AWS Secrets Manager or Lambda/App Runner environment variables.
5. Storage later: Amazon S3 for member photos, documents, QR codes and PDF reports.

## Why Aurora Serverless v2

The project already uses Spring Boot 3, Java 17 and Spring Data JPA. This AWS account blocks Aurora MySQL Serverless and currently allows Aurora PostgreSQL, so the AWS stack uses Aurora PostgreSQL while local development can still use MySQL.

## Local Development

Keep using local MySQL:

```powershell
mvn spring-boot:run
```

## AWS Profile

For AWS, run with the `aws` Spring profile and provide production environment variables:

```powershell
$env:SPRING_PROFILES_ACTIVE="aws"
$env:DB_URL="jdbc:postgresql://YOUR_AURORA_SERVERLESS_WRITER_ENDPOINT:5432/geeta_library?sslmode=require"
$env:DB_USERNAME="geeta_admin"
$env:DB_PASSWORD="your-db-password"
$env:JWT_SECRET="your-long-base64-secret"
mvn spring-boot:run
```

Use `backend/.env.aws.example` as the checklist for AWS environment variables.

## First AWS Deployment Path

This project now includes a first AWS SAM stack in `template.yaml`:

- Amazon API Gateway HTTP API
- AWS Lambda, Java 17
- Aurora Serverless v2, PostgreSQL-compatible
- Private VPC subnets and security groups
- Secrets Manager generated database password

Install AWS CLI and AWS SAM CLI, configure your AWS account, then deploy:

```powershell
$env:GEETA_AWS_JWT_SECRET="replace-with-a-long-production-secret"
.\backend\deploy-aws.ps1
```

The stack output will print `ApiBaseUrl`. Put that value into Android `gradle.properties` as `GEETA_API_BASE_URL`, then rebuild the APK.

To build the Lambda zip without deploying:

```powershell
powershell.exe -NoProfile -ExecutionPolicy Bypass -File .\backend\build-lambda-package.ps1
```

If your PowerShell allows local scripts, this shorter command also works:

```powershell
.\backend\build-lambda-package.ps1
```

## Android App Change After Deployment

Update the Android base URL to your AWS API URL:

```properties
GEETA_API_BASE_URL=https://your-api-id.execute-api.ap-south-1.amazonaws.com/prod/api/
```

or for App Runner:

```properties
GEETA_API_BASE_URL=https://your-app-runner-domain.awsapprunner.com/api/
```

Then rebuild the APK.
