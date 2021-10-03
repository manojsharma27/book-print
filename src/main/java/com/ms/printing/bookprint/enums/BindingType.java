package com.ms.printing.bookprint.enums;

public enum BindingType {
    REGULAR("Regular"),
    SPIRAL("Spiral");

    private final String value;

    BindingType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
