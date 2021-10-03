package com.ms.printing.integration;

import com.ms.printing.bookprint.Application;
import com.ms.printing.bookprint.models.Customer;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:applicationIT.properties")
@RunWith(SpringRunner.class)
public abstract class BookPrintITBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookPrintITBase.class);
    private static final String CUSTOMER_API = "/v1/customer";

    @LocalServerPort
    private int port = 0;

    @Autowired
    private SecurityProperties securityProperties;

    protected TestRestTemplate restTemplate;
    protected TestRestTemplate authenticatedTemplate;
    protected HttpHeaders httpHeaders;
    protected Set<UUID> createdCustomerIds;

    protected void setupBase() {
        restTemplate = template();
        authenticatedTemplate = authenticatedTemplate();
        httpHeaders = getHeaders();
        createdCustomerIds = new HashSet<>();
    }

    protected TestRestTemplate template() {
        return new TestRestTemplate();
    }

    protected TestRestTemplate authenticatedTemplate() {
        return new TestRestTemplate(securityProperties.getUser().getName(), securityProperties.getUser().getPassword());
    }

    protected String url(String partialUrl) {
        return "http://localhost:" + this.port + partialUrl;
    }

    protected int getServerPort() {
        return this.port;
    }

    protected ResponseEntity<String> createCustomer(Customer customer) {
        String url = url(CUSTOMER_API);
        HttpEntity<Customer> httpEntity = new HttpEntity<>(customer, httpHeaders);
        ResponseEntity<String> responseEntity = getRestTemplate().exchange(url, HttpMethod.POST, httpEntity, String.class);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.toString(), HttpStatus.CREATED, responseEntity.getStatusCode());
        return responseEntity;
    }

    private TestRestTemplate getRestTemplate() {
        return authenticatedTemplate;
    }

    protected ResponseEntity<Customer> getCustomerById(UUID customerId) {
        String url = url(CUSTOMER_API) + "/" + customerId;
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Customer> responseEntity = getRestTemplate().exchange(url, HttpMethod.GET, httpEntity, Customer.class);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.toString(), HttpStatus.OK, responseEntity.getStatusCode());
        return responseEntity;
    }

    protected ResponseEntity<Customer> getCustomerByEmail(String email) {
        String url = url(CUSTOMER_API) + "?email=" + email;
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Customer> responseEntity = getRestTemplate().exchange(url, HttpMethod.GET, httpEntity, Customer.class);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.toString(), HttpStatus.OK, responseEntity.getStatusCode());
        return responseEntity;
    }

    protected ResponseEntity<String> deleteCustomer(UUID customerId) {
        String url = url(CUSTOMER_API) + "/" + customerId;
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = getRestTemplate().exchange(url, HttpMethod.DELETE, httpEntity, String.class);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.toString(), HttpStatus.OK, responseEntity.getStatusCode());
        return responseEntity;
    }

    protected HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
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
}
