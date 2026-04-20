package com.hackathon.estoque.exception.vaccine;

import jakarta.persistence.EntityExistsException;

public class VaccineAlreadyExistsException extends EntityExistsException {
    public VaccineAlreadyExistsException(String message) {
        super(message);
    }
}
