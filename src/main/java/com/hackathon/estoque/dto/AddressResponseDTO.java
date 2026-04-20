package com.hackathon.estoque.dto;

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
}
