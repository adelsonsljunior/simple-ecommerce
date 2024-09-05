package com.adelsonsljunior.simpleecommerce.infra.adapters.repositories;

import com.adelsonsljunior.simpleecommerce.core.domain.Sale;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.repositories.SaleRepositoryPort;
import com.adelsonsljunior.simpleecommerce.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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
    public void delete(Long saleId) {

        this.springSaleRepository.findByIdActive(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));

        this.springSaleRepository.softDelete(saleId);
    }

    @Override
    public Sale findById(Long saleId) {
        return this.springSaleRepository.findByIdActive(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));
    }

    @Override
    public List<Sale> findByDate(LocalDate date) {
        return this.springSaleRepository.findByDate(date);
    }

    @Override
    public List<Sale> findByMonth(int month, int year) {
        return this.springSaleRepository.findByMonth(month, year);
    }

    @Override
    public List<Sale> findByCurrentWeek(int year, int week) {
        return this.springSaleRepository.findByCurrentWeek(year, week);
    }
}
