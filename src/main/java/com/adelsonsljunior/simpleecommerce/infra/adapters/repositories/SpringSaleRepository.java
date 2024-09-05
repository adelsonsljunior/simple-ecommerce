package com.adelsonsljunior.simpleecommerce.infra.adapters.repositories;

import com.adelsonsljunior.simpleecommerce.core.domain.Sale;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringSaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT s FROM Sale s  WHERE s.deleted = false")
    List<Sale> findAllActive();

    @Query("SELECT s FROM Sale s WHERE s.deleted = false and s.id = :id")
    Optional<Sale> findByIdActive(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Sale s SET s.deleted = true WHERE s.id = :id")
    void softDelete(@Param("id") Long id);
}
