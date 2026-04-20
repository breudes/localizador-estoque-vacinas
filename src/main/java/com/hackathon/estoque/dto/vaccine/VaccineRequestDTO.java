package com.hackathon.estoque.dto.vaccine;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccineRequestDTO {

    @NotBlank(message = "Vaccine name is required")
    @Size(max = 255, message = "Vaccine name must not exceed 255 characters")
    private String vaccineName;

    @Size(max = 255, message = "Prevented disease must not exceed 255 characters")
    private String preventedDisease;

    @Size(max = 255, message = "Observation must not exceed 255 characters")
    private String observation;

    @Min(value = 0, message = "Initial age in months must be non-negative")
    private Integer initialAgeInMonths;

    @Min(value = 0, message = "Final age in months must be non-negative")
    private Integer finalAgeInMonths;

    @Min(value = 0, message = "Initial age in years must be non-negative")
    private Integer initialAgeInYears;

    @Min(value = 0, message = "Final age in years must be non-negative")
    private Integer finalAgeInYears;

    @Size(max = 100, message = "Life stage must not exceed 100 characters")
    private String lifeStage;

    @NotNull(message = "Dose quantity is required")
    @Min(value = 1, message = "Dose quantity must be at least 1")
    private Integer doseQuantity;

    private Boolean booster;

    private Boolean singleDose;

    @Min(value = 0, message = "Interval in days must be non-negative")
    private Integer intervalInDays;
}
