# Geeta Library Backend

Spring Boot 3 modular monolith using Java 17, Maven, MySQL, Spring Data JPA, Spring Security and JWT.

## Run

1. Start MySQL.
2. Set `DB_USERNAME`, `DB_PASSWORD` and a production `JWT_SECRET` environment variable.
3. Run `mvn spring-boot:run` from this directory.
4. Health endpoint: `GET http://localhost:8080/api/health`.

Android emulator uses `http://10.0.2.2:8080/api/`. For a physical phone, set `GEETA_API_BASE_URL` in the root `gradle.properties` to the computer's LAN IP, for example:

```properties
GEETA_API_BASE_URL=http://192.168.1.10:8080/api/
```

Never commit production database passwords or JWT secrets.

## AWS Serverless Direction

Use `application-aws.yml` with Aurora Serverless v2 MySQL-compatible database. See `AWS_SERVERLESS.md` and `.env.aws.example` for the recommended AWS setup and environment variables.

## Implemented APIs

- `POST /api/auth/register`
- `POST /api/auth/login`
- `POST /api/auth/forgot-password`
- `GET/POST /api/members`
- `PUT/DELETE /api/members/{id}`
- `GET/POST /api/expenses`
- `PUT/DELETE /api/expenses/{id}`

Member and expense APIs require `Authorization: Bearer <jwt>`. Records are scoped to the authenticated admin.
