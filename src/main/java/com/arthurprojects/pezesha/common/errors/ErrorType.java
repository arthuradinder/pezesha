package com.arthurprojects.pezesha.common.errors;

public enum ErrorType {
    VALIDATION(400, "Validation error, request data is incorrect"),
    UNAUTHORIZED(401, "Unauthorized access, authentication required"),
    FORBIDDEN(403, "Access forbidden, you do not have permission to access this resource"),
    NOT_FOUND(404, "Resource not found"),
    CONFLICT(409, "Conflict, data already exists or other conflicts"),
    UNEXPECTED(500, "Unexpected error occurred on the server"),
    TOO_MANY_REQUESTS(429, "Too many requests, rate limit exceeded"),
    CUSTOM(-1, "Custom error, specific error not predefined");

    private final int statusCode;
    private final String description;

    ErrorType(int statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getDescription() {
        return description;
    }
}