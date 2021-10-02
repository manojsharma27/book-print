package com.ms.printing.bookprint.repositories.entities;

import com.ms.printing.bookprint.repositories.converters.JsonConverter;
import lombok.*;

import javax.json.JsonObject;
import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToOne
    @JoinColumn(name = "shipping_status_id", referencedColumnName = "id")
    private ShippingStatusEntity shippingStatusEntity;

}