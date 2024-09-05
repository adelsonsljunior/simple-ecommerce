package com.adelsonsljunior.simpleecommerce.core.domain.ports.repositories;

import com.adelsonsljunior.simpleecommerce.core.domain.Product;

import java.util.List;

public interface ProductRepositoryPort {

    List<Product> findAll();
    Product create(Product product);
    void delete(Long id);
    Product update(Product product);
    Product findById(Long id);
    void decrementStock(Long productId, int quantity);
    void incrementStock(Long productId, int quantity);

}
