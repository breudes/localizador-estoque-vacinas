package com.hackathon.estoque.service;

import com.hackathon.estoque.model.dto.AddressRequestDTO;
import com.hackathon.estoque.model.entity.Address;
import com.hackathon.estoque.model.entity.User;
import com.hackathon.estoque.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {

    private final AddressRepository addressRepository;

    public Address createAddress(AddressRequestDTO addressRequest, User user) {
        Address address = new Address(
            addressRequest.getCep(),
            addressRequest.getStreet(),
            addressRequest.getNumber(),
            addressRequest.getCity(),
            addressRequest.getState()
        );

        address.setUser(user);
        return address;
    }

    public Address save(Address address) {
        return addressRepository.save(address);
    }
}
