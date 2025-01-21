# Pezesha Account Management API

A Spring Boot application that provides REST APIs for managing bank accounts and money transfers.

## Features

- Account Management (Create, Read, Update)
- Money Transfers between accounts
- Balance Inquiries
- Swagger API Documentation
- Error Handling
- Input Validation

## Technologies

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL
- Swagger/OpenAPI 3.0
- Maven
- Lombok
- JUnit 5
- Mockito

## Prerequisites

- JDK 17 or later
- Maven 3.6+
- PostgreSQL 12+
- Your favorite IDE (IntelliJ IDEA recommended)

## Setup

1. Clone the repository:
```bash
git clone https://github.com/arthuradinder/pezesha.git
cd pezesha
```
2. Configure the database in application yml
3. Build the project:
```mvn clean install```
4. Run the application
```mvn spring-boot:run```

## API Usage

### Authentication

Before making any API calls, you need to obtain an access token:

1. Get Access Token:
```json
POST http://localhost:8080/api/v1/auth/register
Content-Type: application/json

{
   "firstname": "John",
  "lastname": "Doe",
  "email": "john.doe@example.com",
  "password": "password123",
  "role": "USER"
}
```
2. Use the token in subsequent requests
### Account Operations
1. Create a new account
```json
POST http://localhost:8080/api/v1/accounts
Authorization: Bearer {your_access_token}
Content-Type: application/json
{
    "id": 1,
    "accountName": "John Doe",
    "balance": 1000.00,
    "version": 1
}
```
2. Get account details
```json
GET /api/accounts/{id}
Authorization: Bearer {your_access_token}
```
### Funds Transfer

After authentication, you can perform funds transfers between accounts using the transfer endpoint.

#### Make a Transfer

```json
POST /api/v1/transfers
Authorization: Bearer {your_access_token}
Content-Type: application/json

{
    "sourceAccountId": 1,
    "destinationAccountId": 2,
    "amount": 500.00,
    "description": "Rent payment for June"
}









