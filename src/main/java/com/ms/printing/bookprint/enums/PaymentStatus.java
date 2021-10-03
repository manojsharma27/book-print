package com.ms.printing.bookprint.enums;

public enum PaymentStatus {
    NOT_PAID("NotPaid", "PAY_NO"),
    IN_PROGRESS("InProgress", "PAY_PROG"),
    FAILED("Failed", "PAY_FAIL"),
    SUCCESS("Success", "PAY_YES");

    private final String value;
    private final String paymentCode;

    PaymentStatus(String value, String paymentCode) {
        this.value = value;
        this.paymentCode = paymentCode;
    }

    public String getValue() {
        return value;
    }

    public String getPaymentCode() {
        return paymentCode;
    }
}
