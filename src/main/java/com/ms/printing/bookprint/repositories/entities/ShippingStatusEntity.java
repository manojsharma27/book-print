package com.ms.printing.bookprint.repositories.entities;

import com.ms.printing.bookprint.enums.ShippingStatus;
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
@Table(name = "shipping_status", schema = "book")
@Entity(name = "shipping_status")
public class ShippingStatusEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "status", length = 50)
    @Enumerated(EnumType.STRING)
    private ShippingStatus status;

    @Column(name = "shipping_code", length = 20)
    private String shippingCode;

}
