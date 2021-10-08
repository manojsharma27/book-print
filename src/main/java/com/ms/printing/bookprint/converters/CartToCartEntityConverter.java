package com.ms.printing.bookprint.converters;

import com.ms.printing.bookprint.models.Cart;
import com.ms.printing.bookprint.models.Product;
import com.ms.printing.bookprint.repositories.CartProductMappingRepository;
import com.ms.printing.bookprint.repositories.ProductRepository;
import com.ms.printing.bookprint.repositories.entities.CartEntity;
import com.ms.printing.bookprint.repositories.entities.CartProductMappingEntity;
import com.ms.printing.bookprint.repositories.entities.ProductEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class CartToCartEntityConverter extends AbstractConverter<Cart, CartEntity> {

    @Resource
    private ProductRepository productRepository;

    @Resource
    private CartProductMappingRepository cartProductMappingRepository;

    @Override
    public boolean canConvert(Object source, Class<?> clazz) {
        return source instanceof Cart && clazz.isAssignableFrom(CartEntity.class);
    }

    @Override
    public CartEntity convert(Cart cart) {
//        CartEntity cartEntity = CartEntity.builder()
//                .id(Objects.isNull(cart.getId()) ? UUID.randomUUID() : cart.getId())
//                .checkedOut(cart.isCheckedOut())
//                .price(cart.getTotalAmount())
//                .build();
        CartEntity cartEntity = transform(cart, CartEntity.class);
        Map<Product, Integer> items = cart.getItems();
        List<CartProductMappingEntity> cartProductMappingEntities;
        if (null == items || items.size() == 0) {
            cartProductMappingEntities = Collections.emptyList();
        } else {
            cartProductMappingEntities = new ArrayList<>(items.size());
            items.forEach((product, quantity) -> {
                ProductEntity productEntity = productRepository.getOne(product.getId());
                CartProductMappingEntity cartProductMappingEntity = CartProductMappingEntity.builder().cartEntity(cartEntity).productEntity(productEntity).quantity(quantity).build();
                cartProductMappingEntities.add(cartProductMappingEntity);
            });
        }

        cartEntity.setCartProductMappingEntities(cartProductMappingEntities);

        return cartEntity;
    }
}
