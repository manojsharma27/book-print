package com.ms.printing.bookprint.repositories;


import com.ms.printing.bookprint.repositories.entities.CartProductMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartProductMappingRepository extends JpaRepository<CartProductMappingEntity, UUID> {

    CartProductMappingEntity findFirstByCartEntityIdAndProductEntityId(UUID cartEntityId, UUID productEntityId);

    void deleteByCartEntityIdAndProductEntityId(UUID cartEntityId, UUID productEntityId);

    void deleteAllByCartEntityId(UUID cartId);
}