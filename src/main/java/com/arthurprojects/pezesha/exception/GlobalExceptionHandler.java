package com.arthurprojects.pezesha.exception;

import com.arthurprojects.pezesha.util.ErrorResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.AccountNotFoundException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Error> handleAccountNotFoundException(
            AccountNotFoundException ex,
            HttpServletRequest request) {

        return ErrorResponseBuilder.buildErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                "The requested account could not be found in the system",
                request
        );
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<Error> handleInsufficientBalanceException(
            InsufficientBalanceException ex,
            HttpServletRequest request) {

        return ErrorResponseBuilder.buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                "The account has insufficient balance for this transaction",
                request
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ErrorResponseBuilder.buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                details,
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        return ErrorResponseBuilder.buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred",
                ex.getMessage(),
                request
        );
    }
}
