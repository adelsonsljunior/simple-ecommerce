package com.adelsonsljunior.simpleecommerce.core.domain.ports.services;

import com.adelsonsljunior.simpleecommerce.core.domain.dtos.product.ProductRequestDTO;
import com.adelsonsljunior.simpleecommerce.core.domain.dtos.product.ProductResponseDTO;

import java.util.List;

public interface ProductServicePort {
    List<ProductResponseDTO> findAll();
    ProductResponseDTO create(ProductRequestDTO product);
    void delete(Long id);
    ProductResponseDTO update(Long id, ProductRequestDTO product);
    ProductResponseDTO findById(Long id);
}
