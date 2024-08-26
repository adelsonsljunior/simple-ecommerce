package com.adelsonsljunior.simpleecommerce.core.domain.dtos.product;

public record ProductRequestDTO(
        String name,
        String description,
        int quantity,
        double price
) {
}
