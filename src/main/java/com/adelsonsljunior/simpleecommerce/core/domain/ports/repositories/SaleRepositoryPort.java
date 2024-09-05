package com.adelsonsljunior.simpleecommerce.core.domain.ports.repositories;

import com.adelsonsljunior.simpleecommerce.core.domain.Sale;

import java.util.List;

public interface SaleRepositoryPort {

    List<Sale> findAll();
    Sale create(Sale sale);
    Sale update(Sale sale);
    void delete(Long id);
    Sale findById(Long id);
}
