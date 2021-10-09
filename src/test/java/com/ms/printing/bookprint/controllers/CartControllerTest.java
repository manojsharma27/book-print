package com.ms.printing.bookprint.controllers;

import com.ms.printing.bookprint.models.Product;
import com.ms.printing.bookprint.models.dto.CartDto;
import com.ms.printing.bookprint.models.dto.CheckoutInfo;
import com.ms.printing.bookprint.models.dto.OperationResponse;
import com.ms.printing.bookprint.service.CartService;
import com.ms.printing.bookprint.service.CheckoutService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CartControllerTest {

    private static final UUID TEST_UUID = UUID.randomUUID();
    private static final String TEST_ID = TEST_UUID.toString();

    @InjectMocks private CartController cartController;
    @Mock private CartService cartService;
    @Mock private CheckoutService checkoutService;
    @Mock private CartDto cartDto;
    @Mock private Product product;

    @Before
    public void setup() {
        when(cartService.getCart(any(UUID.class))).thenReturn(cartDto);
        when(cartService.getCartForUser(any(UUID.class))).thenReturn(cartDto);
        when(cartService.addProduct(any(UUID.class), any(Product.class))).thenReturn(TEST_UUID);
        doNothing().when(cartService).removeProduct(any(UUID.class), any(UUID.class));
        doNothing().when(cartService).removeProduct(any(UUID.class),any(UUID.class));
        when(cartService.updateQuantity(any(UUID.class), any(UUID.class), anyInt())).thenReturn(1);
        doNothing().when(cartService).updateBookTypes(any(UUID.class), anyMap());
        doNothing().when(cartService).clear(any(UUID.class));
        doNothing().when(cartService).deleteCart(any(UUID.class));
    }

    @Test
    public void testGetCart() {
        ResponseEntity<CartDto> responseEntity = cartController.getCart(TEST_ID);
        assertNotNull(responseEntity);
        assertEquals(cartDto, responseEntity.getBody());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCartWithEmptyValue() {
        cartController.getCart("");
    }

    @Test
    public void testGetCartForUser() {
        ResponseEntity<CartDto> responseEntity = cartController.getCartForUser(TEST_ID);
        assertNotNull(responseEntity);
        assertEquals(cartDto, responseEntity.getBody());
    }

    @Test
    public void testAddProduct() {
        ResponseEntity<OperationResponse> responseEntity = cartController.addProduct(TEST_ID, product);
        validateResponse(responseEntity, "added");
    }

    private void validateResponse(ResponseEntity<OperationResponse> responseEntity, String added) {
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody().getMessage().contains(added));
    }

    @Test
    public void testRemoveProduct() {
        ResponseEntity<OperationResponse> responseEntity = cartController.removeProduct(TEST_ID, TEST_ID);
        validateResponse(responseEntity, "removed");
    }

    @Test
    public void testUpdateQuantity() {
        ResponseEntity<OperationResponse> responseEntity = cartController.updateQuantity(TEST_ID, TEST_ID, 1);
        validateResponse(responseEntity, "Updated");
    }

    @Test
    public void testUpdateBookTypes() {
        ResponseEntity<OperationResponse> responseEntity = cartController.updateBookTypes(TEST_ID, Collections.emptyMap());
        validateResponse(responseEntity, "Updated book types");
    }

    @Test
    public void testClearCart() {
        ResponseEntity<OperationResponse> responseEntity = cartController.clearCart(TEST_ID);
        validateResponse(responseEntity, "cleared");
    }

    @Test
    public void testDelete() {
        ResponseEntity<OperationResponse> responseEntity = cartController.delete(TEST_ID);
        validateResponse(responseEntity, "deleted");
    }

    @Test
    public void testCheckout() {
        when(checkoutService.checkout(any(UUID.class), any(CheckoutInfo.class))).thenReturn(OperationResponse.builder().message("Placed x orders").build());
        ResponseEntity<OperationResponse> responseEntity = cartController.checkout(TEST_ID, new CheckoutInfo());
        validateResponse(responseEntity, "Placed");
    }
}