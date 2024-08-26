package com.adelsonsljunior.simpleecommerce.core.domain.ports.repositories;

import com.adelsonsljunior.simpleecommerce.core.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {

    List<Product> findAll();
    Product create(Product product);
    void delete(Long id);
    Product update(Product product);
    Optional<Product> findById(Long id);
}
