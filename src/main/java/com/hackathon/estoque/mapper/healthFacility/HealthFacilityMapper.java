package com.hackathon.estoque.mapper.healthFacility;

import com.hackathon.estoque.dto.healthFacility.HealthFacilityRequestDTO;
import com.hackathon.estoque.dto.healthFacility.HealthFacilityResponseDTO;
import com.hackathon.estoque.model.health.HealthFacility;
import org.mapstruct.Named;
import java.util.List;

public interface HealthFacilityMapper {
    
    HealthFacility toEntity(HealthFacilityRequestDTO healthFacilityRequestDTO);
    
    HealthFacilityResponseDTO toResponseDTO(HealthFacility healthFacility);

    List<HealthFacilityResponseDTO> toResponseDTOList(List<HealthFacility> healthFacilities);

    @Named("inventoryListToResponseDTO")
    default List<com.hackathon.estoque.dto.inventory.InventoryResponseDTO> inventoryListToResponseDTO(
            List<com.hackathon.estoque.model.health.Inventory> inventories) {
        if (inventories == null) return null;
        
        return inventories.stream()
                .map(inventory -> new com.hackathon.estoque.dto.inventory.InventoryResponseDTO(
                        inventory.getId(),
                        inventory.getStock(),
                        inventory.getVaccine() != null ? new com.hackathon.estoque.dto.vaccine.VaccineResponseDTO(
                                inventory.getVaccine().getId(),
                                inventory.getVaccine().getVaccineName(),
                                inventory.getVaccine().getPreventedDisease(),
                                inventory.getVaccine().getObservation(),
                                inventory.getVaccine().getInitialAgeInMonths(),
                                inventory.getVaccine().getFinalAgeInMonths(),
                                inventory.getVaccine().getInitialAgeInYears(),
                                inventory.getVaccine().getFinalAgeInYears(),
                                inventory.getVaccine().getLifeStage(),
                                inventory.getVaccine().getDoseQuantity(),
                                inventory.getVaccine().isBooster(),
                                inventory.getVaccine().isSingleDose(),
                                inventory.getVaccine().getIntervalInDays()
                        ) : null,
                        inventory.getHealthFacility() != null ? inventory.getHealthFacility().getId() : null,
                        inventory.getHealthFacility() != null ? inventory.getHealthFacility().getName() : null,
                        inventory.getBatch(),
                        inventory.getExpirationDate()
                ))
                .toList();
    }

}
