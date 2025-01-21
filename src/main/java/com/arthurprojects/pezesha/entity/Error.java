package com.arthurprojects.pezesha.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Error response details")
public class Error {
    @Schema(description = "Error message", example = "Account not found")
    private String message;

    @Schema(description = "HTTP status code", example = "404")
    private String code;

    @Schema(description = "Detailed error description", example = "Account with ID 123 was not found in the system")
    private String details;

    @Schema(description = "Timestamp when the error occurred", example = "2024-01-20T10:30:45.123Z")
    private LocalDateTime timestamp;

    @Schema(description = "Request path that caused the error", example = "/api/accounts/123")
    private String path;
}
