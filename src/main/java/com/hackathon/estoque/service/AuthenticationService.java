package com.hackathon.estoque.service;

import com.hackathon.estoque.model.dto.AuthResponseDTO;
import com.hackathon.estoque.model.dto.LoginRequestDTO;
import com.hackathon.estoque.model.entity.Role;
import com.hackathon.estoque.model.entity.User;
import com.hackathon.estoque.model.enums.RoleType;
import com.hackathon.estoque.config.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthResponseDTO login(LoginRequestDTO loginRequest) {
        try {
            return authenticateAndGenerateResponse(loginRequest.getCpf(), loginRequest.getPassword());
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid CPF or password");
        }
    }

    public AuthResponseDTO authenticateAndGenerateResponse(String cpf, String password) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(cpf, password)
        );

        String jwt = jwtUtils.generateJwtToken(authentication);
        User user = (User) authentication.getPrincipal();

        // Extract role types for response
        Set<RoleType> roleTypes = user.getRoles().stream()
                .map(Role::getRoleType)
                .collect(Collectors.toSet());

        return new AuthResponseDTO(jwt, user.getId(), user.getName(), user.getCpf(), roleTypes);
    }
}
