package com.adelsonsljunior.simpleecommerce.core.domain.ports.repositories;

import com.adelsonsljunior.simpleecommerce.core.domain.Product;

import java.util.List;

public interface ProductRepositoryPort {

    List<Product> findAll();
    Product create(Product product);
    void delete(Long productId);
    Product update(Product product);
    Product findById(Long productId);
    void decrementStock(Long productId, int quantity);
    void incrementStock(Long productId, int quantity);

}
