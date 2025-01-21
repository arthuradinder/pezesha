package com.arthurprojects.pezesha.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ErrorResponseBuilder {
    public static ResponseEntity<Error> buildErrorResponse(
            HttpStatus status,
            String message,
            String details,
            HttpServletRequest request) {

        Error error = Error.builder()
                .code(String.valueOf(status.value()))
                .message(message)
                .details(details)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, status);
    }

    public static ResponseEntity<Error> buildErrorResponse(
            HttpStatus status,
            String message,
            HttpServletRequest request) {

        return buildErrorResponse(status, message, null, request);
    }
}
