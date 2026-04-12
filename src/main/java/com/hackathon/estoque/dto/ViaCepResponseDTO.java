package com.hackathon.estoque.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ViaCepResponseDTO(
        String cep,
        String logradouro,
        String bairro,
        String localidade, //cidade
        String regiao,
        String uf,
        String ibge,
        String ddd,
        Boolean erro //quando não encontra o cep
) {
}