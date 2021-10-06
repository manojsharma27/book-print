package com.ms.printing.bookprint.converters;

import com.ms.printing.bookprint.models.Customer;
import com.ms.printing.bookprint.repositories.entities.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerToCustomerEntityConverter extends AbstractConverter<Customer, CustomerEntity> {

    @Override
    public boolean canConvert(Object source, Class<?> clazz) {
        return source instanceof Customer && clazz.isAssignableFrom(CustomerEntity.class);
    }

    @Override
    public CustomerEntity convert(Customer customer) {
        return transform(customer, CustomerEntity.class);
    }
}
