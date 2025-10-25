package com.learn.common.exceptions;

/**
 * Exception thrown when request data is invalid or malformed
 * Maps to HTTP 400 Bad Request
 */
public class BadRequestException extends BaseException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String resourceName, String reason) {
        super(String.format("Bad request for %s: %s", resourceName, reason));
    }
}

