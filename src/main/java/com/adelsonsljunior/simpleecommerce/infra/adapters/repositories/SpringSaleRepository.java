package com.adelsonsljunior.simpleecommerce.infra.adapters.repositories;

import com.adelsonsljunior.simpleecommerce.infra.entities.SaleEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SpringSaleRepository extends JpaRepository<SaleEntity, Long> {

    @Query("SELECT s FROM SaleEntity s  WHERE s.deleted = false")
    List<SaleEntity> findAllActive();

    @Query("SELECT s FROM SaleEntity s WHERE s.deleted = false and s.id = :id")
    Optional<SaleEntity> findByIdActive(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE SaleEntity s SET s.deleted = true WHERE s.id = :id")
    void softDelete(@Param("id") Long id);

    @Query("SELECT s FROM SaleEntity s WHERE DATE(s.saleDate) = :date")
    List<SaleEntity> findByDate(@Param("date") LocalDate date);

    @Query("SELECT s FROM SaleEntity s WHERE MONTH(s.saleDate) = :month AND YEAR(s.saleDate) = :year")
    List<SaleEntity> findByMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT s FROM SaleEntity s WHERE YEAR(s.saleDate) = :year AND WEEK(s.saleDate) = :week")
    List<SaleEntity> findByCurrentWeek(@Param("year") int year, @Param("week") int week);

}
