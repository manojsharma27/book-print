package com.ms.printing.bookprint.enums;

public enum OrderStatus {
    ORDERED("Ordered"),
    DESIGN("Design"),
    PRINT("Print"),
    ASSEMBLE("Assemble"),
    REVIEW("Review"),
    SHIPPED("Shipped"),
    COMPLETED("Completed");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
