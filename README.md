![title](C:\Users\Ashish jha\Desktop\ExtraProject\ticketing-system\src\main\resources\static\title.png)

## Ticketing System (Flash Sale / Event Booking API)

This project is a backend system designed to simulate a real-world ticket booking platform where users can browse events and book limited tickets during high-demand scenarios such as flash sales or concert releases.

The main goal of this project is to handle concurrent booking requests safely while maintaining data consistency and performance.

---

## Why I Built This

In real systems like concert ticketing or flash sales, multiple users try to book tickets at the same time. This often leads to issues like overselling, race conditions, and performance bottlenecks.

This project is built to understand and solve:

- how to handle high concurrency in backend systems
- how to prevent double booking and overselling
- how to design scalable and maintainable backend architecture
- how caching and rate limiting improve performance

---

## Key Features

- User authentication and authorization (JWT-based)
- Event creation and management (admin)
- Event browsing with filtering and pagination
- Ticket booking with concurrency control
- Inventory management to prevent overselling
- Redis-based caching for high-read endpoints
- Rate limiting to prevent abuse
- Booking lifecycle management
- Admin dashboard APIs for insights
- Modular architecture for better scalability

---

## Tech Stack

### Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA

### Database

- PostgreSQL

### Caching & Performance

- Redis
- Spring Cache

### Tools

- Maven
- Lombok
- Swagger / OpenAPI
- IntelliJ IDEA

---

## Architecture & Design

The project follows a **modular monolith architecture**.

Instead of grouping code by layers globally, the project is divided into feature-based modules like:

- auth
- user
- event
- booking
- inventory
- notification
- admin

Each module contains its own:

- controller
- service
- repository
- entity
- DTOs

This improves:

- maintainability
- readability
- scalability
- ease of future microservice migration

---

## Important Design Decisions

### Concurrency Control

To prevent overselling:

- database-level locking (pessimistic locking) is used
- booking operations are transactional

### Inventory Handling

- ticket count is updated atomically
- no negative inventory allowed

### Caching

- event data is cached using Redis
- reduces database load for high-read operations

### Rate Limiting

- limits API abuse (especially booking endpoint)
- protects system during high traffic

### Clean Separation of Concerns

- controllers → request handling
- services → business logic
- repositories → data access

---

## Project Structure (Simplified)

```text
com.ashish.ticketing
├── config
├── common
├── modules
│   ├── auth
│   ├── user
│   ├── event
│   ├── booking
│   ├── inventory
│   ├── notification
│   ├── admin
│   ├── audit
│   └── scheduler
```

---

## Future Improvements

- Kafka-based asynchronous processing
- Email notifications with ticket PDF
- Payment integration
- Waitlist system for sold-out events
- Analytics dashboard
- Microservices migration

---

## What This Project Demonstrates

- Handling concurrent operations safely
- Designing real-world backend systems
- Writing maintainable and scalable code
- Applying caching and performance optimization
- Understanding trade-offs in system design

---

## Getting Started

```bash
git clone <repo-url>
cd ticketing-system
mvn clean install
mvn spring-boot:run
```

---

## Author

Ashish Jha (network.ashishjha@gmail.com)
Software Developer (Java, Spring Boot, Angular)
