# Project Documentation

This folder contains the planning and design documentation for the Flight Booking System backend.

- **Flight Booking System.pdf**  
  Defines the system domain, end-to-end booking workflow, Modular Monolith structure, actors and use cases, functional and non-functional requirements, business rules,     status enums, ERD relationships, and system context diagrams.

- **Full API Contract.pdf**  
  Defines the system endpoints, access roles and ownership rules, request and response examples, standard success, error, validation, and pagination formats, HTTP        status codes, JWT authentication requirements, and payment and refund callback contracts.

- **Implementation Backlog.pdf**  
  Defines the dependency-based backend implementation roadmap, project phases and features, related modules and endpoints, business rules, expected components,             implementation notes, and completion criteria.


## Current Progress

The backend is currently implemented and tested through **F1.3 Login**.

Completed:

- Project foundation and Modular Monolith structure.
- Oracle Database integration.
- Standard API responses and global exception handling.
- **F1.1 User Model**
- **F1.2 Register Customer**
  - `POST /api/auth/register`
- **F1.3 Login**
  - `POST /api/auth/login`
  - Email and password authentication.
  - Account status validation.
  - JWT access token generation.
  - Safe user data returned without password information.
  - `401 Unauthorized` for invalid credentials.

