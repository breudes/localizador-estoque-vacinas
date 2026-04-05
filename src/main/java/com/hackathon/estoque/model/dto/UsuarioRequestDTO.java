package com.hackathon.estoque.model.dto;

public record UsuarioRequestDTO(
        String cep,
        String numero,
        int idade
) {
}