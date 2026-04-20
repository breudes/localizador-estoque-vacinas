package com.hackathon.estoque.controller;

import com.hackathon.estoque.dto.healthFacility.HealthFacilityRequestDTO;
import com.hackathon.estoque.dto.healthFacility.HealthFacilityResponseDTO;
import com.hackathon.estoque.dto.healthFacility.HealthFacilityUpdateDTO;
import com.hackathon.estoque.exception.shared.InvalidRequiredAttributeException;
import com.hackathon.estoque.mapper.healthFacility.HealthFaciltyMapperImpl;
import com.hackathon.estoque.model.health.HealthFacility;
import com.hackathon.estoque.service.HealthFacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/health-facilities")
@RequiredArgsConstructor
public class HealthFacilityController {
    private final HealthFacilityService healthFacilityService;
    private final HealthFaciltyMapperImpl healthFacilityMapper;

    @PostMapping
    public ResponseEntity<HealthFacilityResponseDTO> createHealthFacility(@Valid @RequestBody HealthFacilityRequestDTO healthFacilityRequestDTO) {
        HealthFacilityResponseDTO createdHealthFacility = healthFacilityService.createHealthFacility(healthFacilityRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHealthFacility);
    }

    @DeleteMapping("/delete/{cnes}")
    public ResponseEntity<String> deleteHealthFacility(@PathVariable String cnes) {
        boolean result = healthFacilityService.deleteHealthFacility(cnes);
        if(result)
            return ResponseEntity.ok("Estabelecimento de saúde removido com sucesso.");
        return ResponseEntity.badRequest().body("Estabelecimento de saúde não encontrado.");
    }

    @PutMapping("/update")
    public ResponseEntity<HealthFacilityResponseDTO> updateHealthFacility(@RequestBody @Valid HealthFacilityUpdateDTO healthFacilityUpdateDTO) throws InvalidRequiredAttributeException {
        HealthFacility healthFacility = healthFacilityService.updateEntityFromDTO(healthFacilityUpdateDTO);
        HealthFacilityResponseDTO healthFacilityResponseDTO = healthFacilityMapper.toResponseDTO(healthFacility);
        return ResponseEntity.ok(healthFacilityResponseDTO);
    }

    /* GET methods below */

    @GetMapping
    public ResponseEntity<List<HealthFacilityResponseDTO>> getAllHealthFacilities() {
        List<HealthFacilityResponseDTO> healthFacilities = healthFacilityService.getAllHealthFacilities();
        return ResponseEntity.ok(healthFacilities);
    }

    @GetMapping("/cnes/{cnes}")
    public ResponseEntity<HealthFacilityResponseDTO> getHealthFacilityByCnes(@PathVariable String cnes) throws InvalidRequiredAttributeException {
        HealthFacility healthFacility = healthFacilityService.getHealthFacilityByCnes(cnes);
        HealthFacilityResponseDTO healthFacilityResponseDTO = healthFacilityMapper.toResponseDTO(healthFacility);
        return ResponseEntity.ok(healthFacilityResponseDTO);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<HealthFacilityResponseDTO>> getHealthFacilitiesByName(@PathVariable String name) throws InvalidRequiredAttributeException {
        List<HealthFacility> healthFacilities = healthFacilityService.getHealthFacilitiesByName(name);
        List<HealthFacilityResponseDTO> healthFacilityResponseDTOs = healthFacilityMapper.toResponseDTOList(healthFacilities);
        return ResponseEntity.ok(healthFacilityResponseDTOs);
    }

    @GetMapping("/cep/{cep}")
    public ResponseEntity<List<HealthFacilityResponseDTO>> getHealthFacilitiesByCep(@PathVariable String cep) throws InvalidRequiredAttributeException {
        List<HealthFacility> healthFacilities = healthFacilityService.getHealthFacilitiesByCep(cep);
        List<HealthFacilityResponseDTO> healthFacilityResponseDTOs = healthFacilityMapper.toResponseDTOList(healthFacilities);
        return ResponseEntity.ok(healthFacilityResponseDTOs);
    }


}
