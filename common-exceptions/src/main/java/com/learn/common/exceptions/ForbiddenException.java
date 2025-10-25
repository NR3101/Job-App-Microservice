package com.learn.common.exceptions;

/**
 * Exception thrown when user lacks permission to access a resource
 * Maps to HTTP 403 Forbidden
 */
public class ForbiddenException extends BaseException {

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException() {
        super("Access forbidden. You don't have permission to access this resource.");
    }
}

