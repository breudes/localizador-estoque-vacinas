package com.hackathon.estoque.controller;

import com.hackathon.estoque.dto.AuthResponseDto;
import com.hackathon.estoque.dto.UpdatePasswordDto;
import com.hackathon.estoque.model.User;
import com.hackathon.estoque.repository.UserRepository;
import com.hackathon.estoque.security.CustomUserDetails;
import com.hackathon.estoque.security.TokenService;
import com.hackathon.estoque.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import com.hackathon.estoque.dto.LoginRequestDto;
import com.hackathon.estoque.dto.RegisterRequestDto;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final AuthService authService;

    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, AuthService authService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequestDto data) {
        authService.register(data);
        return ResponseEntity.ok().build();

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid LoginRequestDto data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getCpf(), data.getPassword());
        var auth = authenticationManager.authenticate(usernamePassword);
        // CORREÇÃO: O principal agora é CustomUserDetails
        CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();
        User userEntity = customUserDetails.getUserEntity();

        var token = tokenService.generateToken(userEntity);
        return ResponseEntity.ok(new AuthResponseDto(token));
    }

    @PostMapping("/update-password/{id}")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long id,
            @RequestBody @Valid UpdatePasswordDto dto,
            @AuthenticationPrincipal User authenticatedUser) {

        authService.updatePassword(id, dto.getOldPassword(), dto.getNewPassword(), authenticatedUser);
        return ResponseEntity.noContent().build();
    }
}
