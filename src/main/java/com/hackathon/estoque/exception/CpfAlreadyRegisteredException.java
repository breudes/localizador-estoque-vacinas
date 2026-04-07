package com.hackathon.estoque.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CpfAlreadyRegisteredException extends RuntimeException {
    public CpfAlreadyRegisteredException(String message) {
        super(message);
    }
}
