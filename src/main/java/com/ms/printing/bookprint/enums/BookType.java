package com.ms.printing.bookprint.enums;

public enum BookType {
    E_BOOK("eBook"),
    PAPER("Paper"),
    BOTH("Both");

    private final String value;

    BookType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
