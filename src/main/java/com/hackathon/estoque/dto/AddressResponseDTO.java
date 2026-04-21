package com.hackathon.estoque.dto;

import com.hackathon.estoque.model.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDTO {

    private String cep;
    private String street;
    private String city;
    private String state;

    public AddressResponseDTO(Address address) {
        this.cep = address.getCep();
        this.street = address.getStreet();
        this.city = address.getCity();
        this.state = address.getState();
    }
}
