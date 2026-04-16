package com.hackathon.estoque.exception.vaccine;

import jakarta.persistence.EntityNotFoundException;

public class VaccineNotFoundException extends EntityNotFoundException {
    public VaccineNotFoundException(String message) {
        super(message);
    }
}
