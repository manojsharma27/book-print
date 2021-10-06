package com.ms.printing.bookprint.models.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.ms.printing.bookprint.models.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductQuantity {
    private Product product;
    private int quantity;
}
