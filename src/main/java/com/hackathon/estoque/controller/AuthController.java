package com.hackathon.estoque.controller;

import com.hackathon.estoque.dto.AuthResponseDto;
import com.hackathon.estoque.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.hackathon.estoque.model.User;
import com.hackathon.estoque.dto.LoginRequestDto;
import com.hackathon.estoque.dto.RegisterRequestDto;
import com.hackathon.estoque.security.TokenService;
import com.hackathon.estoque.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody @Valid RegisterRequestDto body) {
        User user = authService.registerEntity(body);
        String token = tokenService.generateToken(user);
        return ResponseEntity.ok(new AuthResponseDto(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid LoginRequestDto body) {
        User user = authService.authenticate(body);
        String token = tokenService.generateToken(user);
        return ResponseEntity.ok(new AuthResponseDto(token));
    }
}
