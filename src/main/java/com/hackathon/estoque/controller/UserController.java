package com.hackathon.estoque.controller;

import com.hackathon.estoque.dto.*;
import com.hackathon.estoque.mapper.AddressMapper;
import com.hackathon.estoque.mapper.UserMapper;
import com.hackathon.estoque.model.User;
import com.hackathon.estoque.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final AddressMapper addressMapper = AddressMapper.INSTANCE;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getProfile(@AuthenticationPrincipal User authenticatedUser) {
        return ResponseEntity.ok(userMapper.toResponseDto(authenticatedUser));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody CreateUserDTO data) {
        var user = userService.createUser(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponseDTO> updateProfile(@Valid @RequestBody UpdateUserDTO dto,
                                                         @AuthenticationPrincipal User authenticatedUser) {
        var updatedUser = userService.update(authenticatedUser.getId(), dto);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/address")
    public ResponseEntity<AddressResponseDTO> getAddress(@AuthenticationPrincipal User authenticatedUser) {
        AddressResponseDTO address = userService.findAddressByUser(authenticatedUser);
        return ResponseEntity.ok(address);
    }

    // CADASTRAR ou ATUALIZAR o endereço
    @PostMapping("/address")
    public ResponseEntity<AddressResponseDTO> saveAddress(
            @AuthenticationPrincipal User authenticatedUser,
            @Valid @RequestBody AddressRequestDTO dto) {

        AddressResponseDTO savedAddress = userService.saveOrUpdate(authenticatedUser, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
    }

    // DELETAR o endereço
    @DeleteMapping("/address")
    public ResponseEntity<Void> deleteAddress(@AuthenticationPrincipal User authenticatedUser) {
        userService.deleteAddress(authenticatedUser);
        return ResponseEntity.noContent().build();
    }
}
