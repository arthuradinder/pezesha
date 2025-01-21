package com.arthurprojects.pezesha.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Money transfer request")
public class TransferDTO {
    @Schema(description = "Source account ID", example = "1")
    @NotNull(message = "Source account ID is required")
    private Long sourceAccountId;

    @Schema(description = "Destination account ID", example = "2")
    @NotNull(message = "Destination account ID is required")
    private Long destinationAccountId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Transfer Amount must be greater than zero")
    @Schema(description = "Amount to transfer", example = "500.00")
    private BigDecimal amount;

    private String description;
}
