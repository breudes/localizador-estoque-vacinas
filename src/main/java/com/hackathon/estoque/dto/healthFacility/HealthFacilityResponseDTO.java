package com.hackathon.estoque.dto.healthFacility;

import com.hackathon.estoque.dto.address.AddressResponseDTO;
import com.hackathon.estoque.dto.inventory.InventoryResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthFacilityResponseDTO {
    private Long id;
    private String name;
    private String cnes;
    private String email;
    private String phone;
    private boolean active;
    private AddressResponseDTO address;
    private List<InventoryResponseDTO> vaccinesInventory;

    public HealthFacilityResponseDTO(Long id, String name, String cnes,
                                     String email, String phone, boolean active,
                                     AddressResponseDTO addressResponseDTO) {
        this.id = id;
        this.name = name;
        this.cnes = cnes;
        this.email = email;
        this.phone = phone;
        this.active = active;
        this.address = addressResponseDTO;
    }
}
