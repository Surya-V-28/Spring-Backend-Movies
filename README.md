# Movie Backend  Application - Spring Boot

This is a Spring Boot project demonstrating key features and concepts such as database configuration, file handling, CRUD operations, exception handling, pagination, sorting, security with JWT authentication, and API testing. The project is designed to give you a practical understanding of building a full-stack application using Spring Boot.

## Features
- **Project Setup & Database Configuration**: Initial setup and configuration of the project with database connections.
- **File Handling**: Implementing file upload and download functionality in the application.
- **CRUD Operations**: Creating, reading, updating, and deleting data in the application using Spring Boot.
- **Exception Handling**: Managing exceptions and providing custom error messages for better user experience.
- **Pagination & Sorting**: Implementing pagination and sorting for handling large data sets.
- **Spring Security with JWT Authentication**: Securing the application using JWT (JSON Web Token) for user authentication and authorization.
- **API Testing with Security**: Testing secured APIs with Spring Security to ensure proper access control.
- **Forgot/Reset Password**: Implementing functionality for users to reset their passwords when forgotten.
- **Small Assignment**: A small TODO assignment to further enhance the skills.

## Installation

Follow the steps below to run this project locally.

### Prerequisites
- Java 11 or higher
- Maven
- MySQL (or any other preferred database)
- Spring Boot (for backend development)

## Project Structure
The project is structured as follows:
src/main/java: Contains all the source code files for the project.
- controller: Handles HTTP requests and responses.
- service: Business logic of the application.
- repository: Database access logic (CRUD operations).
- model: Data entities and models.
- security: Handles authentication and authorization.
- exception: Custom exceptions and error handling classes.
- src/main/resources: Contains configuration files like application.properties.

## API Endpoints
### 1. Authentication
- POST /api/auth/login - Login and generate JWT Token
- POST /api/auth/register - Register a new user
- POST /api/auth/forgot-password - Request a password reset
- POST /api/auth/reset-password - Reset password using the token

### 2. CRUD Operations
- GET /api/v1/movie/{id} - Get details of a movie by ID (e.g., /api/v1/movie/1)
- POST /api/v1/movie - Create a new movie
- PUT /api/v1/movie/{id} - Update an existing movie
- DELETE /api/v1/movie/{id} - Delete a movie

### 3. Secured Endpoints
- GET /api/profile - Get user profile (requires authentication)


