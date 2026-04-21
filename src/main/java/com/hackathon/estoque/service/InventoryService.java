package com.hackathon.estoque.service;

import com.hackathon.estoque.dto.inventory.InventoryRequestDTO;
import com.hackathon.estoque.dto.inventory.InventoryResponseDTO;
import com.hackathon.estoque.dto.inventory.InventoryUpdateDTO;
import com.hackathon.estoque.exception.UserNotFoundException;
import com.hackathon.estoque.exception.inventory.InventoryAlreadyExistsException;
import com.hackathon.estoque.exception.inventory.InventoryNotFoundException;
import com.hackathon.estoque.exception.InvalidRequiredAttributeException;
import com.hackathon.estoque.mapper.inventory.InventoryMapper;
import com.hackathon.estoque.model.User;
import com.hackathon.estoque.model.health.HealthFacility;
import com.hackathon.estoque.model.health.Inventory;
import com.hackathon.estoque.model.health.Vaccine;
import com.hackathon.estoque.repository.HealthFacilityRepository;
import com.hackathon.estoque.repository.InventoryRepository;
import com.hackathon.estoque.repository.UserRepository;
import com.hackathon.estoque.repository.VaccineRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.hackathon.estoque.service.AuthService.getCurrentUserSubject;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {
    
    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    private final VaccineRepository vaccineRepository;
    private final HealthFacilityRepository healthFacilityRepository;
    private final UserRepository userRepository;

    public InventoryResponseDTO createInventory(@Valid InventoryRequestDTO inventoryRequestDTO)
            throws InventoryAlreadyExistsException, InvalidRequiredAttributeException {
        // Validate vaccine exists
        Vaccine vaccine = vaccineRepository.findById(inventoryRequestDTO.getVaccineId())
                .orElseThrow(() -> new InvalidRequiredAttributeException("Vaccine not found with ID: " + inventoryRequestDTO.getVaccineId()));
        // Validate health facility exists
        HealthFacility healthFacility = healthFacilityRepository.findById(inventoryRequestDTO.getHealthFacilityId())
                .orElseThrow(() -> new InvalidRequiredAttributeException("Health facility not found with ID: " + inventoryRequestDTO.getHealthFacilityId()));
        // Check if inventory already exists for this combination
        Optional<Inventory> existingInventory = inventoryRepository.findByHealthFacilityIdAndVaccineIdAndBatch(
                inventoryRequestDTO.getHealthFacilityId(), 
                inventoryRequestDTO.getVaccineId(), 
                inventoryRequestDTO.getBatch());
        if (existingInventory.isPresent() && existingInventory.get().isActive()) {
            throw new InventoryAlreadyExistsException("Inventory already exists for this vaccine, health facility, and batch combination.");
        }
        Inventory inventory = inventoryMapper.toEntity(inventoryRequestDTO);
        inventory.setVaccine(vaccine);
        inventory.setHealthFacility(healthFacility);
        inventory.setActive(true);

        Optional<User> foundUser = userRepository.findByCpf(getCurrentUserSubject());
        if(foundUser.isPresent()) inventory.setCreatedBy(foundUser.get().getId());
        else throw new UserNotFoundException("User not found with subject");

        Inventory savedInventory = inventoryRepository.save(inventory);
        return inventoryMapper.toResponseDTO(savedInventory);
    }

    public InventoryResponseDTO updateInventory(Long id, @Valid InventoryUpdateDTO inventoryUpdateDTO)
            throws InventoryNotFoundException, InvalidRequiredAttributeException {
        
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with ID: " + id));
        
        if (!inventory.isActive()) {
            throw new InventoryNotFoundException("Inventory is not active with ID: " + id);
        }
        
        // Update fields if provided
        if (inventoryUpdateDTO.getStock() != null) {
            inventory.setStock(inventoryUpdateDTO.getStock());
        }
        if (inventoryUpdateDTO.getBatch() != null && !inventoryUpdateDTO.getBatch().isEmpty()) {
            inventory.setBatch(inventoryUpdateDTO.getBatch());
        }
        if (inventoryUpdateDTO.getExpirationDate() != null) {
            inventory.setExpirationDate(inventoryUpdateDTO.getExpirationDate());
        }
        if (inventoryUpdateDTO.getVaccineId() != null) {
            Vaccine vaccine = vaccineRepository.findById(inventoryUpdateDTO.getVaccineId())
                    .orElseThrow(() -> new InvalidRequiredAttributeException("Vaccine not found with ID: " + inventoryUpdateDTO.getVaccineId()));
            inventory.setVaccine(vaccine);
        }
        if (inventoryUpdateDTO.getHealthFacilityId() != null) {
            HealthFacility healthFacility = healthFacilityRepository.findById(inventoryUpdateDTO.getHealthFacilityId())
                    .orElseThrow(() -> new InvalidRequiredAttributeException("Health facility not found with ID: " + inventoryUpdateDTO.getHealthFacilityId()));
            inventory.setHealthFacility(healthFacility);
        }

        Optional<User> foundUser = userRepository.findByCpf(getCurrentUserSubject());
        if(foundUser.isPresent()) inventory.setUpdatedBy(foundUser.get().getId());
        else throw new UserNotFoundException("User not found with subject");

        Inventory updatedInventory = inventoryRepository.save(inventory);
        return inventoryMapper.toResponseDTO(updatedInventory);
    }

    public Boolean deleteInventory(Long id) throws InventoryNotFoundException {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with ID: " + id));
        inventory.setActive(false);

        Optional<User> foundUser = userRepository.findByCpf(getCurrentUserSubject());
        if(foundUser.isPresent()) inventory.setUpdatedBy(foundUser.get().getId());
        else throw new UserNotFoundException("User not found with subject");

        inventoryRepository.save(inventory);
        return true;
    }

    public InventoryResponseDTO getInventoryById(Long id) throws InventoryNotFoundException {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with ID: " + id));
        if (!inventory.isActive()) {
            throw new InventoryNotFoundException("Inventory is not active with ID: " + id);
        }
        
        return inventoryMapper.toResponseDTO(inventory);
    }

    public List<InventoryResponseDTO> getAllInventories() {
        List<Inventory> inventories = inventoryRepository.findByActiveTrue();
        if (inventories.isEmpty()) {
            throw new InventoryNotFoundException("No active inventories found.");
        }
        return inventoryMapper.toResponseDTOList(inventories);
    }

    public List<InventoryResponseDTO> getInventoriesByHealthFacility(Long healthFacilityId) 
            throws InvalidRequiredAttributeException {
        if (healthFacilityId == null) {
            throw new InvalidRequiredAttributeException("Health facility ID cannot be null.");
        }
        
        // Validate health facility exists
        healthFacilityRepository.findById(healthFacilityId)
                .orElseThrow(() -> new InvalidRequiredAttributeException("Health facility not found with ID: " + healthFacilityId));
        
        List<Inventory> inventories = inventoryRepository.findByHealthFacilityIdAndActiveTrue(healthFacilityId);
        if (inventories.isEmpty()) {
            throw new InventoryNotFoundException("No active inventories found for health facility ID: " + healthFacilityId);
        }
        return inventoryMapper.toResponseDTOList(inventories);
    }

    public List<InventoryResponseDTO> getInventoriesByVaccine(Long vaccineId) 
            throws InvalidRequiredAttributeException {
        if (vaccineId == null) {
            throw new InvalidRequiredAttributeException("Vaccine ID cannot be null.");
        }
        
        // Validate vaccine exists
        vaccineRepository.findById(vaccineId)
                .orElseThrow(() -> new InvalidRequiredAttributeException("Vaccine not found with ID: " + vaccineId));
        
        List<Inventory> inventories = inventoryRepository.findByVaccineId(vaccineId);
        if (inventories.isEmpty()) {
            throw new InventoryNotFoundException("No inventories found for vaccine ID: " + vaccineId);
        }
        return inventoryMapper.toResponseDTOList(inventories);
    }

    public List<InventoryResponseDTO> getInventoriesByHealthFacilityAndVaccine(Long healthFacilityId, Long vaccineId) 
            throws InvalidRequiredAttributeException {
        if (healthFacilityId == null || vaccineId == null) {
            throw new InvalidRequiredAttributeException("Health facility ID and vaccine ID cannot be null.");
        }
        
        // Validate entities exist
        healthFacilityRepository.findById(healthFacilityId)
                .orElseThrow(() -> new InvalidRequiredAttributeException("Health facility not found with ID: " + healthFacilityId));
        
        vaccineRepository.findById(vaccineId)
                .orElseThrow(() -> new InvalidRequiredAttributeException("Vaccine not found with ID: " + vaccineId));
        
        List<Inventory> inventories = inventoryRepository.findActiveInventoryByFacilityAndVaccine(healthFacilityId, vaccineId);
        if (inventories.isEmpty()) {
            throw new InventoryNotFoundException("No active inventories found for health facility ID: " + healthFacilityId + " and vaccine ID: " + vaccineId);
        }
        return inventoryMapper.toResponseDTOList(inventories);
    }

    public List<InventoryResponseDTO> getExpiredInventories() {
        Date currentDate = new Date();
        List<Inventory> expiredInventories = inventoryRepository.findExpiredInventories(currentDate);
        if (expiredInventories.isEmpty()) {
            throw new InventoryNotFoundException("No expired inventories found.");
        }
        return inventoryMapper.toResponseDTOList(expiredInventories);
    }

    public List<InventoryResponseDTO> getLowStockInventories(int threshold) 
            throws InvalidRequiredAttributeException {
        if (threshold < 0) {
            throw new InvalidRequiredAttributeException("Threshold must be non-negative.");
        }
        
        List<Inventory> lowStockInventories = inventoryRepository.findLowStockInventories(threshold);
        if (lowStockInventories.isEmpty()) {
            throw new InventoryNotFoundException("No inventories with low stock found.");
        }
        return inventoryMapper.toResponseDTOList(lowStockInventories);
    }
}
