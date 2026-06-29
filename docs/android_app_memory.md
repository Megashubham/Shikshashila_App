# Shikshashila ERP - Android App Project Memory

## Project Overview
This project converts the Shikshashila ERP web application into a native Android app. The current website is a PHP/MySQL application with no existing REST API. The app will be built using Kotlin and Jetpack Compose.

## Architecture

**Backend (API Layer):**
- **Location:** `api/` directory in the root of the existing PHP project.
- **Language:** PHP 8+
- **Authentication:** Stateless JWT (JSON Web Tokens). No PHP sessions (`$_SESSION`) are used in the API layer.
- **Data Access:** Reuses existing SQL queries from the web application, wrapped in JSON responses.
- **Security:** HTTPS enforced, JWT for route protection, API rate limiting (future).

**Frontend (Android App):**
- **Language:** Kotlin
- **UI Toolkit:** Jetpack Compose (Material 3 Design)
- **Architecture Pattern:** MVVM (Model-View-ViewModel) with Clean Architecture principles.
- **Networking:** Retrofit2 + OkHttp
- **Dependency Injection:** Hilt
- **Local Database (Offline Cache):** Room
- **Async Programming:** Coroutines + Flow
- **Image Loading:** Coil

## Key Decisions (ADRs)

1.  **Platform Choice (2026-06-28):** Pure Kotlin (Android-only) selected over cross-platform (Flutter/React Native) for Phase 1. Priority is a high-performance, small-sized APK for Indian schools. iOS is a long-term future consideration, potentially via Kotlin Multiplatform (KMP).
2.  **API Strategy (2026-06-28):** A new dedicated `api/` directory will be built alongside the existing web app. It will connect to the same MySQL database, ensuring real-time synchronization between the web admin panel and the mobile app.
3.  **Authentication Migration (2026-06-28):** The web app relies heavily on PHP sessions. The API *must* use stateless JWTs. Mobile apps cannot reliably maintain cookie-based PHP sessions.
4.  **Phased Rollout:**
    *   **Phase 1:** API Foundation + Student & Teacher modules (highest priority).
    *   **Phase 2:** Admin & Fees modules.
    *   **Phase 3:** Remaining modules (HR, Transport, Library).

## Module Structure (Android)
- `core/` (Networking, DI, common utils)
- `auth/` (Login, OTP, JWT handling)
- `student/` (Dashboard, Attendance, Results, Fees)
- `teacher/` (Dashboard, Take Attendance, Assignments)

## API Endpoints (Planned - Phase 1)
- `POST /api/auth/login`: Authenticate and return JWT.
- `POST /api/auth/otp_verify`: Verify OTP (if 2FA enabled).
- `GET /api/student/dashboard`: Student summary data.
- `GET /api/teacher/dashboard`: Teacher summary data.

## Important Notes for Developers/Agents
- **DO NOT** use `$_SESSION` in any file within the `api/` directory. Use the JWT middleware for user identification.
- Always validate input on the API side, even if validated in the Android app.
- Ensure all API responses use the standard `json_success()` or `json_error()` helpers.
- The web app uses MD5 for passwords (legacy). The API login must also use MD5 to verify against the existing database.
- Existing code often interpolates strings in SQL (`query("SELECT * FROM table WHERE id='$id'")`). When writing API endpoints, use prepared statements to prevent SQL injection.
