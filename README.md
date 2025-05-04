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
- Open API documentation

---

## 📦 Tech Stack

- Java 17+
- Spring Boot 3.x
- Spring Security (JWT-based authentication)
- PostgreSQL
- Gradle
- Jakarta Servlet API

---

## 📝 Database Setup

- Create table: In src/main/resources/table, execute the create\_\* table scripts.
- Add constraints: Execute the alter_table_add_constraints scripts.

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

- General User Login Example

```
{
     "username": "john",
     "password": "1234"
}
```

- Admin Login Example

```
{
     "username": "dev",
     "password": "1234"
}
```

## 🌍 Deployed Application

### The application is live on Render. You can access the API and Swagger UI at the following links:

- Live API: https://recipe-backend-2ubw.onrender.com
- Swagger UI: https://recipe-backend-2ubw.onrender.com/swagger-ui/index.html

## 📚 Example Endpoints

| Method | Endpoint                         | Description                |
| ------ | -------------------------------- | -------------------------- |
| POST   | /api/v1/auth/login               | User login                 |
| GET    | /api/v1/recipes                  | List recipes (public)      |
| POST   | /api/v1/recipes/create           | Create recipe (admin only) |
| PUT    | /api/v1/recipes/{id}/update      | Update recipe (admin only) |
| DELETE | /api/v1/recipes/{id}/soft-delete | Delete recipe (admin only) |

## 🔧 Notes

### The Swagger UI is available both locally and on the live server for easy API testing.

- The Live API is hosted on Render and should be fully operational. All POST/GET requests should be made to the live endpoint.
