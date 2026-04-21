package com.hackathon.estoque.exception;

public class InvalidRequiredAttributeException extends IllegalArgumentException {
    public InvalidRequiredAttributeException(String message) {
        super(message);
    }
}
