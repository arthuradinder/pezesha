package com.arthurprojects.pezesha.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponseDTO(
        Long id,
        String accountName,
        BigDecimal balance,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
