package com.adelsonsljunior.simpleecommerce.core.domain.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank(message = "{user.name.not.blank}")
        String username,
        @NotBlank(message = "{user.email.not.blank}")
        @Email(message = "{user.email.email}")
        String email,
        @NotBlank(message = "{user.password.not.blank}")
        @Size(min = 8, message = "{user.password.size}")
        String password
) {
}
