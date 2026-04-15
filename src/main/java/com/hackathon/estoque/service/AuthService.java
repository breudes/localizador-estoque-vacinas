package com.hackathon.estoque.service;

import com.hackathon.estoque.dto.LoginRequestDto;
import com.hackathon.estoque.dto.RegisterRequestDto;
import com.hackathon.estoque.enums.UserRole;
import com.hackathon.estoque.exception.CpfAlreadyRegisteredException;
import com.hackathon.estoque.exception.InvalidCredentialsException;
import com.hackathon.estoque.exception.UserNotFoundException;
import com.hackathon.estoque.mapper.UserMapper;
import com.hackathon.estoque.model.User;
import com.hackathon.estoque.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User registerEntity(RegisterRequestDto dto) {

        if (userRepository.existsByCpf(dto.getCpf())) {
            throw new CpfAlreadyRegisteredException("CPF already registered");
        }

        System.out.println("Registering user with CPF: " + dto.getCpf());

        User user = userMapper.toEntityRegister(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(UserRole.USER);

        System.out.println("User entity created: " + user.getCpf());

        return userRepository.save(user);
    }

    public User authenticate(LoginRequestDto dto) {
        User user = userRepository.findByCpf(dto.getCpf())
                .orElseThrow(() -> new UserNotFoundException("Invalid CPF or password"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid CPF or password");
        }

        return user;
    }

}
