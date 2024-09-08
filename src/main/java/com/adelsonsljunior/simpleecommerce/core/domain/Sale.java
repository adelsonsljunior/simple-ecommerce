package com.adelsonsljunior.simpleecommerce.core.domain;

import com.adelsonsljunior.simpleecommerce.core.domain.dtos.sale.SaleResponseDTO;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class Sale {

    private Long id;
    private User user;
    private List<SaleProduct> saleProducts;
    private double totalAmount;
    private LocalDate saleDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /*
    public Sale(Long id, List<SaleProduct> saleProducts, User user, double totalAmount, LocalDate saleDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.saleProducts = saleProducts;
        this.user = user;
        this.totalAmount = totalAmount;
        this.saleDate = saleDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    */

    public Sale() {
    }

    public Sale(Long id, List<SaleProduct> saleProducts, User user, double totalAmount, LocalDate saleDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.saleProducts = saleProducts;
        this.user = user;
        this.totalAmount = totalAmount;
        this.saleDate = saleDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        // Set back-reference of sale in saleProducts
        this.saleProducts.forEach(saleProduct -> saleProduct.setSale(this));
    }


    public SaleResponseDTO toSaleResponseDTO() {

        return new SaleResponseDTO(
                this.id,
                this.user.getId(),
                this.totalAmount,
                this.saleProducts.stream()
                        .map(SaleProduct::toSaleProductDTO)
                        .collect(Collectors.toList()),
                this.saleDate,
                this.createdAt,
                this.updatedAt
        );
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<SaleProduct> getSaleProducts() {
        return saleProducts;
    }

    public void setSaleProducts(List<SaleProduct> saleProducts) {
        this.saleProducts = saleProducts;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }


}
