package com.adelsonsljunior.simpleecommerce.core.domain.dtos.sale;

import java.time.LocalDateTime;
import java.util.List;

public record SaleRequestDTO(
        Long userId,
        List<SaleProductDTO> saleProducts,
        LocalDateTime saleDate
) {
}
