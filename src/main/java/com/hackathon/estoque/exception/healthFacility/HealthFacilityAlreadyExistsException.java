package com.hackathon.estoque.exception.healthFacility;

import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class HealthFacilityAlreadyExistsException extends EntityExistsException {
    public HealthFacilityAlreadyExistsException(String message) {
        super(message);
    }
}
