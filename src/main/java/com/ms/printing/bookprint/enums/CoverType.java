package com.ms.printing.bookprint.enums;

public enum CoverType {
    SOFT("Soft"),
    HARD("Hard");

    private final String value;

    CoverType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
