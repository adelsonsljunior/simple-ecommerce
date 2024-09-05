package com.adelsonsljunior.simpleecommerce.infra.adapters.repositories;

import com.adelsonsljunior.simpleecommerce.core.domain.Sale;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.repositories.SaleRepositoryPort;
import com.adelsonsljunior.simpleecommerce.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SaleRepository implements SaleRepositoryPort {

    private final SpringSaleRepository springSaleRepository;

    public SaleRepository(SpringSaleRepository springSaleRepository) {
        this.springSaleRepository = springSaleRepository;
    }

    @Override
    public List<Sale> findAll() {
        return this.springSaleRepository.findAllActive();
    }

    @Override
    public Sale create(Sale sale) {
        return this.springSaleRepository.save(sale);
    }

    @Override
    public Sale update(Sale sale) {
        return this.springSaleRepository.save(sale);
    }

    @Override
    public void delete(Long id) {

        this.springSaleRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));

        this.springSaleRepository.softDelete(id);
    }

    @Override
    public Sale findById(Long id) {
        return this.springSaleRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));
    }
}
