package com.ms.printing.bookprint.service;

import com.ms.printing.bookprint.models.Customer;

import java.util.UUID;

public interface CustomerService {

    UUID create(Customer customer);

    Customer getById(UUID customerId);

    Customer getByEmail(String email);

    void update(Customer customer);

    void delete(UUID customerId);
}
