package com.ms.printing.bookprint.repositories.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name = "name", length = 50)
    private String name;

}
