package com.ms.printing.integration;

import com.ms.printing.bookprint.enums.PaymentMethod;
import com.ms.printing.bookprint.models.Book;
import com.ms.printing.bookprint.models.Customer;
import com.ms.printing.bookprint.models.Order;
import com.ms.printing.bookprint.models.dto.CartDto;
import com.ms.printing.bookprint.models.dto.OperationResponse;
import com.ms.printing.bookprint.models.dto.CheckoutInfo;
import com.ms.printing.bookprint.models.dto.CustomerOperationResponse;
import com.ms.printing.bookprint.models.dto.ProductQuantity;
import com.ms.printing.bookprint.strategy.payment.CreditDebitCard;
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

public class OrderControllerIT extends BookPrintITBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderControllerIT.class);

    @Before
    public void init() {
        setupBase();
    }

    @Test
    public void testEndToEndCheckoutWorkflow() {
        UUID customerId = createTestCustomer();

        CartDto cart = getTestCart(customerId);
        UUID cartId = cart.getId();
        assertTrue(CollectionUtils.isEmpty(cart.getProducts()));
        createdCartIds.add(cartId);

        Book researchBook = getResearchBook();
        OperationResponse operationResponse = addBookInCart(cartId, researchBook);
        createdProductIds.add(operationResponse.getProductId());

        Book storyBook = getStoryBook();
        operationResponse = addBookInCart(cartId, storyBook);
        createdProductIds.add(operationResponse.getProductId());
        cart = getTestCart(customerId);

        List<ProductQuantity> products = cart.getProducts();
        assertFalse(CollectionUtils.isEmpty(products));
        assertEquals(2, products.size());

        storyBook.setId(products.get(1).getProduct().getId());
        addBookInCart(cartId, storyBook);

        CheckoutInfo checkoutInfo = testCheckoutInfo();
        ResponseEntity<OperationResponse> checkoutRespEntity = checkoutCart(cartId, checkoutInfo);
        assertNotNull(checkoutRespEntity.getBody());
        assertNotNull(checkoutRespEntity.getBody().getMessage());
        assertTrue(checkoutRespEntity.getBody().getMessage().contentEquals("Placed 2 orders!"));

        ResponseEntity<List<Order>> ordersRespEntity = getOrdersByCustomerId(customerId);
        List<Order> orders = ordersRespEntity.getBody();
        assertFalse(CollectionUtils.isEmpty(orders));
        assertEquals(2, orders.size());
        orders.stream().map(Order::getOrderId).forEach(createdOrderIds::add);

        assertEquals(customerId, orders.get(0).getCustomerId());
        assertEquals(customerId, orders.get(1).getCustomerId());

        assertEquals(researchBook.getName(), orders.get(0).getProduct().getName());
        assertEquals(storyBook.getName(), orders.get(1).getProduct().getName());

        assertEquals(1, orders.get(0).getQuantity());
        assertEquals(2, orders.get(1).getQuantity());
    }

    private CheckoutInfo testCheckoutInfo() {
        CreditDebitCard creditDebitCard = CreditDebitCard.builder()
                .cardNumber("9A7C-EECA-96BFF-24FB")
                .cvv("124")
                .dateOfExpiry("12/24")
                .name("Vivek Anand").build();
        return CheckoutInfo.builder()
                .paymentMethod(PaymentMethod.CARD)
                .card(creditDebitCard)
                .build();
    }

    private OperationResponse addBookInCart(UUID cartId, Book book) {
        ResponseEntity<OperationResponse> cartUpdateRespEntity = addProductToCart(cartId, book);
        OperationResponse operationResponse = cartUpdateRespEntity.getBody();
        assertNotNull(operationResponse);
        assertEquals(cartId, operationResponse.getCartId());
        assertNotNull(operationResponse.getProductId());
        return operationResponse;
    }

    private CartDto getTestCart(UUID customerId) {
        ResponseEntity<CartDto> cartResponseEntity = getCartUsingCustomerId(customerId);
        CartDto cart = cartResponseEntity.getBody();
        assertNotNull(cart);
        UUID cartId = cart.getId();
        return cart;
    }

    private UUID createTestCustomer() {
        ResponseEntity<CustomerOperationResponse> createEntity = createCustomer(getTestCustomer());
        assertNotNull(createEntity.getBody());
        assertNotNull(createEntity.getBody().getCustomerId());
        UUID customerId = createEntity.getBody().getCustomerId();
        createdCustomerIds.add(customerId);
        return customerId;
    }

    @After
    public void tearDown() {
        cleanupAll();
    }

    private Customer getTestCustomer() {
        return Customer.builder()
                .firstname("Rakesh")
                .lastname("Jhunjhunwala")
                .address("India")
                .pincode(411006)
                .phoneNumber("+91 1234567890")
                .email("rakesh" + Math.abs(UUID.randomUUID().hashCode()) + "@abc.com")
                .password("password")
                .build();
    }

}
