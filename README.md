# Dining Review API

A Spring Boot application for managing restaurant reviews with a focus on allergy information.

## Overview

The Dining Review API allows users to submit and browse restaurant reviews with specific information about allergy safety. Users can rate restaurants based on how well they accommodate peanut, egg, and dairy allergies, helping others with similar allergies make informed dining choices.

## Features

- **User Management**: Create and manage user profiles with allergy preferences
- **Restaurant Management**: Browse and add restaurants with location and cuisine type
- **Review System**: Submit reviews with allergy-specific ratings
- **Admin Moderation**: Reviews are moderated before being published
- **Automatic Scoring**: Restaurant allergy scores are automatically calculated from approved reviews
- **Pagination**: All list endpoints support pagination for better performance
- **API Documentation**: Full OpenAPI/Swagger documentation

## Tech Stack

- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Data JPA**
- **H2 Database** (in-memory for development)
- **Lombok**
- **Bean Validation**
- **SpringDoc OpenAPI**

## Project Structure

```
src/
└── main/
    └── java/
        └── com/
            └── project/
                └── Dinning/
                    ├── controllers/     # REST API endpoints
                    ├── models/          # JPA entities
                    ├── dto/             # Data Transfer Objects
                    ├── mappers/         # Entity-DTO converters
                    ├── services/        # Business logic
                    ├── repositories/    # Data access
                    ├── errors/          # Exception handling
                    ├── enums/           # Enumeration types
                    ├── config/          # Configuration classes
                    └── DinningApplication.java
```

## API Endpoints

### Users

- `GET /users/display-name/{displayName}` - Get a user by display name
- `POST /users` - Create a new user
- `PUT /users/{id}` - Update a user

### Restaurants

- `GET /restaurants` - Get restaurants with filtering and pagination
- `GET /restaurants/{id}` - Get a restaurant by ID
- `POST /restaurants` - Create a new restaurant

### Reviews

- `GET /reviews` - Get reviews with optional status filtering and pagination
- `GET /reviews/{id}` - Get a review by ID
- `GET /reviews/approved/{restaurant_id}` - Get approved reviews for a restaurant
- `POST /reviews` - Submit a new review
- `PUT /reviews/admin/{id}/{status}` - Update review status (admin)
- `PUT /reviews/admin/{id}/approve` - Approve a review (admin)
- `PUT /reviews/admin/{id}/reject` - Reject a review (admin)

## Data Models

### User

```java
@Entity
@Table(name = "USERS")
public class User {
    private Long id;
    private String displayName;
    private String city;
    private String zipCode;
    private boolean isPeanutAllergy;
    private boolean isEggAllergy;
    private boolean isDairyAllergy;
}
```

### Restaurant

```java
@Entity
@Table(name = "RESTAURANT")
public class Restaurant {
    private Long id;
    private String name;
    private String address;
    private String zipCode;
    private RestaurantType type;
    private Double peanutScore;
    private Double eggScore;
    private Double dairyScore;
}
```

### Review

```java
@Entity
@Table(name = "REVIEW")
public class Review {
    private Long id;
    private User user;
    private Restaurant restaurant;
    private Integer peanutScore;
    private Integer eggScore;
    private Integer dairyScore;
    private String commentary;
    private ReviewStatus status;
}
```

## Key Features Implementation

### DTOs for API Requests/Responses

The application uses DTOs to separate API contracts from domain models:

```java
public class ReviewDTO {
    private Long id;
    private Long userId;
    private Long restaurantId;
    private Integer peanutScore;
    private Integer eggScore;
    private Integer dairyScore;
    private String commentary;
}

public class ReviewResponseDTO {
    private Long id;
    private Long userId;
    private String userName;
    private Long restaurantId;
    private String restaurantName;
    private Integer peanutScore;
    private Integer eggScore;
    private Integer dairyScore;
    private String commentary;
    private ReviewStatus status;
}
```

### Global Exception Handling

Centralized error handling with consistent error responses:

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<Object> handleEntityNotFoundExceptions(EntityNotFound ex, WebRequest request) {
        // Create standardized error response
    }
    
    // Other exception handlers...
}
```

### Automatic Restaurant Score Calculation

When reviews are approved, restaurant scores are automatically recalculated:

```java
private void updateRestaurantScore(Long restaurantId) {
    // Calculate average scores from approved reviews
    // Update restaurant if scores have changed
}
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Running the Application

1. Clone the repository
   ```
   git clone https://github.com/yourusername/dining-review-api.git
   cd dining-review-api
   ```

2. Build the application
   ```
   mvn clean install
   ```

3. Run the application
   ```
   mvn spring-boot:run
   ```

4. Access the API
   - API: http://localhost:8080/
   - Swagger UI: http://localhost:8080/swagger-ui.html
   - H2 Console: http://localhost:8080/h2-console

## API Documentation

The API is documented using SpringDoc OpenAPI. You can access the Swagger UI at http://localhost:8080/swagger-ui.html when the application is running.

## Testing

Run the tests with:

```
mvn test
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- This project was created as part of the Codecademy Spring Boot course.