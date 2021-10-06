package com.ms.printing.integration;

import com.ms.printing.bookprint.enums.BindingDirection;
import com.ms.printing.bookprint.enums.BindingType;
import com.ms.printing.bookprint.enums.BookType;
import com.ms.printing.bookprint.enums.CoverType;
import com.ms.printing.bookprint.enums.PaperType;
import com.ms.printing.bookprint.enums.ProductType;
import com.ms.printing.bookprint.enums.Size;
import com.ms.printing.bookprint.models.Binding;
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
        assertTrue(CollectionUtils.isEmpty(cart.getOrders()));

        Book researchBook = getResearchBook();
        ResponseEntity<CartOperationResponse> cartUpdateRespEntity = addProductToCart(cartId, researchBook);
        CartOperationResponse cartOperationResponse = cartUpdateRespEntity.getBody();
        assertNotNull(cartOperationResponse);
        assertEquals(cartId, cartOperationResponse.getCartId());
        assertNotNull(cartOperationResponse.getProductId());
        createdProductIds.add(cartOperationResponse.getProductId());

        Book storyBook = getStoryBook();
        cartUpdateRespEntity = addProductToCart(cartId, storyBook);
        assertNotNull(cartUpdateRespEntity.getBody());
        assertNotNull(cartOperationResponse.getProductId());
        createdProductIds.add(cartOperationResponse.getProductId());
        cartResponseEntity = getCartUsingCustomerId(customerId);
        cart = cartResponseEntity.getBody();
        assertNotNull(cart);

        List<ProductQuantity> products = cart.getProducts();
        assertFalse(CollectionUtils.isEmpty(products));
        assertTrue(CollectionUtils.isEmpty(cart.getOrders()));
        assertEquals(2, products.size());

        assertEquals(researchBook.getName(), products.get(0).getProduct().getName());
        assertEquals(storyBook.getName(), products.get(1).getProduct().getName());
        assertEquals(1, products.get(0).getQuantity());
        assertEquals(1, products.get(1).getQuantity());

        storyBook.setId(products.get(1).getProduct().getId());
        cartUpdateRespEntity = addProductToCart(cartId, storyBook);
        assertNotNull(cartUpdateRespEntity.getBody());
        assertNotNull(cartOperationResponse.getProductId());

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

    private Book getResearchBook() {
        return Book.builder()
                .name("Research book")
                .price(3500)
                .productType(ProductType.BOOK)

                .bookType(BookType.E_BOOK)
                .binding(Binding.builder().type(BindingType.REGULAR).direction(BindingDirection.LEFT).build())
                .paperType(PaperType.BOND)
                .size(Size.A4)
                .pages(268)

                .coverType(CoverType.SOFT)
                .build();
    }

    private Book getStoryBook() {
        return Book.builder()
                .name("Harry Potter and the Chamber of Secrets")
                .price(1500)
                .productType(ProductType.BOOK)

                .bookType(BookType.PAPER)
                .binding(Binding.builder().type(BindingType.SPIRAL).direction(BindingDirection.LEFT).build())
                .paperType(PaperType.REGULAR)
                .size(Size.A4)
                .pages(301)

                .coverType(CoverType.HARD)
                .build();
    }
}
