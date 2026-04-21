package com.hackathon.estoque.controller;

import com.hackathon.estoque.dto.ageRange.AgeRangeRequestDTO;
import com.hackathon.estoque.dto.vaccine.VaccineRequestDTO;
import com.hackathon.estoque.dto.vaccine.VaccineResponseDTO;
import com.hackathon.estoque.exception.InvalidRequiredAttributeException;
import com.hackathon.estoque.mapper.vaccine.VaccineMapperImpl;
import com.hackathon.estoque.model.health.Vaccine;
import com.hackathon.estoque.service.VaccineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/vaccines")
@RequiredArgsConstructor
public class VaccineController {
    private final VaccineService vaccineService;
    private final VaccineMapperImpl vaccineMapper;

    @PostMapping
    public ResponseEntity<VaccineResponseDTO> createVaccine(@Valid @RequestBody VaccineRequestDTO vaccineRequestDTO) {
        Vaccine createdVaccine = vaccineService.createVaccine(vaccineRequestDTO);
        VaccineResponseDTO vaccineResponseDTO = vaccineMapper.toResponseDTO(createdVaccine);
        return ResponseEntity.status(HttpStatus.CREATED).body(vaccineResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<VaccineResponseDTO>> getAllVaccines() {
        List<Vaccine> vaccines = vaccineService.getAllVaccines();
        if (vaccines.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<VaccineResponseDTO> vaccineResponseDTOs = vaccines.stream()
                .map(vaccineMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(vaccineResponseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VaccineResponseDTO> getVaccineById(@PathVariable Long id) throws InvalidRequiredAttributeException {
        Vaccine vaccine = vaccineService.getVaccineById(id);
        VaccineResponseDTO vaccineResponseDTO = vaccineMapper.toResponseDTO(vaccine);
        return ResponseEntity.ok(vaccineResponseDTO);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<VaccineResponseDTO> getVaccineByName(@PathVariable String name) throws InvalidRequiredAttributeException {
        Vaccine vaccine = vaccineService.getVaccineByName(name);
        VaccineResponseDTO vaccineResponseDTO = vaccineMapper.toResponseDTO(vaccine);
        return ResponseEntity.ok(vaccineResponseDTO);
    }

    @GetMapping("/disease/{disease}")
    public ResponseEntity<List<VaccineResponseDTO>> getVaccinesByDisease(@PathVariable String disease) throws InvalidRequiredAttributeException {
        List<Vaccine> vaccines = vaccineService.getVaccinesByDisease(disease);
        List<VaccineResponseDTO> vaccineResponseDTOs = vaccines.stream()
                .map(vaccineMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(vaccineResponseDTOs);
    }

    @GetMapping("/life-stage/{lifeStage}")
    public ResponseEntity<List<VaccineResponseDTO>> getVaccinesByLifeStage(@PathVariable String lifeStage) throws InvalidRequiredAttributeException {
        List<Vaccine> vaccines = vaccineService.getVaccinesByLifeStage(lifeStage);
        List<VaccineResponseDTO> vaccineResponseDTOs = vaccines.stream()
                .map(vaccineMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(vaccineResponseDTOs);
    }

    @GetMapping("/age-range")
    public ResponseEntity<List<VaccineResponseDTO>> getVaccinesByAgeRange(@RequestBody AgeRangeRequestDTO ageRangeRequestDTO) throws InvalidRequiredAttributeException {
        List<Vaccine> vaccines = vaccineService.getVaccinesByAgeRange(ageRangeRequestDTO);
        List<VaccineResponseDTO> vaccineResponseDTOs = vaccines.stream()
                .map(vaccineMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(vaccineResponseDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vaccine> updateVaccine(@PathVariable Long id,
            @Valid @RequestBody Vaccine vaccineDetails) throws InvalidRequiredAttributeException {
        Vaccine updatedVaccine = vaccineService.updateVaccine(id, vaccineDetails);
        return ResponseEntity.ok(updatedVaccine);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVaccine(@PathVariable Long id) throws InvalidRequiredAttributeException {
        vaccineService.deleteVaccine(id);
        return ResponseEntity.noContent().build();
    }
}
