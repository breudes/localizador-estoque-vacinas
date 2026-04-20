package com.hackathon.estoque.service;

import com.hackathon.estoque.dto.*;
import com.hackathon.estoque.exception.AddressNotFoundException;
import com.hackathon.estoque.exception.UserNotFoundException;
import com.hackathon.estoque.mapper.AddressMapper;
import com.hackathon.estoque.model.Address;
import com.hackathon.estoque.model.User;
import com.hackathon.estoque.model.UserRole;
import com.hackathon.estoque.repository.AddressRepository;
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
    private final AddressRepository addressRepository;

    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final AddressMapper addressMapper = AddressMapper.INSTANCE;

    public boolean existsByCpf(String cpf) {
        return userRepository.existsByCpf(cpf);
    }

    public List<UserResponseDTO> findAll() {
        return userMapper.toResponseDtoList(userRepository.findAll());
    }

    public UserResponseDTO findById(Long id) {
        return userMapper.toResponseDto(
                userRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id))
        );
    }

    public UserResponseDTO getByCpf(String cpf) {
        return userMapper.toResponseDto(
                userRepository.findByCpf(cpf)
                        .orElseThrow(() -> new UserNotFoundException("User not found with CPF: " + cpf))
        );
    }

    public UserResponseDTO createAdmin(CreateUserDTO data) {
        if (existsByCpf(data.getCpf())) {
            throw new CpfAlreadyRegisteredException("CPF already registered");
        }

        User user = userMapper.toEntityUser(data);
        user.setPassword(passwordEncoder.encode(data.getPassword()));
        user.setRole(UserRole.ADMIN);

        User savedUser = userRepository.save(user);
        return userMapper.toResponseDto(savedUser);
    }

    public UserResponseDTO createUser(CreateUserDTO data) {
        if (existsByCpf(data.getCpf())) {
            throw new CpfAlreadyRegisteredException("CPF already registered");
        }

        User user = userMapper.toEntityUser(data);
        user.setPassword(passwordEncoder.encode(data.getPassword()));
        user.setRole(UserRole.USER);

        User savedUser = userRepository.save(user);
        return userMapper.toResponseDto(savedUser);
    }

    public UserResponseDTO update(Long id, UpdateUserDTO updatedUser) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        if (!user.getCpf().equals(updatedUser.getCpf()) && existsByCpf(updatedUser.getCpf())) {
            throw new CpfAlreadyRegisteredException("CPF already registered");
        }

        user.setName(updatedUser.getName());
        user.setCpf(updatedUser.getCpf());
        user.setEmail(updatedUser.getEmail());
        user.setDataNasc(updatedUser.getDataNasc());

        User savedUser = userRepository.save(user);

        return userMapper.toResponseDto(savedUser);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    public AddressResponseDTO findAddressByUser(User user) {
        Address address = addressRepository.findByUserId(user.getId())
                .orElseThrow(() -> new AddressNotFoundException("Address not found for user with ID: " + user.getId()));

        return addressMapper.toResponseDto(address);
    }

    public void deleteAddress(User user) {
        Address address = addressRepository.findByUserId(user.getId())
                .orElseThrow(() -> new AddressNotFoundException("Address not found for user with ID: " + user.getId()));

        addressRepository.delete(address);
    }

    public AddressResponseDTO saveOrUpdate(User user, AddressRequestDTO dto) {
        // Verifica se o usuário já tem um endereço (para atualizar) ou se é um novo
        Address address = addressRepository.findByUserId(user.getId())
                .orElse(new Address());

        // Mapeia os dados do DTO para a Entity
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setCep(dto.getCep());

        // Vínculo crucial: garante que o endereço pertence ao usuário do Token
        address.setUser(user);

        Address saved = addressRepository.save(address);
        return addressMapper.toResponseDto(saved);
    }

}