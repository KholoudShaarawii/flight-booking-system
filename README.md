# Project Documentation

This folder contains the planning and design documentation for the Flight Booking System backend.

- **Flight Booking System.pdf**  
  Defines the system domain, end-to-end booking workflow, Modular Monolith structure, actors and use cases, functional and non-functional requirements, business rules,     status enums, ERD relationships, and system context diagrams.

- **Full API Contract.pdf**  
  Defines the system endpoints, access roles and ownership rules, request and response examples, standard success, error, validation, and pagination formats, HTTP        status codes, JWT authentication requirements, and payment and refund callback contracts.
  
- **Phase API Test Reports**  
  Contains a separate API test report for each completed phase, including the tested endpoints, test scenarios, expected results, HTTP status codes, and Postman responses.


## Phase 1 — Identity and Access Management

Phase 1 provides the authentication and security foundation of the Flight Booking System.

### Completed Features

- Shared `User` model with roles and account statuses.
- Public customer registration.
- Secure login using email and password.
- JWT access-token generation and validation.
- Protection of authenticated endpoints.
- Current-user profile retrieval and update.
- Secure password change.
- Password hashing and standard API error handling.

### Phase 1 APIs

| Method | Endpoint |
|---|---|
| `POST` | `/api/auth/register` |
| `POST` | `/api/auth/login` |
| `GET` | `/api/auth/me` |
| `PATCH` | `/api/auth/me` |
| `PUT` | `/api/auth/change-password` |

API testing is documented in the Phase 1 API Test Report.


## Phase 2 — Administration (In Progress)

Phase 2 provides Super Admin operations for managing administrative data in the Flight Booking System.

### Completed Features

- Initial active `SUPER_ADMIN` account for development and protected API testing.
- Role-based protection of Airline Management endpoints.
- Airline creation with the default `ACTIVE` status.
- Partial airline information update.
- Airline deactivation using the `INACTIVE` status instead of permanent deletion.
- Duplicate airline-name handling.
- Paginated airline listing.
- Optional airline filtering by status and keyword.
- Standard exception handling for duplicate, not-found, and invalid update requests.

### Completed Phase 2 APIs

| Method | Endpoint |
|---|---|
| `POST` | `/api/airlines` |
| `PATCH` | `/api/airlines/{airlineId}` |
| `PATCH` | `/api/airlines/{airlineId}/deactivate` |
| `GET` | `/api/airlines` |

API testing for the completed Phase 2 features is documented in the Phase 2 API Test Report.

Phase 2 is still in progress. Airline Admin Management and Customer Account Management will be added next.
