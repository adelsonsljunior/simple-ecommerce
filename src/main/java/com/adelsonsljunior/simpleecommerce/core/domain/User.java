package com.adelsonsljunior.simpleecommerce.core.domain;

import com.adelsonsljunior.simpleecommerce.core.domain.dtos.user.UserRequestDTO;
import com.adelsonsljunior.simpleecommerce.core.domain.dtos.user.UserResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

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
    private List<Sale> sales;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleted;

    public User(UserRequestDTO data) {
        this.username = data.username();
        this.email = data.email();
        this.password = data.password();
    }

    public UserResponseDTO toUserResponseDTO() {
        return new UserResponseDTO(
                this.id,
                this.username,
                this.email,
                this.createdAt,
                this.updatedAt
        );
    }

}
