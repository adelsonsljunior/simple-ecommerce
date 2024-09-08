package com.adelsonsljunior.simpleecommerce.infra.entities;

import com.adelsonsljunior.simpleecommerce.core.domain.Sale;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "sales")
@NoArgsConstructor
public class SaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SaleProductEntity> saleProducts;

    @Column(nullable = false)
    private double totalAmount;
    @Column(name = "sale_date", nullable = false)
    private LocalDate saleDate;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleted;

    public SaleEntity(Sale sale) {
        this.id = sale.getId();
        this.user = new UserEntity(sale.getUser());
        this.saleProducts = sale.getSaleProducts().stream()
                .map(saleProduct -> {
                    SaleProductEntity saleProductEntity = new SaleProductEntity(saleProduct);
                    saleProductEntity.setSale(this);  // Set sale to avoid recursion
                    return saleProductEntity;
                })
                .collect(Collectors.toList());
        this.totalAmount = sale.getTotalAmount();
        this.saleDate = sale.getSaleDate();
        this.createdAt = sale.getCreatedAt();
        this.updatedAt = sale.getUpdatedAt();
    }

    /*
    public Sale toSale() {

        return new Sale(
                this.id,
                this.saleProducts.stream()
                        .map(SaleProductEntity::toSaleProduct)
                        .collect(Collectors.toList()),
                this.user.toUser(),
                this.totalAmount,
                this.saleDate,
                this.createdAt,
                this.updatedAt
        );
    }
    */

    public Sale toSale() {
        Sale sale = new Sale(
                this.id,
                this.saleProducts.stream()
                        .map(SaleProductEntity::toSaleProductWithoutSale)  // Avoid recursion
                        .collect(Collectors.toList()),
                this.user.toUser(),
                this.totalAmount,
                this.saleDate,
                this.createdAt,
                this.updatedAt
        );

        // Set back-reference of sale in saleProducts
        sale.getSaleProducts().forEach(saleProduct -> saleProduct.setSale(sale));

        return sale;
    }



}
