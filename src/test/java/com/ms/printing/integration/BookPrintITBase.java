package com.ms.printing.integration;

import com.ms.printing.bookprint.Application;
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
import com.ms.printing.bookprint.models.Order;
import com.ms.printing.bookprint.models.Product;
import com.ms.printing.bookprint.models.dto.CartDto;
import com.ms.printing.bookprint.models.dto.OperationResponse;
import com.ms.printing.bookprint.models.dto.CheckoutInfo;
import com.ms.printing.bookprint.models.dto.CustomerOperationResponse;
import com.ms.printing.bookprint.repositories.CartProductMappingRepository;
import com.ms.printing.bookprint.repositories.ProductRepository;
import com.ms.printing.integration.config.IntegrationTestConfig;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:applicationIT.properties")
@Import(value = {IntegrationTestConfig.class})
@RunWith(SpringRunner.class)
public abstract class BookPrintITBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookPrintITBase.class);
    private static final String CUSTOMER_API = "/v1/customer";
    private static final String CART_API = "/v1/cart";
    private static final String ORDERS_API = "/v1/orders";

    @LocalServerPort
    private int port = 0;

    @Autowired
    private SecurityProperties securityProperties;

    @Resource
    private ProductRepository productRepository;

    @Resource
    private CartProductMappingRepository cartProductMappingRepository;

    protected RestTemplate restTemplate;
    protected TestRestTemplate authenticatedTemplate;
    protected HttpHeaders httpHeaders;
    protected Set<UUID> createdCustomerIds;
    protected Set<UUID> createdProductIds;
    protected Set<UUID> createdCartIds;
    protected Set<UUID> createdOrderIds;

    protected void setupBase() {
        restTemplate = restTemplate();
        authenticatedTemplate = authenticatedTemplate();
        httpHeaders = getHeaders();
        createdCustomerIds = new HashSet<>();
        createdProductIds = new HashSet<>();
        createdCartIds = new HashSet<>();
        createdOrderIds = new HashSet<>();
    }

    protected String url(String partialUrl) {
        return "http://localhost:" + this.port + partialUrl;
    }

    private TestRestTemplate authenticatedTemplate() {
        TestRestTemplate testRestTemplate = new TestRestTemplate(securityProperties.getUser().getName(), securityProperties.getUser().getPassword());
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        messageConverters.add(converter);
        testRestTemplate.getRestTemplate().setMessageConverters(messageConverters);
        return testRestTemplate;
    }

    protected RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        return restTemplate;
    }

    protected HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

    protected ResponseEntity<CustomerOperationResponse> createCustomer(Customer customer) {
        String url = url(CUSTOMER_API);
        HttpEntity<Customer> httpEntity = new HttpEntity<>(customer, httpHeaders);
        ResponseEntity<CustomerOperationResponse> responseEntity = authenticatedTemplate.exchange(url, HttpMethod.POST, httpEntity, CustomerOperationResponse.class);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.toString(), HttpStatus.CREATED, responseEntity.getStatusCode());
        return responseEntity;
    }

    protected ResponseEntity<Customer> getCustomerById(UUID customerId) {
        String url = url(CUSTOMER_API) + "/" + customerId;
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Customer> responseEntity = authenticatedTemplate.exchange(url, HttpMethod.GET, httpEntity, Customer.class);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.toString(), HttpStatus.OK, responseEntity.getStatusCode());
        return responseEntity;
    }

    protected ResponseEntity<Customer> getCustomerByEmail(String email) {
        String url = url(CUSTOMER_API) + "?email=" + email;
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Customer> responseEntity = authenticatedTemplate.exchange(url, HttpMethod.GET, httpEntity, Customer.class);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.toString(), HttpStatus.OK, responseEntity.getStatusCode());
        return responseEntity;
    }

    protected void deleteCustomer(UUID customerId) {
        String url = url(CUSTOMER_API) + "/" + customerId;
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<CustomerOperationResponse> responseEntity = authenticatedTemplate.exchange(url, HttpMethod.DELETE, httpEntity, CustomerOperationResponse.class);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.toString(), HttpStatus.OK, responseEntity.getStatusCode());
    }

    protected ResponseEntity<CartDto> getCart(UUID cartId) {
        String url = url(CART_API) + "/" + cartId;
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<CartDto> responseEntity = authenticatedTemplate.exchange(url, HttpMethod.GET, httpEntity, CartDto.class);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.toString(), HttpStatus.OK, responseEntity.getStatusCode());
        return responseEntity;
    }

    protected ResponseEntity<CartDto> getCartUsingCustomerId(UUID customerId) {
        String url = url(CART_API) + "?customerId=" + customerId;
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<CartDto> responseEntity = authenticatedTemplate.exchange(url, HttpMethod.GET, httpEntity, CartDto.class);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.toString(), HttpStatus.OK, responseEntity.getStatusCode());
        return responseEntity;
    }

    protected void clearCart(UUID cartId) {
        String url = url(CART_API) + "/" + cartId + "/clear";
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<OperationResponse> responseEntity = authenticatedTemplate.exchange(url, HttpMethod.POST, httpEntity, OperationResponse.class);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.toString(), HttpStatus.OK, responseEntity.getStatusCode());
    }

    protected void deleteCart(UUID cartId) {
        String url = url(CART_API) + "/" + cartId;
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<OperationResponse> responseEntity = authenticatedTemplate.exchange(url, HttpMethod.DELETE, httpEntity, OperationResponse.class);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.toString(), HttpStatus.OK, responseEntity.getStatusCode());
    }

    protected ResponseEntity<OperationResponse> addProductToCart(UUID cartId, Product product) {
        String url = url(CART_API) + "/" + cartId + "/addProduct";
        ResponseEntity<OperationResponse> responseEntity = authenticatedTemplate.postForEntity(url, product, OperationResponse.class);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.toString(), HttpStatus.OK, responseEntity.getStatusCode());
        return responseEntity;
    }

    protected ResponseEntity<OperationResponse> checkoutCart(UUID cartId, CheckoutInfo checkoutInfo) {
        String url = url(CART_API) + "/" + cartId + "/checkout";
        ResponseEntity<OperationResponse> responseEntity = authenticatedTemplate.postForEntity(url, checkoutInfo, OperationResponse.class);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.toString(), HttpStatus.CREATED, responseEntity.getStatusCode());
        return responseEntity;
    }

    protected ResponseEntity<List<Order>> getOrdersByCustomerId(UUID customerId) {
        String url = url(ORDERS_API) + "?customerId=" + customerId;
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<Order>> responseEntity = authenticatedTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<Order>>() {});
        assertNotNull(responseEntity);
        assertEquals(responseEntity.toString(), HttpStatus.OK, responseEntity.getStatusCode());
        return responseEntity;
    }

    protected ResponseEntity<OperationResponse> deleteOrder(UUID orderId) {
        String url = url(ORDERS_API) + "/" + orderId;
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<OperationResponse> responseEntity = authenticatedTemplate.exchange(url, HttpMethod.DELETE, httpEntity, OperationResponse.class);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.toString(), HttpStatus.OK, responseEntity.getStatusCode());
        return responseEntity;
    }

    protected Book getResearchBook() {
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

    protected Book getStoryBook() {
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

    protected void cleanupAll() {
        cleanupOrders();
        cleanupCarts();
        cleanupTestCustomers();
        cleanupProducts();
    }

    protected void cleanupTestCustomers() {
        for (UUID customerId : createdCustomerIds) {
            try {
                deleteCustomer(customerId);
            } catch (RuntimeException e) {
                LOGGER.warn("Error deleting customer with ID : " + customerId);
            }
        }
    }

    @Transactional
    protected void cleanupProducts() {
        for (UUID productId : createdProductIds) {
            try {
                productRepository.deleteById(productId);
            } catch (RuntimeException e) {
                LOGGER.warn("Error deleting cart with ID : " + productId);
            }
        }
    }

    protected void cleanupCarts() {
        for (UUID cartId : createdCartIds) {
            try {
                clearCart(cartId);
                deleteCart(cartId);
            } catch (RuntimeException e) {
                LOGGER.warn("Error deleting cart with ID : " + cartId);
            }
        }
    }

    protected void cleanupOrders() {
        for (UUID orderId : createdOrderIds) {
            try {
                deleteOrder(orderId);
            } catch (RuntimeException e) {
                LOGGER.warn("Error deleting order with ID : " + orderId);
            }
        }
    }
}
