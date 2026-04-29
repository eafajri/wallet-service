# 💰 Wallet System API

A simple fintech-style wallet service built with Spring Boot that demonstrates core backend engineering concepts such as transaction handling (ACID), idempotency, concurrency control, and clean architecture.

This project simulates real-world financial system challenges and is designed as a strong backend portfolio.

---

## 🚀 Features

- 👤 User wallet management
- 💰 Top Up balance
- 💸 Payment (deduct balance)
- 📊 Transaction history with pagination
- 🔁 Idempotency (prevents duplicate transactions)
- 🔒 Concurrency-safe using pessimistic locking
- ⚠️ Global error handling with consistent response format

---

## 🧠 Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Flyway (database migration)

---

## 🏗️ Architecture

Controller → Service → Repository → Database

- Controller: Handles HTTP request/response
- Service: Business logic (transaction, idempotency, validation)
- Repository: Database access

---

## 🗄️ Database

Tables:
- users
- wallets
- transactions

---

## 🔥 Core Concepts

### Transaction (ACID)
All balance updates are wrapped inside transaction to ensure consistency.

### Idempotency
Using unique reference_id to prevent duplicate transactions.

### Concurrency Control
Using pessimistic locking to avoid race conditions.

---

## 📦 API Endpoints

POST /wallets/{userId}/topup  
POST /wallets/{userId}/pay  
GET /wallets/{userId}/balance  
GET /wallets/{userId}/transactions

---

## ⚠️ Error Format

{
"message": "error message",
"errors": null
}

---

## 🧪 How to Run

1. Create DB:
   CREATE DATABASE wallet_db;

2. Run:
   ./mvnw spring-boot:run

---

## 🧪 Testing

curl example:

curl -X POST http://localhost:8080/wallets/1/topup -H "Content-Type: application/json" -d '{"amount":10000,"referenceId":"trx-001"}'

---

## 🚀 Future Improvements

- Auth (JWT)
- Rate limiting
- Redis locking
- Kafka
- Audit logging

---

## ⭐ Purpose

Showcase real-world backend engineering:
- consistency
- concurrency
- idempotency
