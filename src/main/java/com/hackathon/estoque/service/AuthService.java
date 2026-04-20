package com.hackathon.estoque.service;

import com.hackathon.estoque.dto.RegisterRequestDto;
import com.hackathon.estoque.exception.InvalidCredentialsException;
import com.hackathon.estoque.exception.UserNotFoundException;
import com.hackathon.estoque.model.UserRole;
import com.hackathon.estoque.exception.CpfAlreadyRegisteredException;
import com.hackathon.estoque.mapper.UserMapper;
import com.hackathon.estoque.model.User;
import com.hackathon.estoque.repository.UserRepository;
import com.hackathon.estoque.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper = UserMapper.INSTANCE;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Busca o usuário no banco
        User user = userRepository.findByCpf(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com CPF: " + username));

        // 2. Envelopa o usuário no "CustomUserDetails" e entrega para o Spring
        return new CustomUserDetails(user);
    }

    @Transactional
    public void register(RegisterRequestDto data) {
        if (userRepository.existsByCpf(data.getCpf())) {
            throw new CpfAlreadyRegisteredException("CPF already registered");
        }

        String encryptedPassword = passwordEncoder.encode(data.getPassword());

        User user = userMapper.toEntity(data);
        user.setPassword(encryptedPassword);
        user.setRole(UserRole.USER);

        userRepository.save(user);
    }

    @Transactional
    public void updatePassword(Long targetId, String oldPassword, String newPassword, User authenticatedUser) {
        // 1. Busca o usuário que terá a senha alterada
        User targetUser = userRepository.findById(targetId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + targetId));

        // 2. Regra de Segurança:
        // Se não for ADMIN e estiver tentando mudar a senha de OUTRA pessoa -> ERRO
        boolean isAdmin = authenticatedUser.getRole().equals(UserRole.ADMIN);
        boolean isChangingOwnPassword = authenticatedUser.getId().equals(targetId);

        if (!isAdmin && !isChangingOwnPassword) {
            throw new RuntimeException("Você não tem permissão para alterar a senha deste usuário.");
        }

        // 3. Validação da Senha Antiga:
        // Só exigimos a senha antiga se for o próprio usuário mudando.
        // Admins fazendo reset de terceiros não precisam da senha antiga.
        if (isChangingOwnPassword) {
            if (oldPassword == null || !passwordEncoder.matches(oldPassword, targetUser.getPassword())) {
                throw new InvalidCredentialsException("A senha atual está incorreta.");
            }
        }

        // 4. Salva a nova senha
        targetUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(targetUser);
    }
}
