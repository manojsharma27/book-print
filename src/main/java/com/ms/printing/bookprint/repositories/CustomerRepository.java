package com.ms.printing.bookprint.repositories;

import com.ms.printing.bookprint.repositories.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {

    CustomerEntity findByEmail(String email);
}
