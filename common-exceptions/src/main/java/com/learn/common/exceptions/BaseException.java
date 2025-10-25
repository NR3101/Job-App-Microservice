package com.learn.common.exceptions;

/**
 * Base exception class for all custom exceptions in the system
 * Provides common functionality for all business exceptions
 */
public abstract class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

