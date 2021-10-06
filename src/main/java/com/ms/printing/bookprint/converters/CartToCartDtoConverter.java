package com.ms.printing.bookprint.converters;

import com.ms.printing.bookprint.models.Cart;
import com.ms.printing.bookprint.models.dto.CartDto;
import com.ms.printing.bookprint.models.dto.ProductQuantity;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartToCartDtoConverter extends AbstractConverter<Cart, CartDto> {

    @Override
    public boolean canConvert(Object source, Class<?> clazz) {
        return source instanceof Cart && clazz.isAssignableFrom(CartDto.class);
    }

    public CartDto convert(Cart cart) {
        CartDto cartDto = transform(cart, CartDto.class);
//        CartDto cartDto = CartDto.builder()
//                .id(cart.getId())
//                .checkedOut(cart.isCheckedOut())
//                .totalAmount(cart.getTotalAmount())
//                .amountToPay(cart.getAmountToPay())
//                .discount(0)
//                .orders(Collections.emptyList())
//                .build();

        if (MapUtils.isEmpty(cart.getItems())) {
            cartDto.setProducts(Collections.emptyList());
        } else {
            List<ProductQuantity> productQuantities = cart.getItems().entrySet().stream().map(entry -> ProductQuantity.builder().product(entry.getKey()).quantity(entry.getValue()).build()).collect(Collectors.toList());
            cartDto.setProducts(productQuantities);
        }
        // populate orders
        return cartDto;
    }
}
