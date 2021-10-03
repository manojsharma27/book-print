package com.ms.printing.bookprint.enums;

public enum Size {
    A3("A3"),
    A4("A4");

    private final String value;

    Size(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}