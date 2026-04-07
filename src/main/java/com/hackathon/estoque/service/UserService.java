package com.hackathon.estoque.service;

import com.hackathon.estoque.model.entity.User;
import com.hackathon.estoque.repository.UserRepository;
import com.hackathon.estoque.exception.CpfAlreadyRegisteredException;
import com.hackathon.estoque.exception.EmailAlreadyRegisteredException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public void validateUserDoesNotExist(String cpf, String email) {
        if (userRepository.existsByCpf(cpf)) {
            throw new CpfAlreadyRegisteredException("CPF already registered in the system");
        }

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyRegisteredException("Email already registered in the system");
        }
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByCpf(String cpf) {
        return userRepository.findByCpf(cpf);
    }

    public boolean existsByCpf(String cpf) {
        return userRepository.existsByCpf(cpf);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
