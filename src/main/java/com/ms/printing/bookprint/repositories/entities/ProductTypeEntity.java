package com.ms.printing.bookprint.repositories.entities;

import com.ms.printing.bookprint.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_type", schema = "book")
@Entity(name = "product_type")
public class ProductTypeEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "type", length = 50)
    @Enumerated(EnumType.STRING)
    private ProductType type;

}
