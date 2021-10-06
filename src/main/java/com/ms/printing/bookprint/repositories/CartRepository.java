package com.ms.printing.bookprint.repositories;

import com.ms.printing.bookprint.repositories.entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, UUID> {

    CartEntity findFirstByCustomerEntityIdAndCheckedOutFalseOrderByModifiedOnDesc(UUID customerId);

}
