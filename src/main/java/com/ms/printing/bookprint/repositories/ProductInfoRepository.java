package com.ms.printing.bookprint.repositories;


import com.ms.printing.bookprint.repositories.entities.ProductInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfoEntity, UUID> {

}
