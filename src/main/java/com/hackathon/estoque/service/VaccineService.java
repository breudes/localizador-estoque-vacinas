package com.hackathon.estoque.service;

import com.hackathon.estoque.dto.ageRange.AgeRangeRequestDTO;
import com.hackathon.estoque.dto.vaccine.VaccineRequestDTO;
import com.hackathon.estoque.exception.InvalidRequiredAttributeException;
import com.hackathon.estoque.exception.vaccine.VaccineAlreadyExistsException;
import com.hackathon.estoque.exception.vaccine.VaccineNotFoundException;
import com.hackathon.estoque.mapper.vaccine.VaccineMapperImpl;
import com.hackathon.estoque.model.health.Vaccine;
import com.hackathon.estoque.repository.VaccineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VaccineService {
    private final VaccineRepository vaccineRepository;
    private final VaccineMapperImpl vaccineMapper;

    public Vaccine createVaccine(VaccineRequestDTO vaccineRequestDTO) {
        if (vaccineRepository.existsByVaccineName(vaccineRequestDTO.getVaccineName())) {
            throw new VaccineAlreadyExistsException("Vacina com nome '" + vaccineRequestDTO.getVaccineName() + "' já está cadastrada.");
        }
        Vaccine vaccine = vaccineMapper.toEntity(vaccineRequestDTO);
        return vaccineRepository.save(vaccine);
    }

    public Vaccine getVaccineById(Long id) throws InvalidRequiredAttributeException {
        if (id == null || id == 0) {
            throw new InvalidRequiredAttributeException("ID não pode ser nulo.");
        }
        return vaccineRepository.findById(id)
                .orElseThrow(() -> new VaccineNotFoundException("Vaccine not found with id: " + id));
    }

    public Vaccine getVaccineByName(String name) throws InvalidRequiredAttributeException {
        if (name == null || name.isBlank()) {
            throw new InvalidRequiredAttributeException("Name cannot be null.");
        }
        return vaccineRepository.findByVaccineName(name)
                .orElseThrow(() -> new VaccineNotFoundException("Vaccine not found with name: " + name));
    }

    public List<Vaccine> getAllVaccines() {
        List<Vaccine> vaccines = vaccineRepository.findAll();
        if (vaccines.isEmpty()) throw new VaccineNotFoundException("No vaccines found");
        return vaccines;
    }

    public List<Vaccine> getVaccinesByDisease(String preventedDisease) throws InvalidRequiredAttributeException {
        if (preventedDisease == null || preventedDisease.isBlank()) {
            throw new InvalidRequiredAttributeException("Prevented disease cannot be null or empty.");
        }
        List<Vaccine> vaccines = vaccineRepository.findByPreventedDiseaseContaining(preventedDisease);
        if (vaccines.isEmpty()) throw new VaccineNotFoundException("No vaccines found for prevented disease: " + preventedDisease);
        return vaccines;
    }

    public List<Vaccine> getVaccinesByLifeStage(String lifeStage) throws InvalidRequiredAttributeException {
        if (lifeStage == null || lifeStage.isBlank()) {
            throw new InvalidRequiredAttributeException("Life stage cannot be null or empty.");
        }
        List<Vaccine> vaccines = vaccineRepository.findByLifeStageContaining(lifeStage.strip());
        if (vaccines.isEmpty()) throw new VaccineNotFoundException("No vaccines found for life stage: " + lifeStage);
        return vaccines;
    }

    public List<Vaccine> getVaccinesByAgeRange(AgeRangeRequestDTO ageRangeRequestDTO) throws InvalidRequiredAttributeException {
        if (ageRangeRequestDTO.initialAge() == null && ageRangeRequestDTO.finalAge() == null) {
            throw new InvalidRequiredAttributeException("Initial age and final age cannot be null or empty.");
        }
        List<Vaccine> vaccines;
        if(ageRangeRequestDTO.isInMonths()){
            vaccines = vaccineRepository.findByAgeRangeInMonths(ageRangeRequestDTO.initialAge(), ageRangeRequestDTO.finalAge());
        } else {
            vaccines = vaccineRepository.findByAgeRange(ageRangeRequestDTO.initialAge(), ageRangeRequestDTO.finalAge());
        }
        if (vaccines.isEmpty()) throw new VaccineNotFoundException("Não foram encontradas vacinas para esta faixa etária.");
        return vaccines;
    }

    public Vaccine updateVaccine(Long id, Vaccine vaccineDetails) throws InvalidRequiredAttributeException {
        Vaccine existingVaccine = getVaccineById(id);

        if (!existingVaccine.getVaccineName().equals(vaccineDetails.getVaccineName()) &&
            vaccineRepository.existsByVaccineName(vaccineDetails.getVaccineName())) {
            throw new VaccineAlreadyExistsException("Vaccine with name '" + vaccineDetails.getVaccineName() + "' already exists");
        }

        BeanUtils.copyProperties(vaccineDetails, existingVaccine);
        return vaccineRepository.save(existingVaccine);
    }

    public void deleteVaccine(Long id) throws InvalidRequiredAttributeException {
        Vaccine vaccine = getVaccineById(id);
        vaccineRepository.delete(vaccine);
    }
}
