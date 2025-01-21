package com.arthurprojects.pezesha.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferResponseDTO(
        Long id,
        Long sourceAccountId,
        Long destinationAccountId,
        BigDecimal amount,
        LocalDateTime timestamp,
        String description,
        String transferStatus
) {}
