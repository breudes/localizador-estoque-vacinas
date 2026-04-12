package com.hackathon.estoque.dto;

public record UsuarioRequestDTO(
        String cep,
        String numero,
        int idade
) {
}