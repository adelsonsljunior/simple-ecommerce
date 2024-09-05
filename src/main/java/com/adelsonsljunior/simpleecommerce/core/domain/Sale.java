package com.adelsonsljunior.simpleecommerce.core.domain;

import com.adelsonsljunior.simpleecommerce.core.domain.dtos.sale.SaleResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "sales")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /*
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            // Tabela intermediária para armazenar as relações Sale-Product
            name = "sale_products",
            joinColumns = @JoinColumn(name = "sale_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;
    */

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SaleProduct> saleProducts;

    @Column(nullable = false)
    private double totalAmount;
    @Column(name = "sale_date", nullable = false)
    private LocalDateTime saleDate;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleted;

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


}
