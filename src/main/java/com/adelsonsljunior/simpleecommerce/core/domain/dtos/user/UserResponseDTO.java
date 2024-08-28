package com.adelsonsljunior.simpleecommerce.core.domain.dtos.user;

import java.time.LocalDateTime;

public record UserResponseDTO(
        Long id,
        String username,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}
