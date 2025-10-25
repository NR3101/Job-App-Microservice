# Common Exceptions Library

A shared exception handling library for microservices architecture. This library provides consistent error handling and response formatting across all services.

## Features

- **Standardized Exception Classes**: Common exception types that extend a base exception
- **Global Exception Handler**: Centralized exception handling with `@RestControllerAdvice`
- **Consistent Error Response**: Uniform error response format across all microservices
- **Service Identification**: Each error response includes the service name that generated it
- **Validation Support**: Built-in handling for Spring validation errors

## Installation

### Step 1: Build and Install the Library

From the `common-exceptions` directory, run:

```bash
cd common-exceptions
mvn clean install
```

This will build the library and install it to your local Maven repository.

### Step 2: Add Dependency to Microservices

Add this dependency to the `pom.xml` of each microservice (job-service, company-service, review-service):

```xml
<dependency>
    <groupId>com.learn</groupId>
    <artifactId>common-exceptions</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Step 3: Update Package Imports

In your microservices, update the imports from:

```java
import com.learn.jobapp.exceptions.*;
```

to:

```java
import com.learn.common.exceptions.*;
```

### Step 4: Configure Service Name

In each microservice's `application.yml`, add:

```yaml
spring:
  application:
    name: job-service  # or company-service, review-service
```

## Available Exceptions

### 1. ResourceNotFoundException (404)
Used when a requested resource doesn't exist.

```java
throw new ResourceNotFoundException("Job", "id", jobId);
throw new ResourceNotFoundException("Job", 123L);
throw new ResourceNotFoundException("Job not found");
```

### 2. ResourceAlreadyExistsException (409)
Used when attempting to create a duplicate resource.

```java
throw new ResourceAlreadyExistsException("Company", "name", companyName);
throw new ResourceAlreadyExistsException("Company already exists");
```

### 3. BadRequestException (400)
Used for malformed or invalid requests.

```java
throw new BadRequestException("Job", "Missing required fields");
throw new BadRequestException("Invalid request format");
```

### 4. InvalidDataException (400)
Used for data validation failures.

```java
throw new InvalidDataException("Review", "Rating must be between 1 and 5");
throw new InvalidDataException("Invalid rating value");
```

### 5. UnauthorizedException (401)
Used when authentication is required.

```java
throw new UnauthorizedException("Please login to access this resource");
throw new UnauthorizedException(); // Uses default message
```

### 6. ForbiddenException (403)
Used when user lacks permission.

```java
throw new ForbiddenException("You don't have permission to delete this job");
throw new ForbiddenException(); // Uses default message
```

### 7. ServiceUnavailableException (503)
Used when a dependent service is down.

```java
throw new ServiceUnavailableException("Company Service", "Connection timeout");
throw new ServiceUnavailableException("External API is temporarily unavailable");
```

## Error Response Format

All exceptions return a standardized JSON response:

```json
{
  "timestamp": "2025-10-25T10:30:00",
  "status": 404,
  "error": "Resource Not Found",
  "message": "Job not found with id: 123",
  "path": "/api/jobs/123",
  "service": "job-service",
  "validationErrors": null
}
```

For validation errors:

```json
{
  "timestamp": "2025-10-25T10:30:00",
  "status": 400,
  "error": "Validation Failed",
  "message": "Invalid input data. Please check the fields.",
  "path": "/api/jobs",
  "service": "job-service",
  "validationErrors": {
    "title": "Title cannot be empty",
    "minSalary": "Minimum salary must be greater than 0"
  }
}
```

## Exception Hierarchy

```
RuntimeException
    └── BaseException (abstract)
        ├── ResourceNotFoundException
        ├── ResourceAlreadyExistsException
        ├── BadRequestException
        ├── InvalidDataException
        ├── UnauthorizedException
        ├── ForbiddenException
        └── ServiceUnavailableException
```

## Usage in Microservices

### Example: Job Service

```java
package com.learn.jobservice.service;

import com.learn.common.exceptions.ResourceNotFoundException;
import com.learn.common.exceptions.InvalidDataException;

@Service
public class JobServiceImpl implements JobService {
    
    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Job", id));
    }
    
    @Override
    public Job createJob(Job job) {
        if (job.getMinSalary() < 0) {
            throw new InvalidDataException("Job", "Salary cannot be negative");
        }
        return jobRepository.save(job);
    }
}
```

## Component Scanning

The `GlobalExceptionHandler` is automatically picked up by Spring's component scanning if your main application class is in a parent package (e.g., `com.learn.jobservice`).

If needed, explicitly enable scanning:

```java
@SpringBootApplication
@ComponentScan(basePackages = {"com.learn.jobservice", "com.learn.common.exceptions"})
public class JobServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobServiceApplication.class, args);
    }
}
```

## Extending the Library

### Adding Custom Exceptions

Create new exception classes in your microservice that extend `BaseException`:

```java
package com.learn.jobservice.exceptions;

import com.learn.common.exceptions.BaseException;

public class JobExpiredException extends BaseException {
    public JobExpiredException(Long jobId) {
        super(String.format("Job with id %s has expired", jobId));
    }
}
```

### Handling Custom Exceptions

Add handlers in your microservice's `@RestControllerAdvice`:

```java
@RestControllerAdvice
public class JobServiceExceptionHandler {
    
    @ExceptionHandler(JobExpiredException.class)
    public ResponseEntity<ErrorResponse> handleJobExpiredException(
            JobExpiredException ex,
            HttpServletRequest request) {
        // Custom handling logic
    }
}
```

## Testing

Example test for exception handling:

```java
@Test
void testResourceNotFoundException() {
    assertThrows(ResourceNotFoundException.class, () -> {
        jobService.getJobById(999L);
    });
}
```

## Version History

- **1.0.0** - Initial release with core exception types and global handler

## License

Internal use only - Part of the Job Application microservices project

