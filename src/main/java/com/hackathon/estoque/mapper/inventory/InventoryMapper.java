package com.hackathon.estoque.mapper.inventory;

import com.hackathon.estoque.dto.inventory.InventoryRequestDTO;
import com.hackathon.estoque.dto.inventory.InventoryResponseDTO;
import com.hackathon.estoque.model.health.Inventory;
import java.util.List;

public interface InventoryMapper {
    Inventory toEntity(InventoryRequestDTO inventoryRequestDTO);
    InventoryResponseDTO toResponseDTO(Inventory inventory);
    List<InventoryResponseDTO> toResponseDTOList(List<Inventory> inventories);
}
