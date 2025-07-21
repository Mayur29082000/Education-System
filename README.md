# Education System Spring Boot Application

## Project Overview

This project is a comprehensive RESTful API built with **Spring Boot** to manage core entities within an education system: **Colleges, Departments, Teachers, and Students.** It leverages an in-memory **H2 database** for development and testing, providing a robust backend for educational administration.

The application is designed following best practices, including layered architecture (controller, service, repository), robust error handling, data validation, and efficient data fetching strategies (`JOIN FETCH` to mitigate N+1 problems).

---

## 🎥 Project Demonstration 

Watch a detailed walkthrough of the Education System:
[Watch Project Demo on Google Drive](https://drive.google.com/file/d/1S2jpNWkpZwwonJ9CTwS0x7gStkzGcZaX/view?usp=drive_link)
Watch Project Demo on Google Drive
https://drive.google.com/file/d/1S2jpNWkpZwwonJ9CTwS0x7gStkzGcZaX/view?usp=drive_link

## Core Directories and Their Purpose

* `education-system/`: The root of your Maven project.
* `pom.xml`: Maven Project Object Model. Defines project dependencies (e.g., Spring Web, Spring Data JPA, H2, Lombok, Validation, Spring Test), build plugins, and project metadata.
* `src/main/java/com/example/education/`: Contains the main application logic.
    * `EducationSystemApplication.java`: The entry point of the Spring Boot application.
    * `controller/`: Contains RESTful API controllers. These classes handle incoming HTTP requests, delegate business logic to service layers, and return HTTP responses.
    * `entity/`: Defines the JPA (Java Persistence API) entities, which are plain old Java objects (POJOs) mapped to database tables.
    * `exception/`: Custom exception classes and a global exception handler to provide consistent error responses.
    * `profile/`: Demonstrates Spring Profiles, allowing different bean implementations based on active environment (e.g., dev, prod).
    * `repository/`: Contains Spring Data JPA repositories, interfaces that extend JpaRepository to provide CRUD (Create, Read, Update, Delete) operations and custom query methods for entities.
    * `service/`: Contains the business logic. Service classes orchestrate operations, interact with repositories, and encapsulate the core functionality of the application.
* `src/main/resources/`: Contains configuration files and static resources.
    * `application.properties`: Main configuration file for Spring Boot, defining database connections, server port, logging levels, etc.
    * `data.sql`: SQL script executed by H2 database on startup to populate initial data.
* `src/test/java/com/example/education/`: Contains unit and integration tests.
    * `service/CollegeServiceTest.java`: Example unit test for CollegeService, demonstrating Mockito usage.

## Key Features & Functionalities

* **Comprehensive CRUD Operations:** Full Create, Read, Update, Delete functionality for Colleges, Departments, Teachers, and Students.
* **Batch Operations:** Efficiently create multiple entities (Colleges, Departments, Teachers, Students) in a single API call, reducing network overhead.
* **Partial Updates (PATCH):** Allows for flexible updates to existing resources, modifying only specified fields without sending the entire object.
* **Robust Data Relationships:**
    * College (One) to Department (Many)
    * Department (One) to Teacher (Many)
    * Department (One) to Student (Many)
    * Relationships are managed with `fetch = FetchType.LAZY` to optimize performance, with strategic `JOIN FETCH` queries in repositories to prevent N+1 problems and `LazyInitializationException` for API responses.
* **Data Validation:** Utilizes Jakarta Bean Validation annotations (`@NotBlank`, `@Size`, `@Email`, `@NotNull`, `@Valid`) to ensure data integrity at the API layer.
* **Centralized Error Handling:**
    * Uses `@RestControllerAdvice` to handle exceptions globally.
    * `ResourceNotFoundException`: Custom exception for entities not found, returning a `404 Not Found` response with structured `ErrorDetails`.
    * Handles validation failures (`MethodArgumentNotValidException`) returning `400 Bad Request` with field-specific errors.
    * Includes a fallback for unhandled exceptions (`500 Internal Server Error`).
* **Spring Profiles:** Demonstrates environment-specific configurations (`@Profile`) for different behaviors (e.g., `dev` vs `prod` messages via `EnvironmentService`).
* **Unit Testing:** Example unit tests using JUnit 5 and Mockito for isolated testing of service layer logic, demonstrating mocking dependencies and assertion.
* **Lombok Integration:** Reduces boilerplate code in entities (getters, setters, constructors, etc.) for cleaner and more concise code.


## Class Explanations

### `EducationSystemApplication.java`

* **Purpose:** The main class that bootstraps the Spring Boot application. When you run this class, Spring Boot starts up, configures all components, and embeds a web server (Tomcat by default).

### Controllers (e.g., `CollegeController.java`, `DepartmentController.java`)

* **Purpose:** Act as the entry points for your REST API. They receive HTTP requests, perform basic input validation, and then delegate the actual business logic to the corresponding service layer. They are responsible for returning appropriate HTTP status codes and response bodies.
* **Key Methods (Common across controllers):**
    * `saveXxx(@Valid @RequestBody Xxx xxx)`: Handles POST requests to create a new resource. The `@Valid` annotation ensures the request body adheres to the validation rules defined in the entity.
    * `saveAllXxx(@Valid @RequestBody List<Xxx> xxxes)`: (NEW) Handles POST requests to create multiple new resources in a single batch.
    * `getAllXxx()`: Handles GET requests to retrieve all resources.
    * `getXxxById(@PathVariable Long id)`: Handles GET requests to retrieve a resource by its ID.
    * `getXxxByName(@PathVariable String name)` (and similar by other unique fields like email, code): Handles GET requests to retrieve a resource by a specific unique attribute.
    * `updateXxx(@PathVariable Long id, @Valid @RequestBody Xxx xxx)`: Handles PUT requests to fully update an existing resource. It expects the entire resource object in the request body.
    * `patchXxx(@PathVariable Long id, @RequestBody Xxx xxx)`: Handles PATCH requests to partially update an existing resource. It only updates the fields provided in the request body.
    * `deleteXxx(@PathVariable Long id)`: Handles DELETE requests to remove a resource by its ID.

### Entities (e.g., `College.java`, `Department.java`, `Student.java`, `Teacher.java`)

* **Purpose:** Represent the data model of your application and are mapped to tables in the database. They contain fields that correspond to table columns and define relationships with other entities (e.g., Department has a ManyToOne relationship with College). They also include validation annotations for data integrity.

### Exceptions (`ErrorDetails.java`, `GlobalExceptionHandler.java`, `ResourceNotFoundException.java`)

* `ResourceNotFoundException.java`:
    * **Purpose:** A custom runtime exception used to signal that a requested resource (e.g., a College with a specific ID) could not be found in the database.
* `ErrorDetails.java`:
    * **Purpose:** A simple POJO (Plain Old Java Object) to define a standardized format for error responses sent back to the client. It includes a timestamp, a message, specific details (like the request URI), and the HTTP status code. This provides consistency for API consumers.
* `GlobalExceptionHandler.java`:
    * **Purpose:** This class uses `@RestControllerAdvice` to centralize exception handling across all controllers. Instead of each controller handling its own exceptions, this class catches specific exceptions and returns appropriate `ResponseEntity` objects with custom `ErrorDetails`.
    * **Key Methods:**
        * `handleResourceNotFoundException()`: Catches `ResourceNotFoundException` and returns a `404 Not Found` response with `ErrorDetails`.
        * `handleValidationExceptions()`: Catches `MethodArgumentNotValidException` (thrown when `@Valid` fails) and returns a `400 Bad Request` response, typically with a map of field errors.
        * `handleGlobalException()`: A fallback handler that catches any other unexpected `Exception` and returns a `500 Internal Server Error` with a generic message and logs the stack trace.
* **How it's used:**
    * **Service Layer:** When `findById()` or other query methods return an empty `Optional`, the service layer explicitly throws `new ResourceNotFoundException(...)`.
    * **Controller Layer:** When a `@Valid` annotation fails, Spring automatically throws `MethodArgumentNotValidException` before the controller method even executes.
    * **GlobalExceptionHandler:** Catches these exceptions and formats the response.
    * This approach separates error handling logic from core business logic in controllers and services, making the code cleaner and more maintainable.

### Profiles (`EnvironmentService.java`, `DevEnvironmentService.java`, `ProdEnvironmentService.java`)

* `EnvironmentService.java`:
    * **Purpose:** An interface defining a contract for providing environment-specific messages. This promotes polymorphism and clean architecture.
* `DevEnvironmentService.java`:
    * **Purpose:** An implementation of `EnvironmentService` that is active only when the `dev` Spring profile is active (defined in `application.properties`). It provides a development-specific message.
* `ProdEnvironmentService.java`:
    * **Purpose:** An implementation of `EnvironmentService` that is active only when the `prod` Spring profile is active. It provides a production-specific message.
* **Usage:** The `AppInfoController` uses `EnvironmentService` to dynamically return a message based on the active profile, showcasing how profiles can be used for environment-specific behaviors.

### Repositories (e.g., `CollegeRepository.java`, `DepartmentRepository.java`)

* **Purpose:** Provide the data access layer for your application. By extending `JpaRepository<Entity, IdType>`, Spring Data JPA automatically provides a rich set of CRUD operations (e.g., `save()`, `findById()`, `findAll()`, `deleteById()`) without writing any implementation code.
* **Custom Derived Query Methods:** You can also define custom query methods by simply declaring method signatures following Spring Data JPA's naming conventions (e.g., `findByName(String name)`, `findByCollegeCollegeId(Long collegeId)`). Spring Data JPA parses these names and generates the appropriate SQL queries.

### Services (e.g., `CollegeService.java` (interface), `CollegeServiceImpl.java` (implementation))

* **Purpose:** Encapsulate the core business logic of the application. They mediate between the controllers and the repositories. Services are responsible for:
    * Orchestrating multiple repository calls if an operation involves more than one entity.
    * Applying business rules and validations (beyond basic field validation).
    * Managing transactions using `@Transactional`.
    * Handling `ResourceNotFoundException` for non-existent entities.
    * Logging business-level operations.
* **Interface (`CollegeService.java`):** Defines the contract (methods) for the business logic, promoting loose coupling and making it easier to swap implementations or test.
* **Implementation (`CollegeServiceImpl.java`):** Contains the actual code for business operations.
* **Key Methods (Common across services):**
    * `saveXxx(Xxx xxx)`: Persists a single entity. For dependent entities (Department, Student, Teacher), it first verifies if the associated parent entity (College for Department, Department for Student/Teacher) exists before saving.
    * `saveAllXxx(List<Xxx> xxxes)`: (NEW) Persists a list of entities in a single transaction. Similar parent entity existence checks are performed for each item in the list.
    * `getAllXxx()`: Retrieves all entities.
    * `getXxxById(Long id)`: Retrieves an entity by ID, throwing `ResourceNotFoundException` if not found.
    * `updateXxx(Long id, Xxx xxx)`: Fully updates an existing entity. Fetches the existing entity, updates its properties from the provided object, and saves it. Throws `ResourceNotFoundException` if the original is not found.
    * `patchXxx(Long id, Xxx xxx)`: Partially updates an existing entity. It checks for null or empty fields in the provided `xxx` object and only updates those fields on the existing entity. This is useful for flexible updates.
    * `deleteXxx(Long id)`: Deletes an entity by ID, throwing `ResourceNotFoundException` if not found.
    * `getXxxBy...()`: Methods using custom repository queries to find entities by specific attributes (e.g., name, email, degree, associated foreign key IDs).

## Error Handling Explained

Your project implements a robust error handling mechanism using Spring's `@RestControllerAdvice` and custom exception classes.

* **Custom Exception (`ResourceNotFoundException`):**
    * Whenever an entity is requested by an ID or unique attribute, and it's not found in the database (e.g., `findById().orElseThrow()`), a `ResourceNotFoundException` is thrown.
* **Global Exception Handler (`GlobalExceptionHandler`):**
    * This class, annotated with `@RestControllerAdvice`, acts as a central error interceptor.
    * When `ResourceNotFoundException` is thrown from any service or controller, the `handleResourceNotFoundException` method in `GlobalExceptionHandler` catches it. It then constructs an `ErrorDetails` object (containing timestamp, specific message from the exception, request URI, and 404 status code) and returns it as a `ResponseEntity` with `HttpStatus.NOT_FOUND (404)`. This provides a user-friendly and consistent JSON error response.
    * Similarly, `MethodArgumentNotValidException` (triggered by `@Valid` and validation constraint violations) is caught by `handleValidationExceptions`, which extracts the specific field errors and returns a `400 Bad Request` response, typically with a map of field errors.
    * A generic `handleGlobalException` is in place to catch any other unforeseen `Exception`, logging them and returning a `500 Internal Server Error` to the client, preventing raw stack traces from being exposed.
* **How it's used:**
    * **Service Layer:** When `findById()` or other query methods return an empty `Optional`, the service layer explicitly throws `new ResourceNotFoundException(...)`.
    * **Controller Layer:** When a `@Valid` annotation fails, Spring automatically throws `MethodArgumentNotValidException` before the controller method even executes.
    * **GlobalExceptionHandler:** Catches these exceptions and formats the response.
    * This approach separates error handling logic from core business logic in controllers and services, making the code cleaner and more maintainable.


## Getting Started
=
## API Endpoints (with Sample Data)

Once the application is running, you can test the RESTful APIs using Postman.

### Colleges (`/colleges`)

* `POST /colleges/batch`: Create multiple colleges.
* `GET /colleges`: Get all colleges.
* `GET /colleges/{collegeId}`: Get college by ID.
* `GET /colleges/name/{name}`: Get college by name.
* `PUT /colleges/{collegeId}`: Update college by ID.
* `PATCH /colleges/{collegeId}`: Partially update college by ID.
* `DELETE /colleges/{collegeId}`: Delete college by ID.

#### Sample College Data for `POST /colleges/batch`

```json
[
  { "name": "Global Tech University", "address": "123 Innovation Drive, Tech City" },
  { "name": "City Arts & Humanities College", "address": "456 Culture Street, Artville" },
  { "name": "National Medical Institute", "address": "789 Health Avenue, Medville" },
  { "name": "Elite Business School", "address": "101 Leadership Way, Business Park" },
  { "name": "State Law College", "address": "202 Justice Lane, Lawton" }
]


### Departments (`/departments`)

* `POST /departments/batch`: Create multiple departments.
    * **Note:** When creating, link to an existing college using `"college": { "collegeId": {existingCollegeId} }`.
* `GET /departments`: Get all departments.
* `GET /departments/{departmentId}`: Get department by ID.
* `GET /departments/college/{collegeId}`: Get departments by college ID.
* `GET /departments/name/{name}`: Get department by name.
* `GET /departments/code/{code}`: Get department by code.
* `PUT /departments/{departmentId}`: Update department by ID.
* `PATCH /departments/{departmentId}`: Partially update department by ID.
* `DELETE /departments/{departmentId}`: Delete department by ID.

#### Sample Department Data for `POST /departments/batch` (Replace `{collegeId}` with actual IDs)

```json
[
  { "name": "Software Engineering", "code": "SE", "college": { "collegeId": 1 } },
  { "name": "Data Science", "code": "DS", "college": { "collegeId": 1 } },
  { "name": "Fine Arts", "code": "FA", "college": { "collegeId": 2 } },
  { "name": "Clinical Research", "code": "CR", "college": { "collegeId": 3 } },
  { "name": "Business Analytics", "code": "BA", "college": { "collegeId": 4 } }
]


### Teachers (`/teachers`)

* `POST /teachers/batch`: Create multiple teachers.
* `GET /teachers`: Get all teachers.
* `GET /teachers/{teacherId}`: Get teacher by ID.
* `GET /teachers/department/{departmentId}: Get teachers by department ID.
* `GET /teachers/name/{name}`: Get teacher by name.
* `GET /teachers/degree/{degree}`: Get teachers by degree.
* `PUT /teachers/{teacherId}`: Update teacher by ID.
* `PATCH /teachers/{teacherId}`: Partially update teacher by ID.
* `DELETE /teachers/{teacherId}`: Delete teacher by ID.

#### Sample Teacher Data for `POST /teachers/batch` (Replace `{departmentId}` with actual IDs)

```json
[
  { "name": "Dr. Alice Smith", "email": "alice.smith@example.com", "degree": "Ph.D. Computer Science", "department": { "departmentId": 201 } },
  { "name": "Prof. Bob Johnson", "email": "bob.johnson@example.com", "degree": "M.Sc. Data Science", "department": { "departmentId": 202 } },
  { "name": "Ms. Carol White", "email": "carol.white@example.com", "degree": "M.A. Fine Arts", "department": { "departmentId": 203 } }
]


### Students (`/students`)

* `POST /students/batch`: Create multiple students.
    * **Note:** When creating, link to an existing department using `"department": { "departmentId": {existingDepartmentId} }`.
* `GET /students`: Get all students.
* `GET /students/{studentId}`: Get student by ID.
* `GET /students/department/{departmentId}: Get students by department ID.
* `GET /students/name/{name}`: Get student by name.
* `GET /students/email/{email}`: Get student by email.
* `PUT /students/{studentId}`: Update student by ID.
* `PATCH /students/{studentId}`: Partially update student by ID.
* `DELETE /students/{studentId}`: Delete student by ID.

#### Sample Student Data for `POST /students/batch` (Replace `{departmentId}` with actual IDs)

```json
[
  { "name": "John Doe", "email": "john.doe@example.com", "major": "Software Engineering", "department": { "departmentId": 201 } },
  { "name": "Jane Roe", "email": "jane.roe@example.com", "major": "Data Science", "department": { "departmentId": 202 } },
  { "name": "Peter Pan", "email": "peter.pan@example.com", "major": "Fine Arts", "department": { "departmentId": 203 } }
]
