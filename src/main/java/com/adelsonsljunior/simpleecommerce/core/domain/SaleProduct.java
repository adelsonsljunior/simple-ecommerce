package com.adelsonsljunior.simpleecommerce.core.domain;


import com.adelsonsljunior.simpleecommerce.core.domain.dtos.sale.SaleProductDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sale_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_product_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    public SaleProductDTO toSaleProductDTO(){
        return new SaleProductDTO(
                this.product.getId(),
                this.quantity
        );
    }



}
