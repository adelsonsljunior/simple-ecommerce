package com.adelsonsljunior.simpleecommerce.core.domain.adapters.services;

import com.adelsonsljunior.simpleecommerce.core.domain.Product;
import com.adelsonsljunior.simpleecommerce.core.domain.dtos.product.ProductRequestDTO;
import com.adelsonsljunior.simpleecommerce.core.domain.dtos.product.ProductResponseDTO;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.repositories.ProductRepositoryPort;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.services.ProductServicePort;

import java.util.List;
import java.util.stream.Collectors;

public class ProductServiceImp implements ProductServicePort {

    private final ProductRepositoryPort productRepository;

    public ProductServiceImp(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponseDTO> findAll() {
        List<Product> products = this.productRepository.findAll();

        return products
                .stream()
                .map(Product::toProductResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO create(ProductRequestDTO product) {
        Product p = new Product(product);
        Product createdProduct = this.productRepository.create(p);
        return createdProduct.toProductResponseDTO();
    }

    @Override
    public ProductResponseDTO findById(Long id) {
        Product foundProduct = this.productRepository.findById(id);

        return foundProduct.toProductResponseDTO();
    }

    @Override
    public void delete(Long id) {

        this.productRepository.delete(id);
    }

    @Override
    public ProductResponseDTO update(Long id, ProductRequestDTO product) {

        Product foundProduct = this.productRepository.findById(id);

        foundProduct.setName(product.name());
        foundProduct.setDescription(product.description());
        foundProduct.setQuantity(product.quantity());
        foundProduct.setPrice(product.price());

        Product updatedProduct = this.productRepository.update(foundProduct);
        return updatedProduct.toProductResponseDTO();
    }

}
