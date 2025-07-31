# Digital Wallet System

This is a simple **Digital Wallet System** built with **Spring Boot** and **H2 Database**.  
It allows users to create wallets, deposit and withdraw funds, approve transactions, and view transaction history.

---

## Features

- Create and list wallets per customer
- Deposit money into wallets
- Withdraw money from wallets
- Approve or deny transactions
- View transaction history
- Simple authentication using HTTP Basic Auth
- H2 in-memory database for easy testing and development

---

## Technology Stack

- Java 17+
- Spring Boot 3.x
- Spring Data JPA (Hibernate)
- Spring Security (Basic Authentication)
- H2 Database (in-memory and file-based modes)
- Maven

---

## Project Structure

- **controller**: REST API endpoints (`WalletController`)
- **service**: Business logic (`WalletService`)
- **model**: Entity classes (`Wallet`, `Transaction`, `Customer`)
- **dto**: Data transfer objects for requests and responses
- **repository**: Spring Data JPA repositories
- **config**: Security configuration and other app configs

---

## Getting Started

### Prerequisites

- Java 17 or newer installed
- Maven installed

### Run Application

```bash
mvn spring-boot:run
