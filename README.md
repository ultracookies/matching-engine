# Order Matching Engine (WIP)

A Spring MVC-based backend application for submitting and validating trading orders. This project lays the foundation for a full-featured order matching engine, designed to support market simulation and trading operations.

---

## ✅ Current Features

- **Submit Orders**
  - Implements a `POST /api/orders` endpoint that accepts JSON payloads.
  - Uses `@Valid` and `@RequestBody` to ensure incoming orders are well-formed.
  - Publishes validated orders via a service layer for further processing.
- **Input Validation**
  - Rejects invalid enums and malformed requests using built-in validation.
  - Handles errors thrown during JSON deserialization (e.g., invalid enum values).
- **Unit Testing**
  - Uses Spring's MockMvc to test order submission and error handling.
  - Verifies that invalid inputs produce appropriate error responses.

---

## 🛠 Technologies Used

- Java 17+
- Spring Boot / Spring MVC
- JUnit 5
- Jackson for JSON serialization/deserialization
- MockMvc for testing

---

## 🔭 Planned Features

- **Additional Endpoints**
  - `DELETE /api/orders/{id}` – Cancel an active order.
  - `PUT /api/orders/{id}` – Update an existing order (e.g., quantity or price).
  - `GET /api/orders` – View all active/pending orders.
- **Matching Engine**
  - Match buy and sell orders based on price-time priority.
  - Execute trades and maintain an order book.
- **Persistence Layer**
  - Integrate with a relational database (e.g., PostgreSQL or MySQL).
  - Store submitted, matched, and canceled orders.
- **Error Handling**
  - Implement a global `@ControllerAdvice` for clean API responses.
  - Return detailed error objects with codes and messages.
- **API Documentation**
  - Add Swagger/OpenAPI for interactive documentation and testing.

---

## 🚧 Status

This project is a work in progress. The basic order submission endpoint is live and fully unit-tested. Next up is implementing order cancellation and updating, followed by the matching engine and persistence integration.

---

## 📂 Structure (Coming Soon)

The project will follow a clean architecture with layers for controllers, services, models, and repositories.

---

## 🤝 Contributions

Suggestions and pull requests are welcome as the project evolves!

