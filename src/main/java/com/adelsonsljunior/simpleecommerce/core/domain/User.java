package com.adelsonsljunior.simpleecommerce.core.domain;

import com.adelsonsljunior.simpleecommerce.core.domain.dtos.user.UserRequestDTO;
import com.adelsonsljunior.simpleecommerce.core.domain.dtos.user.UserResponseDTO;

import java.time.LocalDateTime;

public class User {

    private Long id;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(UserRequestDTO data) {
        this.username = data.username();
        this.email = data.email();
        this.password = data.password();
    }

    public User(Long id, LocalDateTime updatedAt, LocalDateTime createdAt, String password, String username, String email) {
        this.id = id;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.password = password;
        this.username = username;
        this.email = email;
    }

    public User(LocalDateTime updatedAt, LocalDateTime createdAt, String email, String username, String password) {
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.email = email;
        this.username = username;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
