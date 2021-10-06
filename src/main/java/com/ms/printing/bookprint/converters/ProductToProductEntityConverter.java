package com.ms.printing.bookprint.converters;

import com.ms.printing.bookprint.models.Product;
import com.ms.printing.bookprint.repositories.ProductInfoRepository;
import com.ms.printing.bookprint.repositories.entities.ProductEntity;
import com.ms.printing.bookprint.repositories.entities.ProductInfoEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ProductToProductEntityConverter extends AbstractConverter<Product, ProductEntity> {

    @Resource
    private ProductInfoRepository productInfoRepository;

    @Override
    public boolean canConvert(Object source, Class<?> clazz) {
        return source instanceof Product && clazz.isAssignableFrom(ProductEntity.class);
    }

    public ProductEntity convert(Product product) {
        ProductEntity productEntity = transform(product, ProductEntity.class);
        ProductInfoEntity productInfoEntity = productInfoRepository.findFirstByProductTypeEntityType(product.getProductType());
        productEntity.setDetailsJson(jsonUtil.toJson(product));
        productEntity.setProductInfoEntity(productInfoEntity);
        return productEntity;
    }
}
