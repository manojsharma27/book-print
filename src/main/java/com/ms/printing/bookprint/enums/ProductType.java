package com.ms.printing.bookprint.enums;

public enum ProductType {
    BOOK("Book"),
    BROCHURE("Brochure");

    private final String value;

    ProductType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
