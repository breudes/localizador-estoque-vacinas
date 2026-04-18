package com.hackathon.estoque.controller;

import com.hackathon.estoque.dto.UpdatePasswordDto;
import com.hackathon.estoque.dto.UserResponseDto;
import com.hackathon.estoque.model.User;
import com.hackathon.estoque.security.CustomUserDetails;
import com.hackathon.estoque.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // Buscar usuário por ID (ADMIN ou o próprio usuário)
    @GetMapping("/id/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public ResponseEntity<UserResponseDto> getUserById(
            @PathVariable Long id) {

        UserResponseDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    // Buscar usuário por CPF (ADMIN ou o próprio usuário)
    @GetMapping("/cpf/{cpf}")
    @PreAuthorize("hasRole('ADMIN') or #cpf == principal.username")
    public ResponseEntity<UserResponseDto> getUserByCpf(@PathVariable String cpf) {
        UserResponseDto user = userService.getByCpf(cpf);
        return ResponseEntity.ok(user);
    }

    // Listar todos os usuários (apenas ADMIN)
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    // Atualizar usuário (ADMIN ou o próprio usuário)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody User user) {
        UserResponseDto updated = userService.update(id, user);
        return ResponseEntity.ok(updated);
    }

    // Deletar usuário (ADMIN ou o próprio usuário)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/password")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id") // Use o .id que criamos no CustomUserDetails
    public ResponseEntity<Void> updatePassword(
            @PathVariable Long id,
            @RequestBody @Valid UpdatePasswordDto dto) {

        userService.updatePassword(id, dto.getOldPassword(), dto.getNewPassword());

        return ResponseEntity.noContent().build();
    }
}
