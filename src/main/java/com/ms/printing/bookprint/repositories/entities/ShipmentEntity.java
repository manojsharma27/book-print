package com.ms.printing.bookprint.repositories.entities;

import com.ms.printing.bookprint.enums.ShippingStatus;
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
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table(name = "shipment", schema = "book")
@Entity(name = "shipment")
public class ShipmentEntity extends AuditEntity {

    @Id
    @Column(name = "id")
    @org.hibernate.annotations.Type(type="pg-uuid")
    private UUID id;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "pincode", columnDefinition = "integer")
    private int pincode;

    @Column(name = "shipping_status", length = 50)
    @Enumerated(EnumType.STRING)
    private ShippingStatus shippingStatus;

}