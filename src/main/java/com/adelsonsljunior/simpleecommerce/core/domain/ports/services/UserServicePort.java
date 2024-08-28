package com.adelsonsljunior.simpleecommerce.core.domain.ports.services;

import com.adelsonsljunior.simpleecommerce.core.domain.dtos.user.UserRequestDTO;
import com.adelsonsljunior.simpleecommerce.core.domain.dtos.user.UserResponseDTO;

import java.util.List;

public interface UserServicePort {

    UserResponseDTO create(UserRequestDTO user);
    List<UserResponseDTO> findAll();
    UserResponseDTO findById(Long id);
    UserResponseDTO update(Long id, UserRequestDTO user);
    void delete(Long id);
}
