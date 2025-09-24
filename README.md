# Access Control System

## Overview

This is a Spring Boot application for user authentication and role-based access control. It uses MongoDB for data persistence and Thymeleaf for server-side rendering of HTML templates. The system supports user registration, login, and access restriction based on roles: **MANAGER**, **LEADER**, and **EMPLOYEE**.

---

## Architecture

### 1. Layered Structure

- **Controller Layer**  
  Handles HTTP requests, renders views, and manages user interactions.

  - [`UserController`](src/main/java/edu/umc/access_control/User/UserController.java): Handles main pages and error routes.
  - [`AuthController`](src/main/java/edu/umc/access_control/Auth/AuthController.java): Manages registration and login forms.

- **Service Layer**  
  Contains business logic for user management.

  - [`UserService`](src/main/java/edu/umc/access_control/User/UserService.java): Registers users, checks for duplicates, hashes passwords, and interacts with the repository.

- **Repository Layer**  
  Interfaces with MongoDB using Spring Data.

  - [`UserRepository`](src/main/java/edu/umc/access_control/User/UserRepository.java): CRUD operations for users.

- **Security Layer**  
  Configures authentication and authorization.

  - [`SecurityConfig`](src/main/java/edu/umc/access_control/config/SecurityConfig.java): Sets up role-based access, login/logout, and exception handling.
  - [`UserDetailsServiceImp`](src/main/java/edu/umc/access_control/User/UserDetailsServiceImp.java): Loads user details for authentication.

- **Domain Layer**  
  Defines data models and DTOs.
  - [`UserModel`](src/main/java/edu/umc/access_control/User/UserModel.java): Represents a user document in MongoDB.
  - [`UserRegistrationDTO`](src/main/java/edu/umc/access_control/payloads/UserRegistrationDTO.java): Used for registration form binding.
  - [`Role`](src/main/java/edu/umc/access_control/User/Role.java): Enum for user roles.

---

### 2. Authentication & Authorization

- **Registration**  
  Users register via `/register`. The form data is validated and processed by [`AuthController`](src/main/java/edu/umc/access_control/Auth/AuthController.java).  
  Passwords are hashed using BCrypt before storage.

- **Login**  
  Custom login page at `/login`. Spring Security authenticates users using credentials stored in MongoDB.

- **Role-Based Access**

  - `/admin`: Only accessible by users with the `MANAGER` role.
  - `/leader`: Accessible by `LEADER` and `MANAGER`.
  - `/employees`: Accessible by `EMPLOYEE`, `LEADER`, and `MANAGER`.
  - All other routes require authentication.

- **Access Denied**  
  Unauthorized access redirects to `/403`, rendering a custom error page.

---

### 3. Data Model

- **User Document**
  - `id`: MongoDB ObjectId
  - `username`: Unique
  - `email`: Unique
  - `password`: Hashed
  - `roles`: Set of roles (e.g., `["MANAGER"]`)
  - `isVerified`: Email verification flag
  - `creationDate`: Timestamp

---

### 4. Views & Templates

- **Thymeleaf Templates**
  - [`register.html`](src/main/resources/templates/register.html): Registration form.
  - [`login.html`](src/main/resources/templates/login.html): Login form.
  - [`home.html`](src/main/resources/templates/home.html): Main page after login.
  - [`admin.html`](src/main/resources/templates/admin.html): Manager area.
  - [`leader.html`](src/main/resources/templates/leader.html): Leader area.
  - [`employees.html`](src/main/resources/templates/employees.html): Employees area.
  - [`error/403.html`](src/main/resources/templates/error/403.html): Forbidden error page.
  - [`fragments/navbar.html`](src/main/resources/templates/fragments/navbar.html): Navigation bar, shows links based on authentication status.

---

### 5. Security Configuration

- **Spring Security**
  - Disables CSRF for simplicity.
  - Configures custom login and logout URLs.
  - Uses `PasswordEncoder` (BCrypt).
  - Maps roles to authorities (`ROLE_MANAGER`, etc.).
  - Restricts access using `.hasRole()` and `.hasAnyRole()`.

---

### 6. MongoDB Integration

- **Connection**  
  Configured in [`application.properties`](src/main/resources/application.properties) using a connection URI.

- **Repositories**  
  Spring Data MongoDB automatically implements repository methods for user lookup and existence checks.

---

## How It Works

1. **User Registration**

   - User fills out the registration form.
   - [`AuthController`](src/main/java/edu/umc/access_control/Auth/AuthController.java) validates input and calls [`UserService.registerUser`](src/main/java/edu/umc/access_control/User/UserService.java).
   - If username/email exists, error is shown.
   - On success, user is redirected to login.

2. **User Login**

   - User submits credentials at `/login`.
   - Spring Security authenticates using [`UserDetailsServiceImp`](src/main/java/edu/umc/access_control/User/UserDetailsServiceImp.java).
   - On success, user is redirected to `/home`.

3. **Role-Based Access**

   - After login, user can access pages based on their role.
   - Unauthorized access triggers a redirect to `/403`.

4. **Logout**
   - User can log out via the navbar, which submits a POST to `/logout`.

---

## Running the Project

1. **Install Dependencies**

   ```sh
   ./mvnw clean install
   ```

2. **Configure MongoDB**

   - Ensure MongoDB is running and accessible.
   - Update credentials in [`application.properties`](src/main/resources/application.properties) if needed.

3. **Start the Application**

   ```sh
   ./mvnw spring-boot:run
   ```

4. **Access in Browser**
   - Registration: `http://localhost:8080/register`
   - Login: `http://localhost:8080/login`
   - Home: `http://localhost:8080/home`
   - Role pages: `/admin`, `/leader`, `/employees`

---

## Extending & Customizing

- Add more roles by updating [`Role`](src/main/java/edu/umc/access_control/User/Role.java) and security config.
- Add email verification or password reset features.
- Customize templates for branding.
- Integrate REST APIs for frontend frameworks.

---

## Technologies Used

- Spring Boot
- Spring Security
- Spring Data MongoDB
- Thymeleaf
- Lombok

---

## File References

- [src/main/java/edu/umc/access_control/User/UserController.java](src/main/java/edu/umc/access_control/User/UserController.java)
- [src/main/java/edu/umc/access_control/Auth/AuthController.java](src/main/java/edu/umc/access_control/Auth/AuthController.java)
- [src/main/java/edu/umc/access_control/User/UserService.java](src/main/java/edu/umc/access_control/User/UserService.java)
- [src/main/java/edu/umc/access_control/User/UserRepository.java](src/main/java/edu/umc/access_control/User/UserRepository.java)
- [src/main/java/edu/umc/access_control/User/UserDetailsServiceImp.java](src/main/java/edu/umc/access_control/User/UserDetailsServiceImp.java)
- [src/main/java/edu/umc/access_control/config/SecurityConfig.java](src/main/java/edu/umc/access_control/config/SecurityConfig.java)
- [src/main/resources/templates/](src/main/resources/templates/)
- [src/main/resources/application.properties](src/main/resources/application.properties)

---

## License

This project is for educational/demo purposes.
