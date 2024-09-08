package com.adelsonsljunior.simpleecommerce.core.domain;

import com.adelsonsljunior.simpleecommerce.core.domain.dtos.product.ProductRequestDTO;
import com.adelsonsljunior.simpleecommerce.core.domain.dtos.product.ProductResponseDTO;


import java.time.LocalDateTime;


public class Product {

    private Long id;
    private String name;
    private String description;
    private int stock;
    private double price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Product(ProductRequestDTO productDTO) {
        this.name = productDTO.name();
        this.description = productDTO.description();
        this.stock = productDTO.stock();
        this.price = productDTO.price();
    }

    public Product(Long id, String name, String description, int stock, double price, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ProductResponseDTO toProductResponseDTO() {
        return new ProductResponseDTO(
                this.id,
                this.name,
                this.description,
                this.stock,
                this.price,
                this.createdAt,
                this.updatedAt);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }


}
