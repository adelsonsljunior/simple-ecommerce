package com.adelsonsljunior.simpleecommerce.core.domain.ports.repositories;

import com.adelsonsljunior.simpleecommerce.core.domain.Sale;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepositoryPort {

    List<Sale> findAll();

    Sale create(Sale sale);

    Sale update(Sale sale);

    void delete(Long saleId);

    Sale findById(Long saleId);

    List<Sale> findByDate(LocalDate date);

    List<Sale> findByMonth(int month, int year);

    List<Sale> findByCurrentWeek(int year, int week);
}
