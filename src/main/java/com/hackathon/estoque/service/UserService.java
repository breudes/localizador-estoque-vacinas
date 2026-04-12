package com.hackathon.estoque.service;

import com.hackathon.estoque.dto.UserResponseDto;
import com.hackathon.estoque.exception.InvalidCredentialsException;
import com.hackathon.estoque.exception.UserNotFoundException;
import com.hackathon.estoque.model.User;
import com.hackathon.estoque.repository.UserRepository;
import com.hackathon.estoque.exception.CpfAlreadyRegisteredException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.hackathon.estoque.mapper.UserMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public boolean existsByCpf(String cpf) {
        return userRepository.existsByCpf(cpf);
    }

    public List<UserResponseDto> findAll() {
        return userMapper.toResponseDtoList(userRepository.findAll());
    }

    public UserResponseDto findById(Long id) {
        return userMapper.toResponseDto(
                userRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id))
        );
    }

    public UserResponseDto getByCpf(String cpf) {
        return userMapper.toResponseDto(
                userRepository.findByCpf(cpf)
                        .orElseThrow(() -> new UserNotFoundException("User not found with CPF: " + cpf))
        );
    }

    public UserResponseDto update(Long id, User updatedUser) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        if (!user.getCpf().equals(updatedUser.getCpf()) && existsByCpf(updatedUser.getCpf())) {
            throw new CpfAlreadyRegisteredException("CPF already registered");
        }

        user.setName(updatedUser.getName());
        user.setCpf(updatedUser.getCpf());
        user.setEmail(updatedUser.getEmail());

        if (updatedUser.getRole() != null) {
            user.setRole(updatedUser.getRole());
        }

        if (updatedUser.getAddress() != null) {
            user.setAddress(updatedUser.getAddress());
        }

        User savedUser = userRepository.save(user);

        return userMapper.toResponseDto(savedUser);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    public void updatePassword(Long id, String oldPassword, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        if (!user.getId().equals(id)) {
            throw new RuntimeException("You can only change your own password");
        }

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid current password");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}