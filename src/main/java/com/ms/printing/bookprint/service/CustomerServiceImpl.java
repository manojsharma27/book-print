package com.ms.printing.bookprint.service;

import com.ms.printing.bookprint.converters.DataModelMapper;
import com.ms.printing.bookprint.exceptions.BookPrintException;
import com.ms.printing.bookprint.exceptions.DataNotFoundException;
import com.ms.printing.bookprint.models.Customer;
import com.ms.printing.bookprint.repositories.CustomerRepository;
import com.ms.printing.bookprint.repositories.entities.CustomerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Resource
    private DataModelMapper dataModelMapper;

    @Resource
    private CustomerRepository customerRepository;

    @Override
    public UUID create(Customer customer) {
        if (Objects.isNull(customer)) {
            throw new BookPrintException("Invalid input");
        }
        if (Objects.isNull(customer.getId())) {
            customer.setId(UUID.randomUUID());
        }
        CustomerEntity customerEntity = dataModelMapper.map(customer, CustomerEntity.class);
        CustomerEntity savedEntity = customerRepository.saveAndFlush(customerEntity);
        return savedEntity.getId();
    }

    @Override
    public Customer getById(UUID customerId) {
        Optional<CustomerEntity> optional = customerRepository.findById(customerId);
        if (optional.isPresent()) {
            Customer customer = dataModelMapper.map(optional.get(), Customer.class);
            return customer;
        }
        throw new DataNotFoundException("Customer with id:" + customerId + " not found");
    }

    @Override
    public Customer getByEmail(String email) {
        CustomerEntity customerEntity = customerRepository.findByEmail(email);
        if (Objects.isNull(customerEntity)) {
            throw new DataNotFoundException("Customer with email:" + email + " not found");
        }
        Customer customer = dataModelMapper.map(customerEntity, Customer.class);
        return customer;
    }

    @Override
    public void update(Customer customer) {
        if (Objects.isNull(customer)) {
            throw new BookPrintException("Invalid input");
        }
        CustomerEntity customerEntity = dataModelMapper.map(customer, CustomerEntity.class);
        customerRepository.saveAndFlush(customerEntity);
    }

    @Override
    public void delete(UUID customerId) {
        customerRepository.deleteById(customerId);
        LOGGER.info("Deleted customer with id:" + customerId);
    }

}
