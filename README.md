# SpendWise

An AI-powered expense tracker built with Spring Boot and PostgreSQL, with a React frontend and Google Gemini integration planned.

## Features
- User authentication with JWT
- Per-user expense CRUD with ownership checks
- Filtering by date range and category
- Aggregations: category and monthly spending summaries
- Pagination and sorting

## Tech Stack
- **Backend**: Spring Boot 4, Java 17
- **Database**: PostgreSQL (Neon cloud)
- **Auth**: Spring Security + JWT (jjwt 0.12.6)
- **Persistence**: Spring Data JPA / Hibernate

## Status
Backend in progress. Frontend (React) and AI insights (Gemini) coming next.

## Setup
1. Copy `src/main/resources/application.properties.example` to `application.properties`
2. Fill in your PostgreSQL connection details and JWT secret
3. Run the app using one of the options below

### Running the app
- **Maven wrapper (recommended):**
  ```bash
  ./mvnw spring-boot:run
  ```
  On Windows:
  ```bash
  mvnw.cmd spring-boot:run
  ```
- **Build a JAR and run it:**
  ```bash
  ./mvnw clean package
  java -jar target/spendwise-0.0.1-SNAPSHOT.jar
  ```
- **From your IDE (IntelliJ):** run the `SpendwiseApplication` main class directly.

The app starts on [http://localhost:8080](http://localhost:8080) by default.
