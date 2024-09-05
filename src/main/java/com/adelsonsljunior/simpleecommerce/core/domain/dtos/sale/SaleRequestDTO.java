package com.adelsonsljunior.simpleecommerce.core.domain.dtos.sale;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record SaleRequestDTO(
        @NotNull(message = "{sale.user.id.not.null}")
        Long userId,
        @NotEmpty(message = "{sale.saleProducts.not.empty}")
        @Valid
        List<SaleProductDTO> saleProducts,
        @NotNull(message = "{sale.saleDate.not.null}")
        LocalDateTime saleDate
) {
}
