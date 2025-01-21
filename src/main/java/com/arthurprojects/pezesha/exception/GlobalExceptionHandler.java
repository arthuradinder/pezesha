package com.arthurprojects.pezesha.exception;

import com.arthurprojects.pezesha.common.errors.ErrorResponse;
import com.arthurprojects.pezesha.common.errors.ErrorType;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * Global exception handler for handling custom exceptions and sending structured error responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles InsufficientBalanceException and returns a detailed response with a BAD_REQUEST status.
     *
     * @param ex The exception thrown.
     * @return A ResponseEntity containing the error message and HTTP status.
     */
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientBalanceException(InsufficientBalanceException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorType.VALIDATION.getStatusCode(),
                ex.getMessage(),
                ErrorType.VALIDATION.getDescription(),
                ErrorType.VALIDATION,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(ErrorType.VALIDATION.getStatusCode()));
    }

    /**
     * Handles ResourceNotFoundException and returns a detailed response with a NOT_FOUND status.
     *
     * @param ex The exception thrown.
     * @return A ResponseEntity containing the error message and HTTP status.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorType.NOT_FOUND.getStatusCode(),
                ex.getMessage(),
                ErrorType.NOT_FOUND.getDescription(),
                ErrorType.NOT_FOUND,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(ErrorType.NOT_FOUND.getStatusCode()));
    }

    /**
     * Handles all other exceptions and returns a generic error message with an INTERNAL_SERVER_ERROR status.
     *
     * @param ex The exception thrown.
     * @return A ResponseEntity containing a generic error message and HTTP status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorType.UNEXPECTED.getStatusCode(),
                "An unexpected error occurred.",
                ex.getMessage(),
                ErrorType.UNEXPECTED,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(ErrorType.UNEXPECTED.getStatusCode()));
    }


}
