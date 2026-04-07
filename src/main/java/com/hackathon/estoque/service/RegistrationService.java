package com.hackathon.estoque.service;

import com.hackathon.estoque.model.dto.AuthResponseDTO;
import com.hackathon.estoque.model.dto.RegisterRequestDTO;
import com.hackathon.estoque.model.entity.Address;
import com.hackathon.estoque.model.entity.Role;
import com.hackathon.estoque.model.entity.User;
import com.hackathon.estoque.model.enums.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrationService {

    private final UserService userService;
    private final AddressService addressService;
    private final RoleService roleService;
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponseDTO registerUser(RegisterRequestDTO registerRequest) {
        // Validate if user already exists
        userService.validateUserDoesNotExist(registerRequest.getCpf(), registerRequest.getEmail());

        // Get the role for the user
        Role userRole = roleService.findByRoleType(registerRequest.getRoleType());

        // Create new user with role
        User newUser = new User(
            registerRequest.getName(),
            registerRequest.getEmail(),
            registerRequest.getCpf(),
            passwordEncoder.encode(registerRequest.getPassword()),
            Set.of(userRole)
        );

        // Create address and set bidirectional relationship
        Address address = addressService.createAddress(registerRequest.getAddress(), newUser);
        newUser.setAddress(address);

        // Save user (address will be saved via cascade)
        User savedUser = userService.save(newUser);

        // Automatically authenticate after registration
        return authenticationService.authenticateAndGenerateResponse(
            registerRequest.getCpf(),
            registerRequest.getPassword()
        );
    }
}
