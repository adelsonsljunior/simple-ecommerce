package com.adelsonsljunior.simpleecommerce.core.domain.dtos.product;

import java.time.LocalDateTime;

public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        int quantity,
        double price,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
