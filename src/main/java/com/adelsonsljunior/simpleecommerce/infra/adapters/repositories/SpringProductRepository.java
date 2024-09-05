package com.adelsonsljunior.simpleecommerce.infra.adapters.repositories;

import com.adelsonsljunior.simpleecommerce.core.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringProductRepository extends JpaRepository<Product, Long> {

    // Consulta que retorna apenas produtos não deletados
    @Query("SELECT p FROM Product p WHERE p.deleted = false")
    List<Product> findAllActive();

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.deleted = true WHERE p.id = :id")
    void softDeleteById(@Param("id") Long id);

    @Query("SELECT p FROM Product p WHERE p.id = :id and p.deleted = false")
    Optional<Product> findByIdActive(@Param("id")Long id);

    // só será realizada se o estoque atual do produto
    // for maior ou igual à quantidade que está sendo subtraída.
    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.stock = p.stock - :quantity WHERE p.id = :productId AND p.stock >= :quantity")
    int decrementProductStock(@Param("productId") Long productId, @Param("quantity") int quantity);


    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.stock = p.stock + :quantity WHERE p.id = :productId")
    int incrementStock(@Param("productId") Long productId, @Param("quantity") int quantity);

}