package com.adelsonsljunior.simpleecommerce.core.domain;

import com.adelsonsljunior.simpleecommerce.core.domain.dtos.product.ProductRequestDTO;
import com.adelsonsljunior.simpleecommerce.core.domain.dtos.product.ProductResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private int stock;
    @Column(nullable = false)
    private double price;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleted;

    public Product(ProductRequestDTO productDTO) {
        this.name = productDTO.name();
        this.description = productDTO.description();
        this.stock = productDTO.stock();
        this.price = productDTO.price();
    }

    public ProductResponseDTO toProductResponseDTO(){
        return new ProductResponseDTO(
                this.id,
                this.name,
                this.description,
                this.stock,
                this.price,
                this.createdAt,
                this.updatedAt);
    }

}
