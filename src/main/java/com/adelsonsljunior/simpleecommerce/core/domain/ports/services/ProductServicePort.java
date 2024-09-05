package com.adelsonsljunior.simpleecommerce.core.domain.ports.services;

import com.adelsonsljunior.simpleecommerce.core.domain.dtos.product.ProductRequestDTO;
import com.adelsonsljunior.simpleecommerce.core.domain.dtos.product.ProductResponseDTO;

import java.util.List;

public interface ProductServicePort {
    List<ProductResponseDTO> findAll();
    ProductResponseDTO create(ProductRequestDTO productRequest);
    void delete(Long productId);
    ProductResponseDTO update(Long id, ProductRequestDTO productRequest);
    ProductResponseDTO findById(Long productId);
}
