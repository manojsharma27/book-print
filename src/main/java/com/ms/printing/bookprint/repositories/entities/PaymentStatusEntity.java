package com.ms.printing.bookprint.repositories.entities;

import com.ms.printing.bookprint.enums.PaymentStatus;
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
@Table(name = "payment_status", schema = "book")
@Entity(name = "payment_status")
public class PaymentStatusEntity {

    @Id
    //@GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "status", length = 50)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "status_code", length = 20)
    private String statusCode;

}
