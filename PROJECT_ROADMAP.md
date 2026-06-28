# Geeta Library Project Roadmap

## Current architecture

- Android: Kotlin, Jetpack Compose, MVVM, Retrofit, OkHttp and JWT token storage.
- Backend: Java 17, Spring Boot 3, Maven, MySQL, Spring Data JPA and Spring Security.
- Structure: modular monolith with Controller, DTO, Service and Repository layers per module.
- Implemented modules: Authentication, Member CRUD and Expense CRUD.

## Run locally

1. Start MySQL and ensure the configured user can create the `geeta_library` database.
2. Set `DB_USERNAME`, `DB_PASSWORD` and a strong `JWT_SECRET`.
3. In `backend`, run `mvn spring-boot:run`.
4. Android emulator uses `http://10.0.2.2:8080/api/`.
5. For a physical phone, set `GEETA_API_BASE_URL=http://<computer-lan-ip>:8080/api/` in root `gradle.properties`, then rebuild the APK.

## Next backend and UI modules

1. Plan Management: plan list, add/edit plan, fees and duration validation.
2. Seat Management: floor/shift filters, seat CRUD, allocation and transfer transaction.
3. Member Details: full dates, plan assignment, photo/documents and renewal history.
4. Collections and Invoices: payment entry, due calculation, receipt and invoice history.
5. Attendance: manual check-in, signed QR payload and duplicate attendance prevention.
6. Inquiry and Follow-up: inquiry pipeline, status and follow-up reminders.
7. Membership Freeze: freeze history and transactional expiry extension.
8. Dashboard API: server-calculated totals instead of multiple mobile-side queries.
9. Reports: paginated APIs, Excel/PDF generation and secure file download.
10. Notifications: Spring scheduled jobs plus an SMS/WhatsApp provider.

## Required engineering improvements

- Split the large `MainActivity.kt` into feature screen packages and use Navigation Compose typed routes.
- Add Hilt dependency injection and repository interfaces.
- Add Room caching only where offline operation is required.
- Replace `ddl-auto: update` with Flyway migrations before production.
- Add refresh tokens, password reset tokens plus SMTP, rate limiting and account lockout.
- Use HTTPS and remove Android cleartext traffic before release.
- Add pagination, filtering, audit fields and role-level authorization for STAFF and MEMBER.
