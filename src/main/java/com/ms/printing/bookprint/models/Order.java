package com.ms.printing.bookprint.models;

import com.ms.printing.bookprint.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {
    private UUID orderId;
    private Date purchasedOn;
    private Product product;
    private int quantity;
    private Shipment shipment;
    private double amount;
    private PaymentMethod paymentMethod;
}
