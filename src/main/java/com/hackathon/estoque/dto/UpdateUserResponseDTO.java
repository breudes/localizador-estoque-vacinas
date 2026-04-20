package com.hackathon.estoque.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserResponseDTO {

    private String name;
    private String email;
    private String cpf;
    private LocalDate dataNasc;
}
