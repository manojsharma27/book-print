package com.ms.printing.bookprint.enums;

public enum PaperType {
    BOND("Bond"),
    REGULAR("Regular");

    private final String value;

    PaperType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
