# Ticketing System UI Integration Guide (Angular LTS + Tailwind)

This file is for AI-assisted frontend implementation.
It captures the exact backend flow, endpoint sequence, request/response contracts, auth handling, and module-by-module integration plan.

## 1) Base Setup

- Base URL: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Auth type: JWT Bearer token
- API response wrapper:

```json
{
  "success": true,
  "message": "...",
  "data": {},
  "timestamp": "2026-04-17T00:00:00Z"
}
```

- Error response format:

```json
{
  "timestamp": "2026-04-17T00:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/..."
}
```

## 2) Security + Role Rules

### Public endpoints
- `/api/auth/register`
- `/api/auth/login`
- `/api/auth/refresh`
- Swagger docs endpoints

### Authenticated endpoints
- `/api/events/**`
- `/api/bookings/**`
- `/api/users/**`

### Admin-only endpoints
- `/api/admin/**`
- `/api/admin/events/**`

### JWT header for protected endpoints

```http
Authorization: Bearer <accessToken>
```

## 3) Complete End-to-End API Flow (Exact Sequence)

## Step 1: Register

### POST `/api/auth/register`
Request:

```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

Response `data` (`AuthResponse`):

```json
{
  "accessToken": "...",
  "refreshToken": "...",
  "user": {
    "id": 1,
    "email": "user@example.com",
    "role": "USER",
    "status": "ACTIVE"
  }
}
```

## Step 2: Login

### POST `/api/auth/login`
Request:

```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

Response is same shape as register (`AuthResponse`).

## Step 3: Token Refresh

### POST `/api/auth/refresh`
Request:

```json
{
  "refreshToken": "..."
}
```

Response `data` (`TokenResponse`):

```json
{
  "accessToken": "..."
}
```

## Step 4: Admin Creates Event (DRAFT)

### POST `/api/admin/events`
Headers: Admin token required

Request (`CreateEventRequest`):

```json
{
  "title": "Coldplay Live",
  "description": "Music concert",
  "category": "CONCERT",
  "city": "Mumbai",
  "venue": "DY Patil Stadium",
  "startTime": "2026-05-20T18:30:00Z",
  "endTime": "2026-05-20T22:00:00Z",
  "bookingStartTime": "2026-05-01T00:00:00Z",
  "bookingEndTime": "2026-05-19T23:59:00Z",
  "ticketPrice": 2999.00,
  "totalTickets": 5000
}
```

Response `data` is `EventDetailsResponse`.

## Step 5: Admin Publishes Event

### PATCH `/api/admin/events/{id}/publish`
Headers: Admin token required

No body.

## Step 6: User Browses Published Events

### GET `/api/events`
Headers: User token required

Query params (`EventSearchRequest`, all optional):
- `city`
- `keyword`
- `category` (`CONCERT|SPORTS|THEATER|OTHER`)
- `startDate` (ISO datetime)
- `endDate` (ISO datetime)
- `page`
- `size`
- `sort` (example: `startTime,asc`)

Response `data` (`EventListResponse`):

```json
{
  "content": [
    {
      "id": 101,
      "title": "Coldplay Live",
      "city": "Mumbai",
      "category": "CONCERT",
      "startTime": "2026-05-20T18:30:00Z",
      "ticketPrice": 2999.00,
      "availableTickets": 5000
    }
  ],
  "pageNumber": 0,
  "pageSize": 10,
  "totalElements": 1,
  "totalPages": 1,
  "last": true
}
```

### GET `/api/events/{id}`
Headers: User token required

Returns `EventDetailsResponse`.

## Step 7: User Creates Booking

### POST `/api/bookings`
Headers: User token required

Request (`CreateBookingRequest`):

```json
{
  "eventId": 101,
  "quantity": 2
}
```

Response `data` (`BookingResponse`):

```json
{
  "bookingNumber": "BK-20260417-AB12",
  "status": "CONFIRMED",
  "amount": 5998.00
}
```

Backend sequence (important):
1. validate user/event/window/duplicate
2. lock event row
3. reserve inventory
4. create booking + confirm
5. save booking in same transaction
6. trigger notification (non-blocking)
7. log audit (non-blocking)

## Step 8: User Views Booking(s)

### GET `/api/bookings`
Headers: User token required

Response `data`: list of `BookingSummaryResponse`

```json
[
  {
    "bookingNumber": "BK-20260417-AB12",
    "eventId": 101,
    "status": "CONFIRMED",
    "amount": 5998.00
  }
]
```

### GET `/api/bookings/{id}`
Headers: User token required

Response `data` (`BookingDetailsResponse`):

```json
{
  "bookingNumber": "BK-20260417-AB12",
  "eventId": 101,
  "userId": 1,
  "quantity": 2,
  "amount": 5998.00,
  "status": "CONFIRMED",
  "createdAt": "2026-04-17T10:00:00Z"
}
```

## Step 9: User Cancels Booking

### POST `/api/bookings/{id}/cancel`
Headers: User token required

No body.

Backend sequence:
1. verify ownership
2. verify status is `CONFIRMED`
3. release inventory
4. set status `CANCELLED`

## Step 10: User Profile

### GET `/api/users/me`
Headers: User token required

Response `data` (`UserProfileResponse`):

```json
{
  "id": 1,
  "email": "user@example.com",
  "role": "USER",
  "status": "ACTIVE",
  "createdAt": "2026-04-17T09:00:00Z"
}
```

### PUT `/api/users/me`
Headers: User token required

Request (`UpdateUserRequest`, partial):

```json
{
  "name": "Ashish",
  "password": "newStrongPass123"
}
```

## Admin user APIs

### GET `/api/users/{id}`
Headers: Admin token required

### PATCH `/api/users/{id}/status?status=ACTIVE|INACTIVE|BLOCKED`
Headers: Admin token required

## 4) Admin Analytics APIs

## GET `/api/admin/dashboard`
`data` (`DashboardResponse`):

```json
{
  "totalUsers": 12,
  "totalEvents": 5,
  "totalBookings": 30,
  "totalRevenue": 95000.00,
  "activeEvents": 3,
  "soldOutEvents": 1,
  "todayBookings": 6,
  "todayRevenue": 18000.00
}
```

## GET `/api/admin/events/stats`
`data`: list of `EventStatsResponse`

```json
[
  {
    "eventId": 101,
    "eventTitle": "Coldplay Live",
    "totalTickets": 5000,
    "availableTickets": 4200,
    "soldTickets": 800,
    "totalRevenue": 2399200.00,
    "bookingCount": 400
  }
]
```

## GET `/api/admin/events/{id}/stats`
`data`: one `EventStatsResponse`

## GET `/api/admin/bookings/stats`
`data` (`BookingStatsResponse`):

```json
{
  "totalBookings": 30,
  "confirmedBookings": 25,
  "cancelledBookings": 3,
  "failedBookings": 2,
  "totalRevenue": 95000.00
}
```

## 5) Scheduler + Background (No UI API)

No direct REST APIs for these:
- Reservation expiry scheduler
- Notification retry scheduler

UI should only reflect effects (booking status updates, etc.) via normal booking/admin APIs.

## 6) Notification + Audit (No Direct UI Endpoints Yet)

- Notification is triggered after successful booking and does not block booking flow.
- Audit is fire-and-forget and should not impact user experience.
- No dedicated controller endpoints currently for notification/audit logs.

## 7) Frontend Route and API Sequence Map (Recommended)

Suggested Angular page flow:

1. `/auth/register` -> `POST /api/auth/register`
2. `/auth/login` -> `POST /api/auth/login`
3. `/events` -> `GET /api/events`
4. `/events/:id` -> `GET /api/events/{id}`
5. `/bookings/new` -> `POST /api/bookings`
6. `/bookings` -> `GET /api/bookings`
7. `/bookings/:id` -> `GET /api/bookings/{id}`
8. cancel action -> `POST /api/bookings/{id}/cancel`
9. `/profile` -> `GET /api/users/me`, `PUT /api/users/me`
10. `/admin/dashboard` -> `GET /api/admin/dashboard`
11. `/admin/events` -> create/update/publish/cancel endpoints
12. `/admin/analytics` -> event stats + booking stats endpoints

## 8) Angular Integration Rules for AI

- Always parse `response.data` from `ApiResponse`.
- Always show `response.message` for success toast.
- On error, show `error.message` from `ErrorResponse`.
- Keep `accessToken` in a secure client-side storage strategy.
- Add HTTP interceptor for bearer token.
- On `401`, call refresh flow once, retry original request, else logout.
- Role guard:
  - `ADMIN` can access admin routes.
  - `USER` cannot access `/api/admin/**` routes.

## 9) Status Enums for UI Mappings

### User
- `ACTIVE`, `INACTIVE`, `BLOCKED`

### Event
- `DRAFT`, `PUBLISHED`, `CANCELLED`, `COMPLETED`

### Booking
- `PENDING`, `CONFIRMED`, `CANCELLED`, `FAILED`, `EXPIRED`

### Notification Log
- `SUCCESS`, `FAILED`

## 10) Validation Notes (Important for Form UX)

- Register password: minimum 8 chars
- Login email must be valid email format
- Booking quantity: `>= 1`
- Event ticket price: `> 0`
- Event total tickets: `> 0`
- Update profile password: minimum 8 chars

## 11) Quick API Checklist (Nothing Missing)

### Auth
- `POST /api/auth/register`
- `POST /api/auth/login`
- `POST /api/auth/refresh`
- `POST /api/auth/logout`

### Event (User)
- `GET /api/events`
- `GET /api/events/{id}`

### Event (Admin)
- `POST /api/admin/events`
- `PUT /api/admin/events/{id}`
- `PATCH /api/admin/events/{id}/publish`
- `PATCH /api/admin/events/{id}/cancel`

### Booking
- `POST /api/bookings`
- `GET /api/bookings`
- `GET /api/bookings/{id}`
- `POST /api/bookings/{id}/cancel`

### User
- `GET /api/users/me`
- `PUT /api/users/me`
- `GET /api/users/{id}` (admin)
- `PATCH /api/users/{id}/status` (admin)

### Admin Analytics
- `GET /api/admin/dashboard`
- `GET /api/admin/events/stats`
- `GET /api/admin/events/{id}/stats`
- `GET /api/admin/bookings/stats`

---

This document is designed for AI-assisted Angular generation so module flow, endpoint sequence, and dependencies remain aligned with backend behavior.

