package com.adelsonsljunior.simpleecommerce.core.domain.adapters.services;

import com.adelsonsljunior.simpleecommerce.core.domain.Product;
import com.adelsonsljunior.simpleecommerce.core.domain.dtos.product.ProductRequestDTO;
import com.adelsonsljunior.simpleecommerce.core.domain.dtos.product.ProductResponseDTO;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.repositories.ProductRepositoryPort;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.services.ProductServicePort;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.stream.Collectors;

public class ProductServiceImp implements ProductServicePort {

    private final ProductRepositoryPort productRepository;

    public ProductServiceImp(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    @Cacheable("products")
    @Override
    public List<ProductResponseDTO> findAll() {
        List<Product> products = this.productRepository.findAll();

        return products
                .stream()
                .map(Product::toProductResponseDTO)
                .collect(Collectors.toList());
    }

    @CacheEvict(value = "products", allEntries = true)
    @Override
    public ProductResponseDTO create(ProductRequestDTO productRequest) {
        Product product = new Product(productRequest);
        Product createdProduct = this.productRepository.create(product);
        return createdProduct.toProductResponseDTO();
    }

    @Override
    public ProductResponseDTO findById(Long productId) {
        Product foundProduct = this.productRepository.findById(productId);

        return foundProduct.toProductResponseDTO();
    }

    @CacheEvict(value = "products", allEntries = true)
    @Override
    public void delete(Long productId) {

        this.productRepository.delete(productId);
    }

    @CacheEvict(value = "products", allEntries = true)
    @Override
    public ProductResponseDTO update(Long id, ProductRequestDTO productRequest) {

        Product foundProduct = this.productRepository.findById(id);

        foundProduct.setName(productRequest.name());
        foundProduct.setDescription(productRequest.description());
        foundProduct.setStock(productRequest.stock());
        foundProduct.setPrice(productRequest.price());

        Product updatedProduct = this.productRepository.update(foundProduct);
        return updatedProduct.toProductResponseDTO();
    }

}
