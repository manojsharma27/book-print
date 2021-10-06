package com.ms.printing.bookprint.converters;

import com.ms.printing.bookprint.models.Customer;
import com.ms.printing.bookprint.repositories.entities.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerEntityToCustomerConverter extends AbstractConverter<CustomerEntity, Customer> {

    @Override
    public boolean canConvert(Object source, Class<?> clazz) {
        return source instanceof CustomerEntity && clazz.isAssignableFrom(Customer.class);
    }

    @Override
    public Customer convert(CustomerEntity customerEntity) {
        return transform(customerEntity, Customer.class);
    }
}
