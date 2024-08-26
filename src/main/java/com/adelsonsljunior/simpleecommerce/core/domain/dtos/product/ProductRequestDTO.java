package com.adelsonsljunior.simpleecommerce.core.domain.dtos.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductRequestDTO(
        @NotBlank(message = "{product.name.not.blank}")
        @Size(max = 100, message = "{product.name.size}")
        String name,
        @NotBlank(message = "{product.description.not.blank}")
        @Size(max = 500, message = "{product.description.size}")
        String description,
        @Positive(message = "{product.quantity.positive}")
        int quantity,
        @Positive(message = "{product.price.positive}")
        double price
) {
}
