package com.learn.common.exceptions;

/**
 * Exception thrown when a dependent service is unavailable
 * Maps to HTTP 503 Service Unavailable
 */
public class ServiceUnavailableException extends BaseException {

    public ServiceUnavailableException(String message) {
        super(message);
    }

    public ServiceUnavailableException(String serviceName, String reason) {
        super(String.format("Service '%s' is unavailable: %s", serviceName, reason));
    }
}

