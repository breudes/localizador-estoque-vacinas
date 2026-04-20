package com.hackathon.estoque.exception.inventory;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InventoryNotFoundException extends EntityNotFoundException {
    public InventoryNotFoundException(String message) {
        super(message);
    }
}
