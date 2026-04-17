package com.hackathon.estoque.mapper.healthFacility;

import com.hackathon.estoque.dto.address.AddressResponseDTO;
import com.hackathon.estoque.dto.healthFacility.HealthFacilityRequestDTO;
import com.hackathon.estoque.dto.healthFacility.HealthFacilityResponseDTO;
import com.hackathon.estoque.dto.inventory.InventoryResponseDTO;
import com.hackathon.estoque.model.health.HealthFacility;
import com.hackathon.estoque.model.health.Inventory;
import com.hackathon.estoque.repository.AddressRepository;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class HealthFaciltyMapperImpl implements HealthFacilityMapper {
    private final AddressRepository addressRepository;

    public HealthFaciltyMapperImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public HealthFacility toEntity(HealthFacilityRequestDTO healthFacilityRequestDTO) {
        var healthFacility = new HealthFacility();
        healthFacility.setName(healthFacilityRequestDTO.getName());
        healthFacility.setCnes(healthFacilityRequestDTO.getCnes());
        healthFacility.setEmail(healthFacilityRequestDTO.getEmail());
        healthFacility.setPhone(healthFacilityRequestDTO.getPhone());
        healthFacility.setAddress(healthFacilityRequestDTO.getAddress());
        return healthFacility;
    }

    @Override
    public HealthFacilityResponseDTO toResponseDTO(HealthFacility healthFacility) {
        if (healthFacility == null) {
            return null;
        }
        return new HealthFacilityResponseDTO(
                healthFacility.getId(),
                healthFacility.getName(),
                healthFacility.getCnes(),
                healthFacility.getEmail(),
                healthFacility.getPhone(),
                healthFacility.isActive(),
                new AddressResponseDTO(healthFacility.getAddress())
        );
    }

    @Override
    public List<HealthFacilityResponseDTO> toResponseDTOList(List<HealthFacility> healthFacilities) {
        var responseDTOList = new ArrayList<HealthFacilityResponseDTO>(healthFacilities.size());
        for (HealthFacility healthFacility : healthFacilities) {
            responseDTOList.add(toResponseDTO(healthFacility, healthFacility.getAddress().getId()));
        }
        return responseDTOList;
    }

    private HealthFacilityResponseDTO toResponseDTO(HealthFacility healthFacility, Long addressId) {
        AddressResponseDTO addressResponseDTO = addressRepository.findById(addressId)
                .map(AddressResponseDTO::new)
                .orElse(null);
        return new HealthFacilityResponseDTO(
                healthFacility.getId(),
                healthFacility.getName(),
                healthFacility.getCnes(),
                healthFacility.getEmail(),
                healthFacility.getPhone(),
                healthFacility.isActive(),
                addressResponseDTO
        );
    }

    @Override
    public List<InventoryResponseDTO> inventoryListToResponseDTO(List<Inventory> inventories) {
        return HealthFacilityMapper.super.inventoryListToResponseDTO(inventories);
    }
}
