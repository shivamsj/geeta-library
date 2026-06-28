$ErrorActionPreference = "Stop"

$backendDir = $PSScriptRoot
$lambdaDir = Join-Path $backendDir "target\lambda-package"
$zipPath = Join-Path $backendDir "target\geeta-library-lambda.zip"
$libDir = Join-Path $lambdaDir "lib"

Push-Location $backendDir
try {
    mvn -DskipTests "-Dspring-boot.repackage.skip=true" package
    if ($LASTEXITCODE -ne 0) { throw "Maven package failed." }

    if (Test-Path $lambdaDir) {
        Remove-Item -LiteralPath $lambdaDir -Recurse -Force
    }

    New-Item -ItemType Directory -Path $libDir -Force | Out-Null
    Copy-Item -Path "target\classes\*" -Destination $lambdaDir -Recurse -Force
    mvn -DskipTests dependency:copy-dependencies "-DincludeScope=runtime" "-DoutputDirectory=target\lambda-package\lib"
    if ($LASTEXITCODE -ne 0) { throw "Maven dependency copy failed." }

    if (Test-Path $zipPath) {
        Remove-Item -LiteralPath $zipPath -Force
    }

    Compress-Archive -Path (Join-Path $lambdaDir "*") -DestinationPath $zipPath -Force
    Write-Host "Lambda package created: $zipPath"
} finally {
    Pop-Location
}
