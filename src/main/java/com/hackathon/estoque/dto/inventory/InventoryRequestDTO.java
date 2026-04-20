package com.hackathon.estoque.dto.inventory;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryRequestDTO {
    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock must be non-negative")
    private Integer stock;

    @NotNull(message = "Vaccine ID is required")
    private Long vaccineId;

    @NotNull(message = "Health facility ID is required")
    private Long healthFacilityId;

    @NotBlank(message = "Batch is required")
    @Size(max = 100, message = "Batch must not exceed 100 characters")
    private String batch;

    @NotNull(message = "Expiration date is required")
    @Future(message = "Expiration date must be in the future")
    private Date expirationDate;
}
