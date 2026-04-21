package com.hackathon.estoque.model.health;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "inventories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int stock;
    @ManyToOne
    @JoinColumn(name = "vaccine_id")
    private Vaccine vaccine;
    @ManyToOne
    @JoinColumn(name = "health_facility_id")
    private HealthFacility healthFacility;
    private String batch; // lote
    private Date expirationDate;
    private boolean active;
    private Long createdBy;
    private Long updatedBy;

    public Inventory(int stock, String batch, Date expirationDate) {
        this.stock = stock;
        this.batch = batch;
        this.expirationDate = expirationDate;
    }
}
