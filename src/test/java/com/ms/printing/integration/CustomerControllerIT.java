package com.ms.printing.integration;

import com.ms.printing.bookprint.models.Customer;
import com.ms.printing.bookprint.models.dto.CustomerOperationResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CustomerControllerIT extends BookPrintITBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerControllerIT.class);

    @Before
    public void init() {
        setupBase();
    }

    @Test
    public void testCustomerCreateReadDelete() {
        Customer testCustomer = getTestCustomer();
        ResponseEntity<CustomerOperationResponse> createEntity = createCustomer(testCustomer);
        assertNotNull(createEntity.getBody());
        assertNotNull(createEntity.getBody().getCustomerId());
        UUID customerId = createEntity.getBody().getCustomerId();
        createdCustomerIds.add(customerId);
        ResponseEntity<Customer> customerEntity = getCustomerById(customerId);
        Customer customer = customerEntity.getBody();
        assertNotNull(customer);
        assertEquals(testCustomer.getFirstname(), customer.getFirstname());
        assertEquals(testCustomer.getLastname(), customer.getLastname());
        assertEquals(testCustomer.getAddress(), customer.getAddress());
        assertEquals(testCustomer.getPincode(), customer.getPincode());
        assertEquals(testCustomer.getPhoneNumber(), customer.getPhoneNumber());
        assertEquals(testCustomer.getEmail(), customer.getEmail());
        assertEquals(testCustomer.getPassword(), customer.getPassword());

        customerEntity = getCustomerByEmail(testCustomer.getEmail());
        customer = customerEntity.getBody();
        assertNotNull(customer);
        assertEquals(customerId, customer.getId());
    }

    @Test
    public void testCustomerCreateWithNoData() {
        String url = url(CUSTOMER_API);
        HttpEntity<Customer> httpEntity = new HttpEntity<>(new Customer(), httpHeaders);
        ResponseEntity<CustomerOperationResponse> responseEntity = authenticatedTemplate.exchange(url, HttpMethod.POST, httpEntity, CustomerOperationResponse.class);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @After
    public void tearDown() {
        cleanupTestCustomers();
    }

    private Customer getTestCustomer() {
        return Customer.builder()
                .firstname("Lal Bahadur")
                .lastname("Shastri")
                .address("India")
                .pincode(411029)
                .phoneNumber("+91 1234567890")
                .email("lbshastri" + Math.abs(UUID.randomUUID().hashCode()) + "@abc.com")
                .password("password")
                .build();
    }
}
