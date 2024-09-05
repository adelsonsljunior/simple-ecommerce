package com.adelsonsljunior.simpleecommerce.application.adapters.controllers;

import com.adelsonsljunior.simpleecommerce.core.domain.dtos.sale.SaleRequestDTO;
import com.adelsonsljunior.simpleecommerce.core.domain.dtos.sale.SaleResponseDTO;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.services.SaleServicePort;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "sales")
public class SaleController {

    private final SaleServicePort saleService;

    public SaleController(SaleServicePort saleService) {
        this.saleService = saleService;
    }

    @GetMapping()
    public ResponseEntity<List<SaleResponseDTO>> findAll() {
        List<SaleResponseDTO> sales = this.saleService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(sales);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<SaleResponseDTO> findById(@PathVariable Long id) {
        SaleResponseDTO foundSale = this.saleService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(foundSale);
    }

    @PostMapping()
    public ResponseEntity<SaleResponseDTO> create(@Valid @RequestBody SaleRequestDTO data) {

        SaleResponseDTO createdSale = this.saleService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSale);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<SaleResponseDTO> update(@Valid @PathVariable Long id, @RequestBody SaleRequestDTO data) {

        SaleResponseDTO updatedSale = this.saleService.update(id, data);
        return ResponseEntity.status(HttpStatus.OK).body(updatedSale);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        this.saleService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path = "/report/{date}")
    public ResponseEntity<List<SaleResponseDTO>> findByDate(@PathVariable("date") LocalDate date) {

        List<SaleResponseDTO> sales = this.saleService.findByDate(date);
        return ResponseEntity.status(HttpStatus.OK).body(sales);
    }

    @GetMapping(path = "report/{month}/{year}")
    public ResponseEntity<List<SaleResponseDTO>> findByMonth(@PathVariable("month") int month, @PathVariable("year") int year) {

        List<SaleResponseDTO> sales = this.saleService.findByMonth(month, year);
        return ResponseEntity.status(HttpStatus.OK).body(sales);
    }

    @GetMapping(path = "/report/current-week")
    public ResponseEntity<List<SaleResponseDTO>> findByCurrentWeek() {

        List<SaleResponseDTO> sales = this.saleService.findByCurrentWeek();
        return ResponseEntity.status(HttpStatus.OK).body(sales);
    }


}
