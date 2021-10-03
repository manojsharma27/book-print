package com.ms.printing.bookprint.repositories.entities;

import com.ms.printing.bookprint.enums.OrderStatus;
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
@Table(name = "order_status", schema = "book")
@Entity(name = "order_status")
public class OrderStatusEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "status", length = 50)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

}
