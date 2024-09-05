package com.adelsonsljunior.simpleecommerce.infra.adapters.repositories;

import com.adelsonsljunior.simpleecommerce.core.domain.Sale;
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
public interface SpringSaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT s FROM Sale s  WHERE s.deleted = false")
    List<Sale> findAllActive();

    @Query("SELECT s FROM Sale s WHERE s.deleted = false and s.id = :id")
    Optional<Sale> findByIdActive(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Sale s SET s.deleted = true WHERE s.id = :id")
    void softDelete(@Param("id") Long id);

    @Query("SELECT s FROM Sale s WHERE DATE(s.saleDate) = :date")
    List<Sale> findByDate(@Param("date") LocalDate date);

    @Query("SELECT s FROM Sale s WHERE MONTH(s.saleDate) = :month AND YEAR(s.saleDate) = :year")
    List<Sale> findByMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT s FROM Sale s WHERE YEAR(s.saleDate) = :year AND WEEK(s.saleDate) = :week")
    List<Sale> findByCurrentWeek(@Param("year") int year, @Param("week") int week);

}
