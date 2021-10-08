package com.ms.printing.integration;

import com.ms.printing.bookprint.models.Book;
import com.ms.printing.bookprint.models.Customer;
import com.ms.printing.bookprint.models.dto.CartDto;
import com.ms.printing.bookprint.models.dto.CartOperationResponse;
import com.ms.printing.bookprint.models.dto.ProductQuantity;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CartControllerIT extends BookPrintITBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartControllerIT.class);

    @Before
    public void init() {
        setupBase();
    }

    @Test
    public void testEndToEndCartWorkflow() {
/*
        ResponseEntity<CustomerOperationResponse> createEntity = createCustomer(getTestCustomer());
        assertNotNull(createEntity.getBody());
        assertNotNull(createEntity.getBody().getCustomerId());
        UUID customerId = createEntity.getBody().getCustomerId();

        createdCustomerIds.add(customerId);
*/
        UUID customerId = UUID.fromString("c2f02bae-995c-4052-9809-55ae9d079f77");
        ResponseEntity<CartDto> cartResponseEntity = getCartUsingCustomerId(customerId);
        CartDto cart = cartResponseEntity.getBody();
        assertNotNull(cart);
        UUID cartId = cart.getId();
        createdCartIds.add(cartId);

        assertTrue(CollectionUtils.isEmpty(cart.getProducts()));

        Book researchBook = getResearchBook();
        ResponseEntity<OperationResponse> cartUpdateRespEntity = addProductToCart(cartId, researchBook);
        OperationResponse operationResponse = cartUpdateRespEntity.getBody();
        assertNotNull(operationResponse);
        assertEquals(cartId, operationResponse.getCartId());
        assertNotNull(operationResponse.getProductId());
        createdProductIds.add(operationResponse.getProductId());

        Book storyBook = getStoryBook();
        cartUpdateRespEntity = addProductToCart(cartId, storyBook);
        assertNotNull(cartUpdateRespEntity.getBody());
        assertNotNull(operationResponse.getProductId());
        createdProductIds.add(operationResponse.getProductId());
        cartResponseEntity = getCartUsingCustomerId(customerId);
        cart = cartResponseEntity.getBody();
        assertNotNull(cart);

        List<ProductQuantity> products = cart.getProducts();
        assertFalse(CollectionUtils.isEmpty(products));
        assertEquals(2, products.size());

        assertEquals(researchBook.getName(), products.get(0).getProduct().getName());
        assertEquals(storyBook.getName(), products.get(1).getProduct().getName());
        assertEquals(1, products.get(0).getQuantity());
        assertEquals(1, products.get(1).getQuantity());

        storyBook.setId(products.get(1).getProduct().getId());
        cartUpdateRespEntity = addProductToCart(cartId, storyBook);
        assertNotNull(cartUpdateRespEntity.getBody());
        assertNotNull(operationResponse.getProductId());

        cartResponseEntity = getCart(cartId);
        cart = cartResponseEntity.getBody();
        assertNotNull(cart);
        products = cart.getProducts();
        assertEquals(2, products.size());
        assertEquals(2, products.get(1).getQuantity());
    }

    @After
    public void tearDown() {
        cleanupCarts();
//        cleanupTestCustomers();
    }

    private Customer getTestCustomer() {
        return Customer.builder()
                .firstname("Swami")
                .lastname("VivekAnand")
                .address("India")
                .pincode(411006)
                .phoneNumber("+91 1234567890")
                .email("swamivivek" + Math.abs(UUID.randomUUID().hashCode()) + "@abc.com")
                .password("password")
                .build();
    }

}
