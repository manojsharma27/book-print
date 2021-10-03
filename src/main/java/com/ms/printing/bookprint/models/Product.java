package com.ms.printing.bookprint.models;

import com.ms.printing.bookprint.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.json.JsonObject;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    private UUID id;
    private ProductType type;
    private String name;
    private double price;
    private JsonObject detailsJson;
}
