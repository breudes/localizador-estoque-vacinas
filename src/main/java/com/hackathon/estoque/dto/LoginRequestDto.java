package com.hackathon.estoque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    @Pattern(regexp = "\\d{11}", message = "CPF must contain only numbers")
    private String cpf;

    @NotBlank(message = "Password is required")
    private String password;
}
