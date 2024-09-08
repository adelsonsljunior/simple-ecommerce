package com.adelsonsljunior.simpleecommerce.infra.adapters.repositories;

import com.adelsonsljunior.simpleecommerce.core.domain.Product;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.repositories.ProductRepositoryPort;
import com.adelsonsljunior.simpleecommerce.exceptions.InsufficientStockException;
import com.adelsonsljunior.simpleecommerce.exceptions.ResourceNotFoundException;
import com.adelsonsljunior.simpleecommerce.infra.entities.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductRepository implements ProductRepositoryPort {

    private final SpringProductRepository springProductRepository;

    public ProductRepository(SpringProductRepository springProductRepository) {
        this.springProductRepository = springProductRepository;
    }

    @Override
    public List<Product> findAll() {
        List<ProductEntity> products = springProductRepository.findAllActive();
        return products.stream()
                .map(ProductEntity::toProduct)
                .collect(Collectors.toList());
    }

    @Override
    public Product create(Product product) {
        ProductEntity productEntity = new ProductEntity(product);
        ProductEntity createdProduct = springProductRepository.save(productEntity);
        return createdProduct.toProduct();
    }

    @Override
    public void delete(Long productId) {

        this.springProductRepository.findByIdActive(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for id: " + productId));

        this.springProductRepository.softDeleteById(productId);
    }

    @Override
    public Product update(Product product) {
        ProductEntity productEntity = new ProductEntity(product);
        ProductEntity updatedProduct = springProductRepository.save(productEntity);
        return updatedProduct.toProduct();
    }

    @Override
    public Product findById(Long productId) {
        ProductEntity foundProduct = this.springProductRepository.findByIdActive(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for id: " + productId));

        return foundProduct.toProduct();
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
