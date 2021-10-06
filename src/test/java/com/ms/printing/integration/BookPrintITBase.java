package com.ms.printing.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ms.printing.bookprint.Application;
import com.ms.printing.bookprint.models.Customer;
import com.ms.printing.bookprint.models.Product;
import com.ms.printing.bookprint.models.dto.CartDto;
import com.ms.printing.bookprint.models.dto.CartOperationResponse;
import com.ms.printing.bookprint.models.dto.CustomerOperationResponse;
import com.ms.printing.bookprint.util.ObjectMapperProvider;
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

    @LocalServerPort
    private int port = 0;

    @Autowired
    private SecurityProperties securityProperties;

    @Resource
    private ObjectMapperProvider objectMapperProvider;

    protected RestTemplate restTemplate;
    protected TestRestTemplate authenticatedTemplate;
    protected HttpHeaders httpHeaders;
    protected Set<UUID> createdCustomerIds;
    protected Set<UUID> createdProductIds;
    protected Set<UUID> createdCartIds;

    protected void setupBase() {
        restTemplate = restTemplate();
        authenticatedTemplate = authenticatedTemplate();
        httpHeaders = getHeaders();
        createdCustomerIds = new HashSet<>();
        createdProductIds = new HashSet<>();
        createdCartIds = new HashSet<>();
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

    protected void cleanupTestCustomers() {
        for (UUID customerId : createdCustomerIds) {
            try {
                deleteCustomer(customerId);
            } catch (RuntimeException e) {
                LOGGER.warn("Error deleting customer with ID : " + customerId);
            }
        }
    }

    protected void cleanupCarts() {
        for (UUID cartId : createdCartIds) {
            try {
                deleteCart(cartId);
            } catch (RuntimeException e) {
                LOGGER.warn("Error deleting cart with ID : " + cartId);
            }
        }
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

    protected ResponseEntity<CustomerOperationResponse> deleteCustomer(UUID customerId) {
        String url = url(CUSTOMER_API) + "/" + customerId;
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<CustomerOperationResponse> responseEntity = authenticatedTemplate.exchange(url, HttpMethod.DELETE, httpEntity, CustomerOperationResponse.class);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.toString(), HttpStatus.OK, responseEntity.getStatusCode());
        return responseEntity;
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

    protected ResponseEntity<CartOperationResponse> deleteCart(UUID cartId) {
        String url = url(CART_API) + "/" + cartId;
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<CartOperationResponse> responseEntity = authenticatedTemplate.exchange(url, HttpMethod.DELETE, httpEntity, CartOperationResponse.class);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.toString(), HttpStatus.OK, responseEntity.getStatusCode());
        return responseEntity;
    }

    protected ResponseEntity<CartOperationResponse> addProductToCart(UUID cartId, Product product) {
        String url = url(CART_API) + "/" + cartId + "/addProduct";
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<CartOperationResponse> responseEntity = authenticatedTemplate.postForEntity(url, product, CartOperationResponse.class);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.toString(), HttpStatus.OK, responseEntity.getStatusCode());
        return responseEntity;
    }

    private String objectToJson(Object obj) {
        try {
            return objectMapperProvider.getObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("Cannot convert object to json");
            throw new RuntimeException(e);
        }
    }

}
