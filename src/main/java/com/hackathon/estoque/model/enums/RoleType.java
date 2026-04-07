package com.hackathon.estoque.model.enums;

public enum RoleType {
    ADMIN("ADMIN"),
    USER("USER");

    private final String value;

    RoleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
