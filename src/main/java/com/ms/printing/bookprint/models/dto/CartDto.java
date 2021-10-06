package com.ms.printing.bookprint.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ms.printing.bookprint.models.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartDto {
    private UUID id;
    private List<ProductQuantity> products;
    private double totalAmount;
    private double discount;
    private double amountToPay;
    private boolean checkedOut;
    private List<Order> orders;

}
