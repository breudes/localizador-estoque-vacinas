package com.hackathon.estoque.dto.inventory;

import com.hackathon.estoque.dto.healthFacility.HealthFacilityResponseDTO;
import com.hackathon.estoque.dto.vaccine.VaccineResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponseDTO {
    private Long id;
    private Integer stock;
    private VaccineResponseDTO vaccine;
    private HealthFacilityResponseDTO healthFacility;
    private String batch;
    private Date expirationDate;
}
