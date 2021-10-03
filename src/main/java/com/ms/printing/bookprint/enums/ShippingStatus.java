package com.ms.printing.bookprint.enums;

public enum ShippingStatus {
    ORDERED("Ordered", "SHIP_ORD"),
    DISPATCHED("Dispatched", "SHIP_DISP"),
    OUT_FOR_DELIVERY("OutForDelivery", "SHIP_OUT"),
    DELIVERED("Delivered", "SHIP_DELV");

    private final String value;
    private final String code;

    ShippingStatus(String value, String code) {
        this.value = value;
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public String getCode() {
        return code;
    }
}
