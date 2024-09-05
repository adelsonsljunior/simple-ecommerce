package com.adelsonsljunior.simpleecommerce.core.domain.dtos.sale;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SaleProductDTO(
        @NotNull(message = "{sale.products.productId.not.null}")
        Long productId,
        @Positive(message = "{sale.products.quantity.positive}")
        int quantity
) {
}
