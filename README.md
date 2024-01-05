# Social Media Platform API

A Spring Boot-based RESTful API service for a basic social media platform.

## Table of Contents

- [Description](#description)
- [Features](#features)
- [Requirements](#requirements)
- [Getting Started](#getting-started)
  - [Build and Run](#build-and-run)
  - [Database Configuration](#database-configuration)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
  - [User Registration Test](#user-registration-test)
  - [User Authentication Test](#user-authentication-test)
  - [Post Service Tests](#post-service-tests)
  - [Comment Service Tests](#comment-service-tests)
  - [Like Service Tests](#like-service-tests)
- [Error Handling](#error-handling)
- [Unit Tests](#unit-tests)

## Description

This project is a Spring Boot-based RESTful API service for a basic social media platform. It provides endpoints for user registration, authentication, post creation, comment addition, post liking, and more. The application can use an in-memory data source or a legacy database for storing data.

## Features

- User registration and authentication
- Post creation, retrieval, update, and deletion
- Comment addition to posts
- Liking posts
- Pagination for retrieving posts

## Requirements

- Java 8 or higher
- Maven
- A database (H2 used for persistence)

## Getting Started

### Build and Run

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/social-media-platform.git

 2.  **Build the project:**
```bash
mvn clean install
```

3. **Run the application:**

```bash
mvn spring-boot:run
```
The application will be accessible at http://localhost:8185.

## Database Configuration
Database properties are configured under application.properties in the src/main/resources directory.

```bash
spring.h2.console.enabled=true
spring.h2.console.path=/h2-ui
 
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
 
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
server.port:8185
```

## API Endpoints
1. User Registration:
    POST /api/users
   
2. User Authentication:
   POST /api/auth
   
3. Retrieve User Profile:
    GET /api/users/{userId}

4. Create a New Post:
    POST /api/posts
   
5.Retrieve All Posts with Pagination:
    GET /api/posts
    
6. Retrieve a Specific Post:
    GET /api/posts/{postId}

7. Update a Specific Post:
    PUT /api/posts/{postId}

8. Delete a Specific Post:
    DELETE /api/posts/{postId}
  
9. Add a Comment to a Post:
    POST /api/posts/{postId}/comments

10. Like a Post:
    PUT /api/posts/{postId}/likes

## Testing
To test the API, you can use tools like Postman.

# User Registration Test
Scenario: Valid user registration.

Steps:
`Send a POST request to /api/users with valid user registration data.`
`Verify that the response status code is 201 (Created).`
`Check if the user is correctly added to the database.`

Scenario: Attempt to register with an existing username.

Steps:
`Send a POST request to /api/users with an existing username.`
`Verify that the response status code is 400 (Bad Request).`
`Check that the response body contains an appropriate error message.`

# User Authentication Test
Scenario: Valid user authentication.

Steps:
`Send a POST request to /api/auth with valid credentials.`
`Verify that the response status code is 200 (OK).`
`Check that the response contains a valid authentication token.`

Scenario: Invalid user authentication (wrong password).

Steps:
`Send a POST request to /api/auth with incorrect password.`
`Verify that the response status code is 401 (Unauthorized).`
`Check that the response body contains an appropriate error message.`

# Post Service Tests

# Create Post Test
Scenario: Valid post creation.

Steps:
`Send a POST request to /api/posts with valid post data.`
`Verify that the response status code is 201 (Created).`
`Check if the post is correctly added to the database.`

Scenario: Attempt to create a post with missing required fields.

Steps:
`Send a POST request to /api/posts with incomplete post data.`
`Verify that the response status code is 400 (Bad Request).`
`Check that the response body contains an appropriate error message.`

# Update Post Test
Scenario: Valid post update.

Steps:
`Send a PUT request to /api/posts/{postId} with valid updated data.`
`Verify that the response`

## Error Handling

The application incorporates global error handling to deliver meaningful error responses for various scenarios, as implemented in the following files:

1. `ResourceNotFoundException.java`
2. `UnauthorizedException.java`
3. `CustomExceptionHandler.java`
4. `ApiError.java`

## Unit Tests

The project is equipped with unit tests that validate the correctness of the application logic. Execute these tests using:

1. `UserControllerTest.java`
2. `PostControllerTest.java`

These tests are available under `src/test/java`. Right-click and select "Run as JUnit Test" to execute them.






