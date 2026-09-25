# SpendWise

An AI-powered expense tracker built with Spring Boot and PostgreSQL, with a React/Vite frontend and Google Gemini integration.

## Project Structure

```
spendwise/
├── backend/          # Java/Spring Boot backend API
│   ├── src/
│   ├── pom.xml
│   └── mvnw
├── frontend/         # React/Vite frontend
│   ├── src/
│   ├── package.json
│   └── vite.config.js
└── README.md
```

## Features
- User authentication with JWT
- Per-user expense CRUD with ownership checks
- Filtering by date range and category
- Aggregations: category and monthly spending summaries
- Pagination and sorting
- AI-powered spending insights with Google Gemini

## Tech Stack
- **Backend**: Spring Boot 3, Java 17
- **Frontend**: React 18, Vite
- **Database**: PostgreSQL (Neon cloud)
- **Auth**: Spring Security + JWT (jjwt 0.12.6)
- **Persistence**: Spring Data JPA / Hibernate
- **AI**: Google Gemini API

## Setup

### Backend Setup
1. Navigate to `backend/` directory
2. Copy `src/main/resources/application.properties.example` to `application.properties`
3. Fill in your PostgreSQL connection details and JWT secret
4. Run using one of the options below

#### Running the Backend
- **Maven wrapper (recommended):**
  ```bash
  cd backend
  ./mvnw spring-boot:run
  ```
  On Windows:
  ```bash
  cd backend
  mvnw.cmd spring-boot:run
  ```
- **Build a JAR and run it:**
  ```bash
  cd backend
  ./mvnw clean package
  java -jar target/spendwise-0.0.1-SNAPSHOT.jar
  ```
- **From your IDE (IntelliJ):** run the `SpendwiseApplication` main class directly.

The backend API starts on [http://localhost:8080](http://localhost:8080) by default.

### Frontend Setup
1. Navigate to `frontend/` directory
2. Install dependencies:
   ```bash
   cd frontend
   npm install
   ```
3. Start the development server:
   ```bash
   npm run dev
   ```
4. Build for production:
   ```bash
   npm run build
   ```

The frontend will be available at [http://localhost:5173](http://localhost:5173) by default.
