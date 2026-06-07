# 📦 Production Monitoring System

## 📌 Description

This project is a Spring Boot application designed for real-time monitoring of production processes in a factory environment.

The system tracks operators working on machines and provides live information about production progress, norms, and announcements from management.

Managers can manage users, machines, production norms, and announcements.

---

## 🏗️ Features

### 👤 User Management
- Create, update, and delete users
- Assign operators to machines
- Role-based access: OPERATOR / MANAGER

### 🏭 Machine Management
- Machine tracking system
- Machine states: WORKING, WAITING, FAILURE
- One operator is assigned to one machine

### 📏 Production Norms
- Target number of packages per shift
- Minimum performance threshold
- Critical performance threshold
- Time required per package

### 📢 Announcements
- Created by managers
- Visible to operators
- Validity period (validFrom, validTo)

### 📦 Production Monitoring
- Real-time packed products counter
- Last package timestamp tracking
- Dashboard for operator overview

---

## 🧠 Business Rules

- Each operator must be assigned to exactly one machine
- A machine can have only one operator assigned
- Production norms apply globally to all machines
- Only manager doesn't have machine assignment
- Managers are responsible for configuration and announcements

---

## 🛠️ Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- Hibernate
- Microsoft SQL Server
- Maven
- JUnit 5
- Mockito

---

## ▶️ How to Run

### 1. Configure database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:sqlserver://localhost;databaseName=YOUR_DB
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update