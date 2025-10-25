package com.learn.common.exceptions;

/**
 * Exception thrown when attempting to create a resource that already exists
 * Maps to HTTP 409 Conflict
 */
public class ResourceAlreadyExistsException extends BaseException {

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

    public ResourceAlreadyExistsException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s already exists with %s: '%s'", resourceName, fieldName, fieldValue));
    }

    public ResourceAlreadyExistsException(String resourceName, Long id) {
        super(String.format("%s already exists with id: %s", resourceName, id));
    }
}

