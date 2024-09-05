package com.adelsonsljunior.simpleecommerce.application.adapters.controllers;

import com.adelsonsljunior.simpleecommerce.core.domain.dtos.sale.SaleRequestDTO;
import com.adelsonsljunior.simpleecommerce.core.domain.dtos.sale.SaleResponseDTO;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.services.SaleServicePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        List<SaleResponseDTO> sales = saleService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(sales);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<SaleResponseDTO> findById(@PathVariable Long id) {
        SaleResponseDTO foundSale = saleService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(foundSale);
    }

    @PostMapping()
    public ResponseEntity<SaleResponseDTO> create(@RequestBody SaleRequestDTO data) {

        SaleResponseDTO createdSale = saleService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSale);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<SaleResponseDTO> update(@PathVariable Long id, @RequestBody SaleRequestDTO data) {

        SaleResponseDTO updatedSale = saleService.update(id, data);
        return ResponseEntity.status(HttpStatus.OK).body(updatedSale);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        this.saleService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
