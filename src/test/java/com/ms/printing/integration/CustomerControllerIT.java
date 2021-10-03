package com.ms.printing.integration;

import com.ms.printing.bookprint.models.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomerControllerIT extends BookPrintITBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerControllerIT.class);

    @Before
    public void init() {
        setupBase();
    }

    @Test
    public void testCustomerCreateReadDelete() {
        Customer testCustomer = getTestCustomer();
        ResponseEntity<String> createEntity = createCustomer(testCustomer);
        assertNotNull(createEntity.getBody());
        UUID customerId = UUID.fromString(createEntity.getBody());
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
                .email("lalbahadurshastri@abc.com")
                .password("password")
                .build();
    }
}
