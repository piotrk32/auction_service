# Auction Service Backend

## Overview

This repository contains the backend part of the Auction Service project, which is a comprehensive auction system enabling users to engage in buying and selling items through an interactive platform. This backend is implemented using Java and Spring Boot, providing robust API endpoints for the frontend, handling business logic, authentication, and data management.

## Technologies

- Java
- Spring Boot
- Spring Security with OAuth2 for authentication
- JPA/Hibernate for database interaction
- PostgreSQL as the relational database
- Maven for project management and build automation

## Features

- User authentication and authorization with OAuth2
- CRUD operations for auction items
- Real-time bidding system
- Secure transaction handling
- Data validation and error handling
- Logging and monitoring

## Getting Started

To get the backend server running locally:

- Clone this repo
- Ensure you have Java JDK 11 and Maven installed
- Set up PostgreSQL database
- Configure application properties in `src/main/resources/application.properties`
- Run `mvn spring-boot:run` to start the server

## API Documentation
After running the application, Swagger API documentation can be accessed at http://localhost:8080/swagger-ui.html

