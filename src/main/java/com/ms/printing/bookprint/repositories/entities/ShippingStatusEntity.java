package com.ms.printing.bookprint.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shipping_status", schema = "book")
@Entity(name = "shipping_status")
public class ShippingStatusEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "shipping_code", length = 20)
    private String shippingCode;

}
