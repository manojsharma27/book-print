package com.ms.printing.bookprint.repositories.entities;

import com.ms.printing.bookprint.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table(name = "product_info", schema = "book")
@Entity(name = "product_info")
public class ProductInfoEntity extends AuditEntity {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "image_url", length = 1000)
    private String imageUrl;

    @Column(name = "base_price", columnDefinition = "numeric")
    private double basePrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", length = 50)
    private ProductType productType;

}
