package com.ms.printing.bookprint.models.dto;

import com.ms.printing.bookprint.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductInfo {

    private int id;
    private ProductType productType;
    private String description;
    private String imageUrl;
    private double basePrice;

}
