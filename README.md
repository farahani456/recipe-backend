# 🍽️ Recipe Management System

A RESTful API built with **Java Spring Boot** for managing recipes, users, and authentication with JWT. This project supports user registration, login, and role-based access control for managing recipe data.

---

## 🚀 Features

- User authentication with JWT (JSON Web Token)
- Role-based access control (Admin & regular users)
- CRUD operations for recipes
- Settings for recipe category, Ingredients, and UOM (CRUD)
- Image upload and retrieval
- User registration and login
- Pagination and search for recipe listings
- PostgreSQL database integration

---

## 📦 Tech Stack

- Java 17+
- Spring Boot 3.x
- Spring Security (JWT-based authentication)
- PostgreSQL
- Gradle
- Jakarta Servlet API

---

## Configuration

1. **application.properties:**
2. **Create table:**
   - go to src/main/resources/table
   - execute all create\_\* table
   - execute alter_table_add_constraints

## 🔧 Setup & Run

1. **Clone the repository:**

   ```bash
   git clone https://github.com/your-username/recipe-management.git
   cd recipe-backend
   ```

2. **Clean and build the application:**

   ```shell
   ./gradlew clean build
   ```

3. **Run the application:**

   ```shell
   ./gradlew bootrun
   ```

## 🛡️ API Authentication:

Login endpoint: POST /api/v1/auth/login

- General User

```
{
     "username": "john",
     "password": "1234"
}
```

- Admin

```
{
     "username": "dev",
     "password": "1234"
}
```

## 📚 Swagger UI Endpoint

- http://localhost:8080/swagger-ui/index.html

## 📚 Example Endpoints

| Method | Endpoint                         | Description                |
| ------ | -------------------------------- | -------------------------- |
| POST   | /api/v1/auth/login               | User login                 |
| GET    | /api/v1/recipes                  | List recipes (public)      |
| POST   | /api/v1/recipes/create           | Create recipe (admin only) |
| PUT    | /api/v1/recipes/{id}/update      | Update recipe (admin only) |
| DELETE | /api/v1/recipes/{id}/soft-delete | Delete recipe (admin only) |
