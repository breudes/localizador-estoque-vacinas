package com.hackathon.estoque.model.dto;

import com.hackathon.estoque.model.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {

    private String token;
    private String type = "Bearer";
    private Long userId;
    private String name;
    private String cpf;
    private Set<RoleType> roles;

    public AuthResponseDTO(String token, Long userId, String name, String cpf, Set<RoleType> roles) {
        this.token = token;
        this.userId = userId;
        this.name = name;
        this.cpf = cpf;
        this.roles = roles;
    }
}
