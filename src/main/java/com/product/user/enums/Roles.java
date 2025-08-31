package com.product.user.enums;

public enum Roles {

    ADMIN("ADMIN"),
    USER("USER"),
    SUB_ADMIN("SUB_ADMIN");

    private final String role;

    Roles(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
