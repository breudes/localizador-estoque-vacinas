package com.hackathon.estoque.controller;

import com.hackathon.estoque.dto.*;
import com.hackathon.estoque.dto.AddressRequestDTO;
import com.hackathon.estoque.dto.AddressResponseDTO;
import com.hackathon.estoque.dto.UpdateUserDTO;
import com.hackathon.estoque.mapper.UserMapperImpl;
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

    private final UserMapperImpl userMapper;

    UserController(UserService userService, UserMapperImpl userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
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

    @PostMapping("/address")
    public ResponseEntity<AddressResponseDTO> saveAddress(
            @AuthenticationPrincipal User authenticatedUser,
            @Valid @RequestBody AddressRequestDTO dto) {

        AddressResponseDTO savedAddress = userService.saveOrUpdate(authenticatedUser, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
    }

    @DeleteMapping("/address")
    public ResponseEntity<Void> deleteAddress(@AuthenticationPrincipal User authenticatedUser) {
        userService.deleteAddress(authenticatedUser);
        return ResponseEntity.noContent().build();
    }
}
