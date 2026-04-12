package com.hackathon.estoque.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.estoque.exception.CepInvalidoException;
import com.hackathon.estoque.exception.CepNaoEncontradoException;
import com.hackathon.estoque.dto.ViaCepResponseDTO;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class BuscaCepService {
    private static final String BASE_URL = "https://viacep.com.br/ws/%s/json/";
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public ViaCepResponseDTO buscarEndereco(String cep) {
        validaFormatoCep(cep);
        String url = String.format(BASE_URL, cep);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> urlResponse;
        ViaCepResponseDTO response;
        try {
            urlResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            response = mapper.readValue(urlResponse.body(), ViaCepResponseDTO.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao realizar a requisição para a API de CEPs. Verifique a conexão com a Internet e tente novamente.", e);
        }

        if (response == null || response.erro() != null) {
            throw new CepNaoEncontradoException("CEP não encontrado. Verifique o formato do CEP e tente novamente.");
        }
        return response;
    }

    private void validaFormatoCep(String cep) {
        if (cep == null || !cep.matches("\\d{8}")) {
            throw new CepInvalidoException("CEP inválido. O formato de um CEP deve ser composto por 8 dígitos numéricos.");
        }
    }
}
