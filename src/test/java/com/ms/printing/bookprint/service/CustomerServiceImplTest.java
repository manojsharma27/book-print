package com.ms.printing.bookprint.service;

import com.ms.printing.bookprint.converters.DataModelMapper;
import com.ms.printing.bookprint.exceptions.DataNotFoundException;
import com.ms.printing.bookprint.models.Customer;
import com.ms.printing.bookprint.repositories.CustomerRepository;
import com.ms.printing.bookprint.repositories.entities.CustomerEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CustomerServiceImplTest {

    private static final UUID TEST_UUID = UUID.randomUUID();

    @InjectMocks private CustomerServiceImpl customerService;
    @Mock private DataModelMapper dataModelMapper;
    @Mock private CustomerRepository customerRepository;
    @Mock private Customer customer;
    @Mock private CustomerEntity customerEntity;

    @Test
    public void testCreate() {
        ArgumentCaptor<UUID> uuidCaptor = ArgumentCaptor.forClass(UUID.class);
        doNothing().when(customer).setId(uuidCaptor.capture());
        when(dataModelMapper.map(any(Customer.class), eq(CustomerEntity.class))).thenReturn(customerEntity);
        when(customerRepository.saveAndFlush(any(CustomerEntity.class))).thenReturn(customerEntity);
        UUID uuid = customerService.create(customer);
        assertNull(uuid);
        assertNotNull(uuidCaptor.getValue());
    }

    @Test
    public void testGetById() {
        when(customerRepository.findById(any(UUID.class))).thenReturn(Optional.of(customerEntity));
        when(dataModelMapper.map(any(CustomerEntity.class), eq(Customer.class))).thenReturn(customer);
        Customer result = customerService.getById(TEST_UUID);
        assertNotNull(result);
        assertEquals(customer, result);
    }

    @Test(expected = DataNotFoundException.class)
    public void testGetByIdWhenEntityNotFound() {
        when(customerRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        customerService.getById(TEST_UUID);
    }

    @Test
    public void testGetByEmail() {
        when(customerRepository.findByEmail(anyString())).thenReturn(customerEntity);
        when(dataModelMapper.map(any(CustomerEntity.class), eq(Customer.class))).thenReturn(customer);
        Customer result = customerService.getByEmail("some@one.com");
        assertNotNull(result);
        assertEquals(customer, result);
    }

    @Test
    public void testUpdate() {
        when(dataModelMapper.map(any(Customer.class), eq(CustomerEntity.class))).thenReturn(customerEntity);
        when(customerRepository.saveAndFlush(any(CustomerEntity.class))).thenReturn(customerEntity);
        customerService.update(customer);
        verify(customerRepository).saveAndFlush(eq(customerEntity));
    }

    @Test
    public void testDelete() {
        doNothing().when(customerRepository).deleteById(any(UUID.class));
        customerService.delete(TEST_UUID);
        verify(customerRepository).deleteById(eq(TEST_UUID));
    }
}