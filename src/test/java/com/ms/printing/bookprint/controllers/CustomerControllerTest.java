package com.ms.printing.bookprint.controllers;

import com.ms.printing.bookprint.models.Customer;
import com.ms.printing.bookprint.models.dto.CustomerOperationResponse;
import com.ms.printing.bookprint.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CustomerControllerTest {

    private static final UUID TEST_UUID = UUID.randomUUID();
    private static final String TEST_ID = TEST_UUID.toString();

    @InjectMocks private CustomerController customerController;
    @Mock private CustomerService customerService;
    @Mock private Customer customer;

    @Test
    public void testCreate() {
        when(customerService.create(any(Customer.class))).thenReturn(TEST_UUID);
        ResponseEntity<CustomerOperationResponse> responseEntity = customerController.create(customer);
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getMessage());
        assertTrue(responseEntity.getBody().getMessage().contains("Created"));
        assertNotNull(responseEntity.getBody().getCustomerId());
    }

    @Test
    public void testRead() {
        when(customerService.getById(any(UUID.class))).thenReturn(customer);
        ResponseEntity<Customer> responseEntity = customerController.read(TEST_ID);
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(customer, responseEntity.getBody());
    }

    @Test
    public void testReadByEmail() {
        when(customerService.getByEmail(anyString())).thenReturn(customer);
        ResponseEntity<Customer> responseEntity = customerController.readByEmail("abc@xyz.com");
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(customer, responseEntity.getBody());
    }

    @Test
    public void testDelete() {
        doNothing().when(customerService).delete(any(UUID.class));
        ResponseEntity<CustomerOperationResponse> responseEntity = customerController.delete(TEST_ID);
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getMessage());
        assertTrue(responseEntity.getBody().getMessage().contains("Deleted"));
        assertNotNull(responseEntity.getBody().getCustomerId());
    }
}