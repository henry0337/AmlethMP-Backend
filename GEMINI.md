# Project Overview

This project is the backend for Amleth's music player application. It is a reactive Spring Boot application built with Java 21 and Gradle. It utilizes a wide range of technologies, including:

*   **Core Frameworks:** Spring Boot 3 (Reactive WebFlux), Spring Security
*   **Data & Persistence:** PostgreSQL (via R2DBC), Liquibase for database migrations, Redis for caching
*   **Messaging:** Apache Kafka
*   **Cloud Integration:** Spring Cloud Azure
*   **Monitoring:** Sentry
*   **Mapping:** MapStruct
*   **Utility:** Lombok
*   **Resilience:** Resilience4j
*   **API Documentation:** Springdoc OpenAPI (Swagger UI)
*   **Authentication:** JSON Web Tokens (JWT)

The application is designed to provide a robust and scalable backend for a music player, handling features such as user authentication, data storage, and potentially real-time interactions.

# Building and Running

This project uses Gradle as its build automation tool.

## Prerequisites

*   Java Development Kit (JDK) 21
*   Docker (for running PostgreSQL locally via `docker compose`)

## Build the Application

To build the application, navigate to the project root directory and execute the following command:

```bash
./gradlew build
```

This will compile the code, run tests, and package the application into a JAR file.

## Run PostgreSQL with Docker Compose

A `compose.yaml` file is provided to quickly spin up a PostgreSQL database instance required by the application. Navigate to the project root directory and run:

```bash
docker compose up -d postgres
```

The database credentials and connection details are expected to be provided via environment variables, as seen in `application-dev.yaml`.

## Run the Application

After building the application and ensuring the PostgreSQL database is running, you can run the Spring Boot application directly:

```bash
java -jar build/libs/AmlethMP-Backend-0.0.1-SNAPSHOT.jar
```

Alternatively, you can use the Spring Boot Gradle plugin to run the application:

```bash
./gradlew bootRun
```

**Note:** The application expects several environment variables for configuration (e.g., `REDIS_HOST`, `DB_USERNAME`, `JWT_SECRET`). These should be set in your environment before running the application, especially for the development profile (`application-dev.yaml`).

## Build Docker Image

You can build a Docker (OCI) image of the application using the Spring Boot plugin:

```bash
./gradlew bootBuildImage
```

# Development Conventions

*   **Language:** Java 21
*   **Build Tool:** Gradle
*   **Reactive Programming:** Heavily utilizes Spring WebFlux for reactive endpoints and R2DBC for reactive database access.
*   **Dependency Injection:** Spring Framework's dependency injection is used throughout.
*   **Code Generation:** Lombok is used to reduce boilerplate code (e.g., getters, setters, constructors). MapStruct is used for efficient object mapping.
*   **Security:** Spring Security is configured for JWT-based authentication.
*   **Configuration:** The `dev` profile is active by default (specified in `application.yaml`). Environment variables are used extensively for sensitive configurations in `application-dev.yaml`.
*   **API Documentation:** Swagger UI is available for API exploration, typically at `/swagger-ui.html` or `/webjars/swagger-ui/index.html`.
