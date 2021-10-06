package com.ms.printing.bookprint.service;

import com.ms.printing.bookprint.models.Product;
import com.ms.printing.bookprint.models.dto.CartDto;

import java.util.UUID;

public interface CartService {

    CartDto getCart(UUID cartId);

    CartDto getCartForUser(UUID customerId);

    UUID addProduct(UUID cartId, Product product);

    void removeProduct(UUID cartId, UUID productId);

    void clear(UUID cartId);

    void checkout(UUID cartId);

    int updateQuantity(UUID cartId, UUID productId, int quantity);

    void deleteCart(UUID cartId);
}
