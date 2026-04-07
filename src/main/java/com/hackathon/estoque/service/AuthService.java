package com.hackathon.estoque.service;

import com.hackathon.estoque.model.dto.AuthResponseDTO;
import com.hackathon.estoque.model.dto.RegisterRequestDTO;
import com.hackathon.estoque.model.dto.LoginRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RegistrationService registrationService;
    private final AuthenticationService authenticationService;

    public AuthResponseDTO registerUser(RegisterRequestDTO registerRequest) {
        return registrationService.registerUser(registerRequest);
    }

    public AuthResponseDTO login(LoginRequestDTO loginRequest) {
        return authenticationService.login(loginRequest);
    }
}
