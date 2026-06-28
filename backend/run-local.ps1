$ErrorActionPreference = "Stop"

$env:DB_USERNAME = [Environment]::GetEnvironmentVariable("GEETA_DB_USERNAME", "User")
$env:DB_PASSWORD = [Environment]::GetEnvironmentVariable("GEETA_DB_PASSWORD", "User")
$env:JWT_SECRET = [Environment]::GetEnvironmentVariable("GEETA_JWT_SECRET", "User")

if ([string]::IsNullOrWhiteSpace($env:DB_USERNAME) -or
    [string]::IsNullOrWhiteSpace($env:DB_PASSWORD) -or
    [string]::IsNullOrWhiteSpace($env:JWT_SECRET)) {
    throw "Geeta Library backend environment variables are not configured."
}

$jar = Join-Path $PSScriptRoot "target\geeta-library-backend-1.0.0.jar"
if (-not (Test-Path $jar)) {
    throw "Backend JAR not found. Run 'mvn -DskipTests package' first."
}

& java -jar $jar
