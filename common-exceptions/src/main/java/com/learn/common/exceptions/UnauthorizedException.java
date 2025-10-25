package com.learn.common.exceptions;

/**
 * Exception thrown when user is not authenticated
 * Maps to HTTP 401 Unauthorized
 */
public class UnauthorizedException extends BaseException {

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException() {
        super("Unauthorized access. Please authenticate.");
    }
}

