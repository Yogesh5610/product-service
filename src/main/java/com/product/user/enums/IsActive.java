package com.product.user.enums;

public enum IsActive {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String status;

    IsActive(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
