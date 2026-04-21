package com.hackathon.estoque.controller.authroles;

import com.hackathon.estoque.dto.CreateUserDTO;
import com.hackathon.estoque.dto.UpdateUserDTO;
import com.hackathon.estoque.dto.UserResponseDTO;
import com.hackathon.estoque.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createAdmin(@Valid @RequestBody CreateUserDTO dto) {
        userService.createAdmin(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UpdateUserDTO dto) {
        return ResponseEntity.ok().body(userService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
