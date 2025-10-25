package com.learn.common.exceptions;

/**
 * Exception thrown when a requested resource is not found
 * Maps to HTTP 404 Not Found
 */
public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
    }

    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("%s not found with id: %s", resourceName, id));
    }
}

