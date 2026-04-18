package com.hackathon.estoque.dto.inventory;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryUpdateDTO {
    @Min(value = 0, message = "Stock must be non-negative")
    private Integer stock;
    @Size(max = 100, message = "Batch must not exceed 100 characters")
    private String batch;
    private Date expirationDate;
    private Long healthFacilityId;
    private Long vaccineId;
}
