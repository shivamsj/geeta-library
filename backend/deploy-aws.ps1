param(
    [string]$StackName = "geeta-library-prod",
    [string]$Region = "ap-south-1",
    [string]$StageName = "prod",
    [string]$DatabaseClusterId = "geeta-library-prod-db",
    [string]$DatabaseName = "postgres",
    [string]$DatabaseUsername = "postgres"
)

$ErrorActionPreference = "Stop"

if (-not (Get-Command sam -ErrorAction SilentlyContinue)) {
    throw "AWS SAM CLI is not installed. Install it before deploying."
}

if ([string]::IsNullOrWhiteSpace($env:GEETA_AWS_JWT_SECRET)) {
    throw "Set GEETA_AWS_JWT_SECRET to a long production JWT secret before deploying."
}

Push-Location (Join-Path $PSScriptRoot "..")
try {
    $database = aws rds describe-db-clusters `
        --db-cluster-identifier $DatabaseClusterId `
        --region $Region `
        --query "DBClusters[0].{Endpoint:Endpoint,ResourceId:DbClusterResourceId,Port:Port}" `
        --output json | ConvertFrom-Json

    if ([string]::IsNullOrWhiteSpace($database.Endpoint) -or [string]::IsNullOrWhiteSpace($database.ResourceId)) {
        throw "Database cluster $DatabaseClusterId is not ready."
    }

    & (Join-Path $PSScriptRoot "build-lambda-package.ps1")
    sam validate --template-file template.yaml --region $Region
    sam deploy `
        --stack-name $StackName `
        --region $Region `
        --capabilities CAPABILITY_IAM `
        --resolve-s3 `
        --no-confirm-changeset `
        --no-fail-on-empty-changeset `
        --parameter-overrides `
            StageName=$StageName `
            DatabaseHost=$($database.Endpoint) `
            DatabasePort=$($database.Port) `
            DatabaseName=$DatabaseName `
            DatabaseUsername=$DatabaseUsername `
            DatabaseResourceId=$($database.ResourceId) `
            JwtSecret=$env:GEETA_AWS_JWT_SECRET
} finally {
    Pop-Location
}
