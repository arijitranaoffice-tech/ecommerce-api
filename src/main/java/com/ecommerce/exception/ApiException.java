package com.ecommerce.exception;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Base exception class for the application.
 */
@Getter
public class ApiException extends RuntimeException {

    private final String message;
    private final LocalDateTime timestamp;

    public ApiException(String message) {
        super(message);
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
