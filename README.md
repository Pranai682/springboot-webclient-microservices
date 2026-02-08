# 🚀 Spring Boot Microservices Communication using WebClient

## 📌 Project Overview

This project demonstrates **microservice-to-microservice communication** using **Spring Boot** and **Spring WebClient (WebFlux)**. It contains two independent services where a client microservice communicates with a product microservice using reactive HTTP calls.

The project shows how to implement clean service communication, structured error handling, logging, and reactive request handling between services.

---

## 🧩 Services Included

### ✅ Product Service
- REST API provider microservice
- Handles Product CRUD operations
- Returns product data as JSON
- Runs independently on a separate port

### ✅ Product Client
- Consumer microservice
- Uses **Spring WebClient** to call Product Service APIs
- Implements reactive request/response handling
- Includes exception handling and logging

---

## 🔧 Key Features

- Microservice-to-microservice REST communication
- Reactive HTTP client using WebClient
- CRUD operations across services
- Centralized client service layer
- Status-based error handling
- Custom exception mapping
- Logging using SLF4J
- Reactive types (Mono / Flux)
- exchangeToMono and retrieve usage
- WebClient filters and lifecycle hooks

---

## ⚙️ Tech Stack

- Java 17
- Spring Boot
- Spring WebFlux (WebClient)
- REST APIs
- Reactor (Mono, Flux)
- SLF4J Logger
- Maven
- IntelliJ IDEA
- Git & GitHub

---

