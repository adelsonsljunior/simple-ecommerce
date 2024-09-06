package com.adelsonsljunior.simpleecommerce.application.adapters.controllers;

import com.adelsonsljunior.simpleecommerce.core.domain.dtos.product.ProductRequestDTO;
import com.adelsonsljunior.simpleecommerce.core.domain.dtos.product.ProductResponseDTO;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.services.ProductServicePort;
import com.adelsonsljunior.simpleecommerce.infra.configs.log.LogExecutionTime;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "products")
public class ProductController {

    private final ProductServicePort productService;

    public ProductController(ProductServicePort productService) {
        this.productService = productService;
    }

    @LogExecutionTime
    @GetMapping()
    public ResponseEntity<List<ProductResponseDTO>> findAll() {

        List<ProductResponseDTO> products = this.productService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @PostMapping()
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductRequestDTO data) {

        ProductResponseDTO createdProduct = this.productService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {

        ProductResponseDTO foundUser = this.productService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(foundUser);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO data) {

        ProductResponseDTO updatedProduct = this.productService.update(id, data);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        this.productService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
