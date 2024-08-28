package com.adelsonsljunior.simpleecommerce.core.domain.dtos.user;

public record UserRequestDTO(
        String username,
        String email,
        String password
) {
}
