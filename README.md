# GTD Cards Backend Assessment

This project is a **Spring Boot backend application** developed as part of a technical assessment for **GTD Cards**.
The application demonstrates clean backend architecture, database integration, transactional REST APIs, pagination, logging, API documentation using Swagger, and an endpoint that integrates with an **external card‑scheme service**.

---

## 🧱 Tech Stack

- **Java**: 17  
- **Spring Boot**: 4.0.5  
- **Spring Data JPA / Hibernate**  
- **Database**: Microsoft SQL Server (MSSQL)  
- **Build Tool**: Maven  
- **API Documentation**: Springdoc OpenAPI (Swagger UI)  
- **Logging**: SLF4J + Logback with Trace ID  
- **DB Migration (Optional)**: Flyway  
- **API Testing**: Swagger UI & Postman  

---

## 📂 Project Structure

```
.
├── README.md
├── pom.xml
├── postman/
│   └── gtd-cards-api.postman_collection.json
└── src
    └── main
        ├── java/com/shahir/gtdcards
        │   ├── controller		// REST API controllers
        │   ├── service			// Business logic
        │   ├── repository		// JPA repositories
        │   ├── entity			// Domain entities
        │   ├── dto				// Request / Response DTOs
        │   ├── config			// Logging, Swagger, REST client
        │   └── exception		// Global exception handling
        └── resources
            ├── application.properties
            ├── db/migration
            │   ├── V1__create_database_and_tables.sql
            │   └── V2__insert_dummy_clients.sql
```

---

## 🗄 Database Setup (REQUIRED FIRST STEP)

Before running the application, please ensure **Microsoft SQL Server** is installed and running locally.

### 1️⃣ Run Database Migration Scripts

After cloning the project, navigate to the following folder:

```
src/main/resources/db/migration
```

The project provides SQL scripts that must be executed **manually** using **SQL Server Management Studio (SSMS)**.

#### Script Execution Order

1. **V1__create_database_and_tables.sql**  
   - Creates the `TESTDB` database (if not exists)
   - Creates required tables (`clients`)

2. **V2__insert_dummy_clients.sql**  
   - Inserts sample/dummy client records
   - Used for testing pagination, update, and retrieval APIs

> ℹ️ The scripts are **idempotent** and safe to re‑run.

---

### 2️⃣ Create SQL Login (SQL Authentication)

If not already created, execute the following in SSMS:

```sql
CREATE LOGIN appuser WITH PASSWORD = 'admin';
GO

USE TESTDB;
CREATE USER appuser FOR LOGIN appuser;
ALTER ROLE db_owner ADD MEMBER appuser;
GO
```

SQL authentication is intentionally used for local development simplicity and stability.

---

## ⚙️ Application Properties Configuration

Update the database configuration in:

```
src/main/resources/application.properties
```

### Required Properties to Update

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=TESTDB;encrypt=true;trustServerCertificate=true
spring.datasource.username=appuser
spring.datasource.password=admin
```

If your local database name, username, or password differs, update the values accordingly.

---

## 🚀 Running the Application

```bash
mvn spring-boot:run
```

Swagger UI:
```
http://localhost:8080/swagger-ui.html
```
Swagger API Docs:
```
http://localhost:8080/v3/api-docs
```

---

## 🐳 Running the Application with Docker (OPTIONAL)

> ⚠️ Docker usage is **optional**. This section is provided as an alternative
> way to run the application and does not affect the main assessment requirement.

### 1️⃣ Dockerfile Location

The `Dockerfile` is located at the **root of the project**, next to `pom.xml`.

### 2️⃣ Update Database Host for Docker

When running inside Docker, update the datasource URL in `application.properties`:

```properties
spring.datasource.url=jdbc:sqlserver://host.docker.internal:1433;databaseName=TESTDB;encrypt=true;trustServerCertificate=true
```

### 3️⃣ Build Application JAR

```bash
mvn clean package
```

### 4️⃣ Build Docker Image

```bash
docker build -t gtd-cards-api .
```

### 5️⃣ Run Docker Container

```bash
docker run -p 8080:8080 gtd-cards-api
```

Swagger UI will be accessible at:

```
http://host.docker.internal:8080/swagger-ui.html
```

---

## 🔌 API Design

The application follows a layered architecture (Controller → Service → Repository).

### Client APIs

| Method | Endpoint |
|------|--------|
| POST | `/api/v1/clients` |
| PUT | `/api/v1/clients/{id}` |
| GET | `/api/v1/clients/{id}` |
| GET | `/api/v1/clients` |

Pagination is supported using `page` and `pageSize` parameters.

---

## 💳 External Card Scheme Integration

The endpoint below demonstrates **nested communication with an external service**.

| Method | Endpoint |
|------|--------|
| GET | `/api/v1/clients/{id}/card-scheme` |

### Design Flow

```
Client
  ↓
GTD Cards API
  ↓
External BIN/Card‑Scheme Service
  ↓
GTD Cards API
  ↓
Response
```

### Notes

- Client data is retrieved from the internal database
- Card‑scheme metadata (e.g. VISA, Mastercard) is obtained from an external public API
- No sensitive card information is stored
- Demonstrates clean external service integration

---

## 🔁 Transaction Management

- **POST / PUT** operations use `@Transactional`
- **GET** operations use `@Transactional(readOnly = true)`

Optimistic locking is implemented using a `version` column to ensure safe concurrent updates.

---

## 📜 Logging & Trace ID

Each incoming request is assigned a **trace ID**, included in:
- Application logs
- HTTP response headers

This supports debugging and request correlation.

Logs file will be located at 
```
/logs/gtd-cards-app.log
```
---

## 🧪 Postman Integration

OpenAPI specification:
```
http://localhost:8080/v3/api-docs
```

Import this URL directly into Postman to generate an API collection.
Or
Can get postman collection JSON file at
```
/postman/GTD Cards API - Shahir.postman_collection.json
```

---

