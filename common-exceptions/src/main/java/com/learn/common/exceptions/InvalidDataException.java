package com.learn.common.exceptions;

/**
 * Exception thrown when data validation fails
 * Maps to HTTP 400 Bad Request
 */
public class InvalidDataException extends BaseException {

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String resourceName, String reason) {
        super(String.format("Invalid %s data: %s", resourceName, reason));
    }
}

