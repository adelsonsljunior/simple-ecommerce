package com.adelsonsljunior.simpleecommerce.infra.adapters.repositories;

import com.adelsonsljunior.simpleecommerce.core.domain.User;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.repositories.UserRepositoryPort;
import com.adelsonsljunior.simpleecommerce.exceptions.ResourceNotFoundException;
import com.adelsonsljunior.simpleecommerce.infra.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRepository implements UserRepositoryPort {

    private final SpringUserRepository springUserRepository;

    public UserRepository(SpringUserRepository springUserRepository) {
        this.springUserRepository = springUserRepository;
    }

    @Override
    public List<User> findAll() {
        List<UserEntity> users = this.springUserRepository.findAllActive();
        return users.stream()
                .map(UserEntity::toUser)
                .collect(Collectors.toList());
    }

    @Override
    public User findById(Long id) {
        UserEntity foundUser = this.springUserRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for id: " + id));

        return foundUser.toUser();
    }

    @Override
    public User create(User user) {
        UserEntity userEntity = new UserEntity(user);
        UserEntity createdUser = this.springUserRepository.save(userEntity);
        return createdUser.toUser();
    }

    @Override
    public User update(User user) {
        UserEntity userEntity = new UserEntity(user);
        UserEntity updatedUser = this.springUserRepository.save(userEntity);
        return updatedUser.toUser();
    }

    @Override
    public void delete(Long userId) {

        this.springUserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for id:" + userId));

        this.springUserRepository.softDeleteById(userId);
    }
}
