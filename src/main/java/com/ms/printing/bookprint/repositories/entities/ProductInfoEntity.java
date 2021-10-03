package com.ms.printing.bookprint.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_info", schema = "book")
@Entity(name = "product_info")
public class ProductInfoEntity extends AuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "base_price", columnDefinition = "numeric")
    private double basePrice;

    @OneToOne
    @JoinColumn(name = "product_type_id", referencedColumnName = "id")
    private ProductTypeEntity productTypeEntity;

}
