package com.adelsonsljunior.simpleecommerce.core.domain.ports.repositories;

import com.adelsonsljunior.simpleecommerce.core.domain.User;

import java.util.List;

public interface UserRepositoryPort {

    List<User> findAll();
    User findById(Long userId);
    User create(User user);
    User update(User user);
    void delete(Long userId);
}

