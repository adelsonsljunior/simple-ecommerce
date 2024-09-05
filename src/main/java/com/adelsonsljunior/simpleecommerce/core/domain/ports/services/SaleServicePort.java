package com.adelsonsljunior.simpleecommerce.core.domain.ports.services;

import com.adelsonsljunior.simpleecommerce.core.domain.dtos.sale.SaleRequestDTO;
import com.adelsonsljunior.simpleecommerce.core.domain.dtos.sale.SaleResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface SaleServicePort {

    SaleResponseDTO create(SaleRequestDTO saleRequest);

    List<SaleResponseDTO> findAll();

    SaleResponseDTO findById(Long saleId);

    SaleResponseDTO update(Long saleId, SaleRequestDTO saleRequest);

    void delete(Long saleId);

    List<SaleResponseDTO> findByDate(LocalDate date);

    List<SaleResponseDTO> findByMonth(int month, int year);

    List<SaleResponseDTO> findByCurrentWeek();
}
