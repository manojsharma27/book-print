package com.ms.printing.bookprint.enums;

public enum PaymentMethod {
    CARD("Card");

    private final String value;

    PaymentMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
