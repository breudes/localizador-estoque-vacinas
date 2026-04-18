package com.hackathon.estoque.mapper.inventory;

import com.hackathon.estoque.dto.inventory.InventoryRequestDTO;
import com.hackathon.estoque.dto.inventory.InventoryResponseDTO;
import com.hackathon.estoque.mapper.healthFacility.HealthFaciltyMapperImpl;
import com.hackathon.estoque.mapper.vaccine.VaccineMapperImpl;
import com.hackathon.estoque.model.health.Inventory;
import com.hackathon.estoque.repository.AddressRepository;
import jakarta.validation.constraints.*;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class InventoryMapperImpl implements InventoryMapper {
    private final AddressRepository addressRepository;

    public InventoryMapperImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Inventory toEntity(InventoryRequestDTO inventoryRequestDTO) {
        return new Inventory(
                inventoryRequestDTO.getStock(),
                inventoryRequestDTO.getBatch(),
                inventoryRequestDTO.getExpirationDate()
        );
    }

    @Override
    public InventoryResponseDTO toResponseDTO(Inventory inventory) {
        return new InventoryResponseDTO(
                inventory.getId(),
                inventory.getStock(),
                inventory.getVaccine() != null ? new VaccineMapperImpl().toResponseDTO(inventory.getVaccine()) : null,
                inventory.getHealthFacility() != null ? new HealthFaciltyMapperImpl(addressRepository).toResponseDTO(inventory.getHealthFacility()) : null,
                inventory.getBatch(),
                inventory.getExpirationDate()
        );
    }

    @Override
    public List<InventoryResponseDTO> toResponseDTOList(List<Inventory> inventories) {
        return inventories.stream()
                .map(this::toResponseDTO)
                .toList();
    }
}
