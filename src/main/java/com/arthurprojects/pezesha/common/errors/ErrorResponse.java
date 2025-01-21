package com.arthurprojects.pezesha.common.errors;

import java.time.LocalDateTime;

public record ErrorResponse(
        int errorCode,
        String message,
        String description,
        ErrorType errorType,
        LocalDateTime timestamp
) {
    public ErrorResponse(int errorCode, String message, String description, ErrorType errorType, LocalDateTime timestamp) {
        this.errorCode = errorCode;
        this.message = message;
        this.description = description;
        this.errorType = errorType;
        this.timestamp = timestamp;
    }
}
