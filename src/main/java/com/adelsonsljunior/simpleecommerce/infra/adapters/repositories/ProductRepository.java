package com.adelsonsljunior.simpleecommerce.infra.adapters.repositories;

import com.adelsonsljunior.simpleecommerce.core.domain.Product;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.repositories.ProductRepositoryPort;
import com.adelsonsljunior.simpleecommerce.exceptions.InsufficientStockException;
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
    public void delete(Long productId) {

        this.springProductRepository.findByIdActive(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for id: " + productId));

        this.springProductRepository.softDeleteById(productId);
    }

    @Override
    public Product update(Product product) {
        return this.springProductRepository.save(product);
    }

    @Override
    public Product findById(Long productId) {
        return this.springProductRepository.findByIdActive(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for id: " + productId));
    }

    @Override
    public void decrementStock(Long productId, int quantity) {

        int affectedRows = this.springProductRepository.decrementProductStock(productId, quantity);

        if (affectedRows == 0) {
            // Se nenhuma linha foi afetada ou a quantidade solicitada
            // é maior do que o estoque disponível
            throw new InsufficientStockException("Not enough stock for product id: " + productId);
        }
    }

    @Override
    public void incrementStock(Long productId, int quantity) {
        int updatedRows = this.springProductRepository.incrementStock(productId, quantity);
        if (updatedRows == 0) {
            throw new ResourceNotFoundException("Product could not be restocked for id: " + productId);
        }
    }

}
