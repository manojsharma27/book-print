package com.ms.printing.bookprint.enums;

public enum BindingDirection {
    LEFT("Left"),
    TOP("Top");

    private final String value;

    BindingDirection(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
