package com.hackathon.estoque.dto.vaccine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccineResponseDTO {
    private Long id;
    private String vaccineName;
    private String preventedDisease;
    private String observation;
    private Integer initialAgeInMonths;
    private Integer finalAgeInMonths;
    private Integer initialAgeInYears;
    private Integer finalAgeInYears;
    private String lifeStage;
    private Integer doseQuantity;
    private Boolean booster;
    private Boolean singleDose;
    private Integer intervalInDays;
}
