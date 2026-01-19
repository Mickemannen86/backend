# TankaBilligt – Backend (BE)

Backend for TankaBilligt, a fullstack application that finds the cheapest gas station near the user's current location.

This project is part of my fullstack graduation work, where the backend (Spring Boot / Java) handles authentication, security, and business logic, while the frontend focuses on user interaction and presentation.

🔗 Backend repository (Spring Boot / Java):  
[https://github.com/Mickemannen86/tanka-billigt-fe](https://github.com/Mickemannen86/tanka-billigt-fe)

---

## 🧠 Project Overview

**TankaBilligt Backend (BE)** is the core service for my first full-stack graduation project.  

It provides:

- User authentication with JWT
- Secure communication with the frontend (React / Next.js)
- Integration with external APIs (Google Geocoding)
- Data processing and filtering
- Meaningful responses to the frontend

This project demonstrates my ability to design and implement a real end-to-end system.

---

## 🧱 Tech Stack (Backend)

- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA
- JWT (JJWT – modern API usage)
- PostgreSQL
- Maven

---

## 🔐 Authentication & Security

- **Authentication**: JSON Web Tokens (JWT)  
- **Security concepts implemented**:
  - Stateless authentication
  - Role-based authorities (Spring Security)
  - Secure password hashing (BCrypt)
  - JWT validation via request filter
- **Sensitive data**: All secrets (JWT keys, API keys, passwords) are externalized in `application.properties` and **not committed to GitHub**.

> For portfolio purposes, the exposed JWT secret has been replaced with a placeholder. In production, secrets should be managed via environment variables or secret vaults.

---

## 🔑 Secrets & Configuration

The project uses an application.properties file that is not committed to GitHub.

A template is provided instead:
```
# Spring Security
spring.main.banner-mode=off

# Database
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:postgresql://localhost:5432/<your_database_name>
spring.datasource.username=<your_username>
spring.datasource.password=<your_password>

# External API
google.geocoding.api.key=<your_GCP_key>

# JWT
jwt.secret=<your_jwt_secret>
jwt.expiration-ms=3600000
```

This approach keeps sensitive information out of the repository while still allowing easy local setup.

---

## 🌍 External API Integration

- Google Geocoding API is used to:
  - Receive latitude & longitude from the frontend
  - Reverse-geocode coordinates to a postal town
  - Match the location against local gas station data
  - Return the station with the lowest fuel price

---

## 🧠 Business Logic Flow

Frontend (latitude, longitude)
↓
Backend Controller
↓
Geocoding Service (Google API)
↓
Postal town extraction
↓
Gas station filtering
↓
Lowest price selection
↓
Response to frontend


This separation keeps the backend clean, testable, and maintainable.

---

## 🛠️ What I Learned

- Designing a secure authentication flow
- Structuring a Spring Boot application properly
- Integrating third-party APIs
- Handling real-world API failures
- Refactoring legacy code safely
- Managing secrets responsibly
- Building a full-stack system, not just isolated features

---

## 🚀 How to Run Locally

1. Clone the repository.
2. Create `application.properties` based on the provided template (all sensitive values are placeholders).  
3. Configure PostgreSQL.
4. Run the Spring Boot application.
5. Connect the frontend client.

---

## ⚠️ Notes

- This backend is intentionally learning-focused but production-inspired.
- The goal is to showcase both coding skills and understanding of secure, maintainable backend design.
