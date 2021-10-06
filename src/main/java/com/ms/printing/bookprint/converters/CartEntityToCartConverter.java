package com.ms.printing.bookprint.converters;

import com.ms.printing.bookprint.models.Cart;
import com.ms.printing.bookprint.models.Product;
import com.ms.printing.bookprint.repositories.entities.CartEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CartEntityToCartConverter extends AbstractConverter<CartEntity, Cart> {

    @Resource
    private ProductEntityToProductConverter productEntityToProductConverter;

    @Override
    public boolean canConvert(Object source, Class<?> clazz) {
        return source instanceof CartEntity && clazz.isAssignableFrom(Cart.class);
    }

    @Override
    public Cart convert(CartEntity cartEntity) {
        Cart cart = transform(cartEntity, Cart.class);
        cart.setTotalAmount(cartEntity.getPrice());
        cart.setAmountToPay(cartEntity.getPrice());

        Map<Product, Integer> productQuantityMap = new LinkedHashMap<>();
        if (CollectionUtils.isNotEmpty(cartEntity.getCartProductMappingEntities())) {
            cartEntity.getCartProductMappingEntities().forEach(mappingEntity -> {
                Product product = productEntityToProductConverter.convert(mappingEntity.getProductEntity());
                productQuantityMap.put(product, mappingEntity.getQuantity());
            });
        }
        cart.setItems(productQuantityMap);
        // populate orders
        return cart;
    }
}
