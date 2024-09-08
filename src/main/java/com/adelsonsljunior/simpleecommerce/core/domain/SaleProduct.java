package com.adelsonsljunior.simpleecommerce.core.domain;

import com.adelsonsljunior.simpleecommerce.core.domain.dtos.sale.SaleProductDTO;

public class SaleProduct {

    private Sale sale;
    private Product product;
    private int quantity;

    public SaleProduct(Sale sale, Product product, int quantity) {
        this.sale = sale;
        this.product = product;
        this.quantity = quantity;
    }

    public SaleProduct() {
    }

    public SaleProductDTO toSaleProductDTO(){
        return new SaleProductDTO(
                this.product.getId(),
                this.quantity
        );
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Sale getSale() {
        return sale;
    }
}
