# Project Documentation

This folder contains the planning and design documentation for the Flight Booking System backend.

- **Flight Booking System.pdf**  
  Defines the system domain, end-to-end booking workflow, Modular Monolith structure, actors and use cases, functional and non-functional requirements, business rules,     status enums, ERD relationships, and system context diagrams.

- **Full API Contract.pdf**  
  Defines the system endpoints, access roles and ownership rules, request and response examples, standard success, error, validation, and pagination formats, HTTP        status codes, JWT authentication requirements, and payment and refund callback contracts.

- **Implementation Backlog.pdf**  
  Defines the dependency-based backend implementation roadmap, project phases and features, related modules and endpoints, business rules, expected components,             implementation notes, and completion criteria.


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
