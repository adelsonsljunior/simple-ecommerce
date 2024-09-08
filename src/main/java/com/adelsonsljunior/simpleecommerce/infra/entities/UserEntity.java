package com.adelsonsljunior.simpleecommerce.infra.entities;

import com.adelsonsljunior.simpleecommerce.core.domain.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    // Um usuário pode ter várias vendas
    @OneToMany(mappedBy = "user")
    private List<SaleEntity> sales;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleted;

    public UserEntity(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }


    public User toUser() {
        return new User(
                this.id,
                this.updatedAt,
                this.createdAt,
                this.username,
                this.password,
                this.email
        );
    }

}
