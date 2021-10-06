package com.ms.printing.bookprint.converters;

import com.ms.printing.bookprint.models.Product;
import com.ms.printing.bookprint.repositories.entities.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductEntityToProductConverter extends AbstractConverter<ProductEntity, Product> {

    @Override
    public boolean canConvert(Object source, Class<?> clazz) {
        return source instanceof ProductEntity && clazz.isAssignableFrom(Product.class);
    }

    public Product convert(ProductEntity productEntity) {
        String productJson = productEntity.getDetailsJson();
        return jsonUtil.parse(productJson, Product.class);
    }
}