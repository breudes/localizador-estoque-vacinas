package com.hackathon.estoque.mapper.vaccine;

import com.hackathon.estoque.dto.vaccine.VaccineRequestDTO;
import com.hackathon.estoque.dto.vaccine.VaccineResponseDTO;
import com.hackathon.estoque.model.health.Vaccine;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class VaccineMapperImpl implements VaccineMapper {
    @Override
    public Vaccine toEntity(VaccineRequestDTO vaccineRequestDTO) {
            Vaccine vaccine = new Vaccine();
            // Vaccine
            vaccine.setVaccineName(vaccineRequestDTO.getVaccineName() != null ? vaccineRequestDTO.getVaccineName() : vaccine.getVaccineName());
            vaccine.setPreventedDisease(vaccineRequestDTO.getPreventedDisease() != null ? vaccineRequestDTO.getPreventedDisease() : vaccine.getPreventedDisease());
            vaccine.setObservation(vaccineRequestDTO.getObservation() != null ? vaccineRequestDTO.getObservation() : vaccine.getObservation());
            // Age
            vaccine.setInitialAgeInMonths(vaccineRequestDTO.getInitialAgeInMonths() != null ? vaccineRequestDTO.getInitialAgeInMonths() : vaccine.getInitialAgeInMonths());
            vaccine.setFinalAgeInMonths(vaccineRequestDTO.getFinalAgeInMonths() != null ? vaccineRequestDTO.getFinalAgeInMonths() : vaccine.getFinalAgeInMonths());
            vaccine.setInitialAgeInYears(vaccineRequestDTO.getInitialAgeInYears() != null ? vaccineRequestDTO.getInitialAgeInYears() : vaccine.getInitialAgeInYears());
            vaccine.setFinalAgeInYears(vaccineRequestDTO.getFinalAgeInYears() != null ? vaccineRequestDTO.getFinalAgeInYears() : vaccine.getFinalAgeInYears());
            // Dose
            vaccine.setDoseQuantity(vaccineRequestDTO.getDoseQuantity() != null ? vaccineRequestDTO.getDoseQuantity() : vaccine.getDoseQuantity());
            vaccine.setBooster(vaccineRequestDTO.getBooster() != null ? vaccineRequestDTO.getBooster() : vaccine.isBooster());
            vaccine.setSingleDose(vaccineRequestDTO.getSingleDose() != null ? vaccineRequestDTO.getSingleDose() : vaccine.isSingleDose());
            vaccine.setIntervalInDays(vaccineRequestDTO.getIntervalInDays() != null ? vaccineRequestDTO.getIntervalInDays() : vaccine.getIntervalInDays());
            vaccine.setLifeStage(vaccineRequestDTO.getLifeStage() != null ? vaccineRequestDTO.getLifeStage() : vaccine.getLifeStage());
            return vaccine;
    }

    @Override
    public VaccineResponseDTO toResponseDTO(Vaccine vaccine) {
        return new VaccineResponseDTO(
                vaccine.getId(),
                vaccine.getVaccineName(),
                vaccine.getPreventedDisease(),
                vaccine.getObservation(),
                vaccine.getInitialAgeInMonths(),
                vaccine.getFinalAgeInMonths(),
                vaccine.getInitialAgeInYears(),
                vaccine.getFinalAgeInYears(),
                vaccine.getLifeStage(),
                vaccine.getDoseQuantity(),
                vaccine.isBooster(),
                vaccine.isSingleDose(),
                vaccine.getIntervalInDays()
        );
    }

    @Override
    public List<VaccineResponseDTO> toResponseDTOList(List<Vaccine> vaccines) {
        return List.of();
    }
}
