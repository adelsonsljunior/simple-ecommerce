package com.adelsonsljunior.simpleecommerce.application.adapters.controllers;

import com.adelsonsljunior.simpleecommerce.core.domain.dtos.user.UserRequestDTO;
import com.adelsonsljunior.simpleecommerce.core.domain.dtos.user.UserResponseDTO;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.services.UserServicePort;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "users")
public class UserController {

    private final UserServicePort userService;

    public UserController(UserServicePort userServicePort) {
        this.userService = userServicePort;
    }

    @GetMapping()
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        List<UserResponseDTO> users = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PostMapping()
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO data) {

        UserResponseDTO createdUser = this.userService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {

        UserResponseDTO foundUser = this.userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(foundUser);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UserRequestDTO data) {
        UserResponseDTO updatedUser = this.userService.update(id, data);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        this.userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
