package com.adelsonsljunior.simpleecommerce.core.domain.adapters.services;

import com.adelsonsljunior.simpleecommerce.core.domain.User;
import com.adelsonsljunior.simpleecommerce.core.domain.dtos.user.UserRequestDTO;
import com.adelsonsljunior.simpleecommerce.core.domain.dtos.user.UserResponseDTO;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.repositories.UserRepositoryPort;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.services.UserServicePort;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImp implements UserServicePort {

    private final UserRepositoryPort userRepository;

    public UserServiceImp(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDTO create(UserRequestDTO user) {

        User u = new User(user);
        User createdUser = this.userRepository.create(u);
        return createdUser.toUserResponseDTO();
    }

    @Override
    public List<UserResponseDTO> findAll() {

        List<User> users = this.userRepository.findAll();
        return users
                .stream()
                .map(User::toUserResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO findById(Long id) {
        User foundUser = this.userRepository.findById(id);
        return foundUser.toUserResponseDTO();
    }

    @Override
    public UserResponseDTO update(Long id, UserRequestDTO user) {

        User foundUser = this.userRepository.findById(id);
        foundUser.setUsername(user.username());
        foundUser.setEmail(user.email());
        foundUser.setPassword(user.password());

        User updatedUser = this.userRepository.update(foundUser);
        return updatedUser.toUserResponseDTO();
    }

    @Override
    public void delete(Long id) {
        this.userRepository.delete(id);
    }
}
