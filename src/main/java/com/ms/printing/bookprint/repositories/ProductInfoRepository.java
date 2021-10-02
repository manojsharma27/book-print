package com.ms.printing.bookprint.repositories;


import com.ms.printing.bookprint.repositories.entities.ProductInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfoEntity, String> {
    long count();
}
