package com.adelsonsljunior.simpleecommerce.core.domain.dtos.sale;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record SaleResponseDTO(
        Long id,
        Long userId,
        double totalAmount,
        List<SaleProductDTO> saleProducts,
        LocalDate saleDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
