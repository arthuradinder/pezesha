package com.arthurprojects.pezesha.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Schema(description = "Account Data Transfer Object")
public class AccountDTO {
    private Long id;

    @Schema(description = "Account holder's name", example = "John Doe")
    @NotBlank(message = "Account holder name is required")
    private String accountName;

    @NotNull(message = "Initial balance is required")
    @PositiveOrZero(message = "Initial balance must be zero or more")
    @Schema(description = "Initial account balance", example = "1000.00")
    private BigDecimal balance;

    /**
     * Version for optimistic locking.
     */
    private Long version; // For optimistic locking

}
