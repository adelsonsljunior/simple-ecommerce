package com.adelsonsljunior.simpleecommerce.infra.entities;


import com.adelsonsljunior.simpleecommerce.core.domain.SaleProduct;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "sale_product")
@NoArgsConstructor
@Setter
public class SaleProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_product_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    private SaleEntity sale;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(nullable = false)
    private int quantity;

    public SaleProductEntity(SaleProduct saleProduct) {
        this.product = new ProductEntity(saleProduct.getProduct());
        this.quantity = saleProduct.getQuantity();
        // Do NOT set sale here to avoid recursion
    }

    public SaleProduct toSaleProductWithoutSale() {
        return new SaleProduct(
                null,  // Sale is not set to avoid recursion
                this.product.toProduct(),
                this.quantity
        );
    }

}
