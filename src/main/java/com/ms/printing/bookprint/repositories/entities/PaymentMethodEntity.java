package com.ms.printing.bookprint.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_method", schema = "book")
@Entity(name = "payment_method")
public class PaymentMethodEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name", length = 50)
    private String name;

}
