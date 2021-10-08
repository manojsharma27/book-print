package com.ms.printing.bookprint.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cart {
    private UUID id;
    @JsonIgnore
    private Map<Product, Integer> items;
    private double totalAmount;
    private double discount;
    private double amountToPay;
    private boolean checkedOut;
    private List<Order> orders;
}
