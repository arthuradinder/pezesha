package com.arthurprojects.pezesha.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class AccountDTO {
    private Long id;

    @NotBlank(message = "Account holder name is required")
    private String accountName;

    @NotNull(message = "Initial balance is required")
    @PositiveOrZero(message = "Initial balance must be zero or more")
    private BigDecimal balance;

    private Long version; // For optimistic locking

}
