package com.adelsonsljunior.simpleecommerce.core.domain.ports.services;

import com.adelsonsljunior.simpleecommerce.core.domain.dtos.sale.SaleRequestDTO;
import com.adelsonsljunior.simpleecommerce.core.domain.dtos.sale.SaleResponseDTO;

import java.util.List;

public interface SaleServicePort {

    SaleResponseDTO create(SaleRequestDTO sale);
    List<SaleResponseDTO> findAll();
    SaleResponseDTO findById(Long id);
    SaleResponseDTO update(Long id, SaleRequestDTO sale);
    void delete(Long id);

}
