package com.hackathon.estoque.controller;

import com.hackathon.estoque.model.dto.UsuarioRequestDTO;
import com.hackathon.estoque.model.dto.ViaCepResponseDTO;
import com.hackathon.estoque.service.BuscaCepService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vacinas")
public class VacinaController {
    private final BuscaCepService buscaCepService;

    public VacinaController(BuscaCepService buscaCepService) {
        this.buscaCepService = buscaCepService;
    }

    @GetMapping("/buscarEndereco")
    public ResponseEntity<ViaCepResponseDTO> buscarEndereco(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        ViaCepResponseDTO response = buscaCepService.buscarEndereco(usuarioRequestDTO.cep());
        return ResponseEntity.ok(response);
    }
}
