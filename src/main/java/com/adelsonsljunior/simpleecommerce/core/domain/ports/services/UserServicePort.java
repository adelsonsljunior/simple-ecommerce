package com.adelsonsljunior.simpleecommerce.core.domain.ports.services;

import com.adelsonsljunior.simpleecommerce.core.domain.dtos.user.UserRequestDTO;
import com.adelsonsljunior.simpleecommerce.core.domain.dtos.user.UserResponseDTO;

import java.util.List;

public interface UserServicePort {

    UserResponseDTO create(UserRequestDTO userRequest);
    List<UserResponseDTO> findAll();
    UserResponseDTO findById(Long userId);
    UserResponseDTO update(Long userId, UserRequestDTO userRequest);
    void delete(Long userId);
}
