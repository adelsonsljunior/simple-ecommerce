package com.adelsonsljunior.simpleecommerce.infra.adapters.repositories;

import com.adelsonsljunior.simpleecommerce.core.domain.Product;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.repositories.ProductRepositoryPort;
import com.adelsonsljunior.simpleecommerce.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductRepository implements ProductRepositoryPort {

    private final SpringProductRepository springProductRepository;

    public ProductRepository(SpringProductRepository springProductRepository) {
        this.springProductRepository = springProductRepository;
    }

    @Override
    public List<Product> findAll() {
        return springProductRepository.findAllActive();
    }

    @Override
    public Product create(Product product) {
        return this.springProductRepository.save(product);
    }

    @Override
    public void delete(Long id) {

        this.springProductRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        this.springProductRepository.softDeleteById(id);
    }

    @Override
    public Product update(Product product) {
        return this.springProductRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return this.springProductRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

}
