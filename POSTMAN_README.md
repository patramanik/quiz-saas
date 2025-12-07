# Postman API Testing Guide for Quiz SaaS

This guide provides detailed instructions and examples on how to test the Quiz SaaS API using Postman.

## Prerequisites

- [Postman](https://www.postman.com/downloads/) installed.
- Valid Database (`quizsaas`) and Spring Boot app running at `http://localhost:8080`.

## Environment Setup

1.  **Create Environment**: `Quiz SaaS Local`
2.  **Add Variables**:
    *   `baseUrl`: `http://localhost:8080/api`
    *   `jwtToken`: (Leave blank, will be auto-populated)

## Authentication (Global)

Configure your Collection to use `Bearer Token` auth with `{{jwtToken}}` as the token value. This allows you to skip manually pasting tokens for every request.

---

## Endpoint Examples

### 1. Auth Controller (`/auth`)

#### Login
*   **POST** `{{baseUrl}}/auth/login`
*   **Body** (JSON):
    ```json
    {
        "email": "superadmin@quizsaas.com",
        "password": "password123"
    }
    ```
*   **Test Script**:
    ```javascript
    var jsonData = pm.response.json();
    if (jsonData.data && jsonData.data.token) {
        pm.environment.set("jwtToken", jsonData.data.token);
    }
    ```

#### Register (Method: POST)
*   **URL**: `{{baseUrl}}/auth/register`
*   **Body** (JSON):
    ```json
    {
        "email": "teacher@school.com",
        "password": "securePass123!",
        "fullName": "John Doe",
        "role": "TEACHER",
        "organizationId": 1
    }
    ```
    *Note: `organizationId` is required for Tenant users (Student/Teacher).*

---

### 2. Super Admin Controller (`/super-admin`)

*   **Requires**: `SUPER_ADMIN` role token.

#### Get All Organizations
*   **GET** `{{baseUrl}}/super-admin/organizations`

#### Block/Unblock Organization
*   **PUT** `{{baseUrl}}/super-admin/organizations/1/block`
*   **PUT** `{{baseUrl}}/super-admin/organizations/1/unblock`

---

### 3. Org Admin Controller (`/org-admin`)

*   **Requires**: `ORG_ADMIN` role token.

#### Create Organization
*   **POST** `{{baseUrl}}/org-admin/create-org`
*   **Body** (JSON):
    ```json
    {
        "name": "Springfield High School",
        "subscriptionPlan": "PREMIUM"
    }
    ```

#### Create Organization (Free Plan)
*   **POST** `{{baseUrl}}/org-admin/create-org`
*   **Description**: Creates an organization with `ACTIVE` status immediately (skips payment).
*   **Body** (JSON):
    ```json
    {
        "name": "Community Learning Center",
        "subscriptionPlan": "FREE"
    }
    ```

#### Initialize Payment
*   **POST** `{{baseUrl}}/org-admin/payment/initialize`
*   **Body** (JSON):
    ```json
    {
        "organizationId": 1,
        "amount": 999.00,
        "currency": "USD"
    }
    ```

#### Generate API Key
*   **POST** `{{baseUrl}}/org-admin/api-keys/generate`
*   *(No Body required)*

---

### 4. Teacher Controller (`/teacher`)

*   **Requires**: `TEACHER` role token.

#### Create Quiz
*   **POST** `{{baseUrl}}/teacher/quizzes`
*   **Body** (JSON):
    ```json
    {
        "title": "Math Mid-Term 2024",
        "subjectId": 101,
        "classroomId": 202,
        "durationMinutes": 60,
        "startTime": "2024-12-10T09:00:00",
        "endTime": "2024-12-10T12:00:00"
    }
    ```

---

### 5. Student Controller (`/student`)

*   **Requires**: `STUDENT` role token.

#### View Available Quizzes
*   **GET** `{{baseUrl}}/student/available-quizzes`

#### Attempt Quiz
*   **POST** `{{baseUrl}}/student/quizzes/5/attempt`
*   *(Body may vary based on implementation, typically empty to start or contains specific attempt metadata)*

---

## Status Codes to Watch
- **200 OK**: Success.
- **400 Bad Request**: Invalid JSON or missing fields.
- **401 Unauthorized**: Token missing or invalid.
- **403 Forbidden**: Token valid but Role not authorized.
