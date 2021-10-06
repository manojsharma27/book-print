package com.ms.printing.bookprint.strategy;

import com.ms.printing.bookprint.enums.ProductType;

public interface PricingStrategy {
    double getPrice(ProductType productType);
}