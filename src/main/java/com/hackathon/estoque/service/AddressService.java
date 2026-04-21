package com.hackathon.estoque.service;

import com.hackathon.estoque.dto.AddressRequestDTO;
import com.hackathon.estoque.model.Address;
import com.hackathon.estoque.model.User;
import com.hackathon.estoque.repository.AddressRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {

    private final AddressRepository addressRepository;

    public Address createAddress(AddressRequestDTO dto, User user) {
        Address address = Address.builder()
                .cep(dto.getCep())
                .street(dto.getStreet())
                .city(dto.getCity())
                .state(dto.getState())
                .user(user)
                .build();
        user.setAddress(address);
        return addressRepository.save(address);
    }

    public Address updateAddress(AddressRequestDTO dto, User user) {
        Address address = addressRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Address not found for user ID: " + user.getId()));

        address.setCep(dto.getCep());
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());

        return addressRepository.save(address);
    }

    public Address createAddressHealthFacility(@NotNull(message = "Address is required") AddressRequestDTO addressRequestDTO) {
        return Address.builder()
                .cep(addressRequestDTO.getCep())
                .street(addressRequestDTO.getStreet())
                .number(addressRequestDTO.getNumber())
                .complement(addressRequestDTO.getComplement())
                .city(addressRequestDTO.getCity())
                .state(addressRequestDTO.getState())
                .build();
    }
}
