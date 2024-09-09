package com.adelsonsljunior.simpleecommerce.infra.adapters.repositories;

import com.adelsonsljunior.simpleecommerce.core.domain.Sale;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.repositories.SaleRepositoryPort;
import com.adelsonsljunior.simpleecommerce.exceptions.ResourceNotFoundException;
import com.adelsonsljunior.simpleecommerce.infra.entities.SaleEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SaleRepository implements SaleRepositoryPort {

    private final SpringSaleRepository springSaleRepository;

    public SaleRepository(SpringSaleRepository springSaleRepository) {
        this.springSaleRepository = springSaleRepository;
    }

    @Override
    public List<Sale> findAll() {
        List<SaleEntity> sales = this.springSaleRepository.findAllActive();

        return sales.stream()
                .map(SaleEntity::toSale)
                .collect(Collectors.toList());
    }

    @Override
    public Sale create(Sale sale) {
        SaleEntity saleEntity = new SaleEntity(sale);
        SaleEntity createdSale = this.springSaleRepository.save(saleEntity);
        return createdSale.toSale();
    }

    @Override
    public Sale update(Sale sale) {
        SaleEntity saleEntity = new SaleEntity(sale);
        SaleEntity updatedSale = this.springSaleRepository.save(saleEntity);
        return updatedSale.toSale();
    }

    @Override
    public void delete(Long saleId) {

        this.springSaleRepository.findByIdActive(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found for id: " + saleId));

        this.springSaleRepository.softDelete(saleId);
    }

    @Override
    public Sale findById(Long saleId) {
        SaleEntity foundSale = this.springSaleRepository.findByIdActive(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found for id: " + saleId));

        return foundSale.toSale();
    }

    @Override
    public List<Sale> findByDate(LocalDate date) {
        List<SaleEntity> sales = this.springSaleRepository.findByDate(date);
        return sales.stream()
                .map(SaleEntity::toSale)
                .collect(Collectors.toList());
    }

    @Override
    public List<Sale> findByMonth(int month, int year) {
        List<SaleEntity> sales = this.springSaleRepository.findByMonth(month, year);
        return sales.stream()
                .map(SaleEntity::toSale)
                .collect(Collectors.toList());
    }

    @Override
    public List<Sale> findByCurrentWeek(int year, int week) {
        List<SaleEntity> sales = this.springSaleRepository.findByCurrentWeek(year, week);
        return sales.stream()
                .map(SaleEntity::toSale)
                .collect(Collectors.toList());
    }
}
