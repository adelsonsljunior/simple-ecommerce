package com.adelsonsljunior.simpleecommerce.infra.adapters.repositories;

import com.adelsonsljunior.simpleecommerce.core.domain.Product;
import com.adelsonsljunior.simpleecommerce.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringUserRepository extends JpaRepository<User, Long> {

    // Consulta que retorna apenas usuários não deletados
    @Query("SELECT u FROM User u WHERE u.deleted = false")
    List<User> findAllActive();

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.deleted = true WHERE u.id = :id")
    void softDeleteById(Long id);

    @Query("SELECT u FROM User u WHERE u.id = :id and u.deleted = false")
    Optional<User> findByIdActive(Long id);
}
