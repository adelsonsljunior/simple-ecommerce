package com.adelsonsljunior.simpleecommerce.infra.adapters.repositories;

import com.adelsonsljunior.simpleecommerce.core.domain.User;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.repositories.UserRepositoryPort;
import com.adelsonsljunior.simpleecommerce.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepository implements UserRepositoryPort {

    private final SpringUserRepository springUserRepository;

    public UserRepository(SpringUserRepository springUserRepository) {
        this.springUserRepository = springUserRepository;
    }

    @Override
    public List<User> findAll() {
        return this.springUserRepository.findAllActive();
    }

    @Override
    public User findById(Long id) {
        return this.springUserRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User create(User user) {
        return this.springUserRepository.save(user);
    }

    @Override
    public User update(User user) {
        return this.springUserRepository.save(user);
    }

    @Override
    public void delete(Long id) {

        this.springUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        this.springUserRepository.softDeleteById(id);
    }
}
