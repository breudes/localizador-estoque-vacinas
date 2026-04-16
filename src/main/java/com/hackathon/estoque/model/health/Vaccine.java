package com.hackathon.estoque.model.health;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vaccines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vaccineName;
    private String preventedDisease;
    private String observation;
    // Age
    private int initialAgeInMonths;
    private int finalAgeInMonths;
    private int initialAgeInYears;
    private int finalAgeInYears;
    private String lifeStage;
    // Dose
    private int doseQuantity;
    private boolean booster;
    private boolean singleDose;
    private int intervalInDays;
}
