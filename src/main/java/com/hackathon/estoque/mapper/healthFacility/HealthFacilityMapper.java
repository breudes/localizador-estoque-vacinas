package com.hackathon.estoque.mapper.healthFacility;

import com.hackathon.estoque.dto.healthFacility.HealthFacilityRequestDTO;
import com.hackathon.estoque.dto.healthFacility.HealthFacilityResponseDTO;
import com.hackathon.estoque.model.health.HealthFacility;
import java.util.List;

public interface HealthFacilityMapper {
    HealthFacility toEntity(HealthFacilityRequestDTO healthFacilityRequestDTO);
    HealthFacilityResponseDTO toResponseDTO(HealthFacility healthFacility);
    List<HealthFacilityResponseDTO> toResponseDTOList(List<HealthFacility> healthFacilities);
}
