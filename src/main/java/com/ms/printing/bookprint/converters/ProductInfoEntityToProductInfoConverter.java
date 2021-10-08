package com.ms.printing.bookprint.converters;

import com.ms.printing.bookprint.models.Product;
import com.ms.printing.bookprint.models.dto.ProductInfo;
import com.ms.printing.bookprint.repositories.entities.ProductInfoEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductInfoEntityToProductInfoConverter extends AbstractConverter<ProductInfoEntity, ProductInfo> {

    @Override
    public boolean canConvert(Object source, Class<?> clazz) {
        return source instanceof ProductInfoEntity && clazz.isAssignableFrom(ProductInfo.class);
    }

    public ProductInfo convert(ProductInfoEntity productInfoEntity) {
        ProductInfo productInfo = transform(productInfoEntity, ProductInfo.class);
        return productInfo;
    }
}