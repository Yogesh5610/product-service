package com.product.product.enums;

public enum IsActive {

    ACTIVE("Active"),
    INACTIVE("Inactive");

    private final String status;

    IsActive(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
