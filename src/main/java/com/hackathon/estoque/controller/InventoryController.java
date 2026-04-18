package com.hackathon.estoque.controller;

import com.hackathon.estoque.dto.inventory.InventoryRequestDTO;
import com.hackathon.estoque.dto.inventory.InventoryResponseDTO;
import com.hackathon.estoque.dto.inventory.InventoryUpdateDTO;
import com.hackathon.estoque.exception.shared.InvalidRequiredAttributeException;
import com.hackathon.estoque.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryResponseDTO> createInventory(@Valid @RequestBody InventoryRequestDTO inventoryRequestDTO) throws InvalidRequiredAttributeException {
        InventoryResponseDTO createdInventory = inventoryService.createInventory(inventoryRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInventory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryResponseDTO> updateInventory(
            @PathVariable Long id,
            @Valid @RequestBody InventoryUpdateDTO inventoryUpdateDTO) throws InvalidRequiredAttributeException {
        InventoryResponseDTO updatedInventory = inventoryService.updateInventory(id, inventoryUpdateDTO);
        return ResponseEntity.ok(updatedInventory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteInventory(@PathVariable Long id) {
        Boolean deleted = inventoryService.deleteInventory(id);
        return ResponseEntity.ok(deleted);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponseDTO> getInventoryById(@PathVariable Long id) {
        InventoryResponseDTO inventory = inventoryService.getInventoryById(id);
        return ResponseEntity.ok(inventory);
    }

    @GetMapping
    public ResponseEntity<List<InventoryResponseDTO>> getAllInventories() {
        List<InventoryResponseDTO> inventories = inventoryService.getAllInventories();
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/health-facility/{healthFacilityId}")
    public ResponseEntity<List<InventoryResponseDTO>> getInventoriesByHealthFacility(@PathVariable Long healthFacilityId) throws InvalidRequiredAttributeException {
        List<InventoryResponseDTO> inventories = inventoryService.getInventoriesByHealthFacility(healthFacilityId);
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/vaccine/{vaccineId}")
    public ResponseEntity<List<InventoryResponseDTO>> getInventoriesByVaccine(@PathVariable Long vaccineId) throws InvalidRequiredAttributeException {
        List<InventoryResponseDTO> inventories = inventoryService.getInventoriesByVaccine(vaccineId);
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/health-facility/{healthFacilityId}/vaccine/{vaccineId}")
    public ResponseEntity<List<InventoryResponseDTO>> getInventoriesByHealthFacilityAndVaccine(
            @PathVariable Long healthFacilityId,
            @PathVariable Long vaccineId) throws InvalidRequiredAttributeException {
        List<InventoryResponseDTO> inventories = inventoryService.getInventoriesByHealthFacilityAndVaccine(healthFacilityId, vaccineId);
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/expired")
    public ResponseEntity<List<InventoryResponseDTO>> getExpiredInventories() {
        List<InventoryResponseDTO> inventories = inventoryService.getExpiredInventories();
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/low-stock/{threshold}")
    public ResponseEntity<List<InventoryResponseDTO>> getLowStockInventories(@PathVariable int threshold) throws InvalidRequiredAttributeException {
        List<InventoryResponseDTO> inventories = inventoryService.getLowStockInventories(threshold);
        return ResponseEntity.ok(inventories);
    }
}
