# Spring Boot Blog App Backend

A production-ready Spring Boot REST API for a blogging platform. This backend supports user registration, JWT-based authentication, posts management (supporting both drafts and published statuses), tags, and categories.

---

## Tech Stack & Features
- **Java**: 17
- **Framework**: Spring Boot (Spring Web MVC, Spring Data JPA, Spring Security, Validation)
- **Database**: PostgreSQL (Development & Testing)
- **Object Mapping**: MapStruct
- **Aids**: Lombok, JWT (jjwt)
- **Containerization**: Docker Compose (PostgreSQL database & Adminer DB Manager)

---

## Getting Started

### 1. Prerequisites
Ensure you have the following installed on your machine:
* Java Development Kit (JDK) 17 or higher
* Docker Desktop (for database containers)
* Maven (optional, wrapper is included in the project)
* Postman (for testing endpoints)

### 2. Database Setup
Start the local PostgreSQL database and Adminer web database manager using Docker Compose:
```bash
docker compose up -d
```
* **PostgreSQL Port**: `5432` (accessible locally at `localhost:5432` with user `postgres` and password `changemeinprod!`)
* **Adminer Web Console**: `http://localhost:8888` (allows you to view and run SQL queries against the database directly from your browser)

### 3. Run the Application
Start the Spring Boot backend server:
```bash
# Windows
.\mvnw.cmd spring-boot:run

# macOS/Linux
./mvnw spring-boot:run
```
The server will start up on **port 8080** (`http://localhost:8080`).

### 4. Running Tests
Run the Maven test suite against your active PostgreSQL database:
```bash
# Windows
.\mvnw.cmd test

# macOS/Linux
./mvnw test
```

### 5. Interactive API Documentation (Swagger UI)
Once the application is running, you can access the Swagger UI directly in your browser:
* **Interactive UI**: `http://localhost:8080/swagger-ui/index.html`
* **OpenAPI Docs**: `http://localhost:8080/v3/api-docs`

---

## API Endpoints Reference

### 1. Authentication (Public)
The system seeds a default user on startup:
* **Email**: `user@test.com`
* **Password**: `password`

#### `POST /api/v1/auth/register` (Public)
Create a new user account.
* **Payload**:
  ```json
  {
    "name": "John Doe",
    "email": "john@example.com",
    "password": "mysecretpassword"
  }
  ```

#### `POST /api/v1/auth/login` (Public)
Authenticate to obtain a JWT token.
* **Payload**:
  ```json
  {
    "email": "john@example.com",
    "password": "mysecretpassword"
  }
  ```
* **Response**:
  ```json
  {
    "token": "<JWT_TOKEN>",
    "expiresIn": 86400
  }
  ```

---

### 2. Categories

#### `GET /api/v1/categories` (Public)
Retrieve a list of all categories.

#### `POST /api/v1/categories` (Authenticated)
Create a new category.
* **Payload**:
  ```json
  {
    "name": "Software Development"
  }
  ```

#### `DELETE /api/v1/categories/{id}` (Authenticated)
Delete a category by its UUID.

---

### 3. Tags

#### `GET /api/v1/tags` (Public)
Retrieve a list of all tags.

#### `POST /api/v1/tags` (Authenticated)
Create one or multiple tags.
* **Payload**:
  ```json
  {
    "names": ["Java", "Docker", "REST API"]
  }
  ```

#### `DELETE /api/v1/tags/{id}` (Authenticated)
Delete a tag by its UUID.

---

### 4. Posts

#### `GET /api/v1/posts` (Public)
Retrieve a list of all published posts. You can filter the posts using optional query parameters:
* **Query Parameters**:
  * `categoryId` (UUID) - Filter posts by category.
  * `tagId` (UUID) - Filter posts by tag.

#### `GET /api/v1/posts/{id}` (Public)
Get a specific published post by its UUID.

#### `GET /api/v1/posts/drafts` (Authenticated)
List draft posts of the currently authenticated user.

#### `POST /api/v1/posts` (Authenticated)
Create a new post.
* **Payload**:
  ```json
  {
    "title": "Getting Started with Spring Boot",
    "content": "Spring Boot makes it easy to create stand-alone, production-grade applications.",
    "categoryId": "<CATEGORY_UUID>",
    "tagIds": [
      "<TAG_UUID_1>",
      "<TAG_UUID_2>"
    ],
    "status": "DRAFT"
  }
  ```
  *(Note: `status` must be either `"DRAFT"` or `"PUBLISHED"`)*

#### `PUT /api/v1/posts/{id}` (Authenticated)
Update an existing post (e.g. changing title, contents, or transitioning from draft to published).
* **Payload**:
  ```json
  {
    "id": "<POST_UUID>",
    "title": "Getting Started with Spring Boot (Updated)",
    "content": "Spring Boot makes it easy to create stand-alone, production-grade applications.",
    "categoryId": "<CATEGORY_UUID>",
    "tagIds": [
      "<TAG_UUID_1>"
    ],
    "status": "PUBLISHED"
  }
  ```

#### `DELETE /api/v1/posts/{id}` (Authenticated)
Delete a post by its UUID.
