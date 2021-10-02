package com.ms.printing.bookprint.repositories.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order", schema = "book")
@Entity(name = "order")
public class OrderEntity extends AuditEntity {

    @Id
    @Column(name = "id")
    @org.hibernate.annotations.Type(type="pg-uuid")
    private UUID id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "amount", columnDefinition = "numeric")
    private double amount;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private CartEntity cartEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductEntity productEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_details_id", referencedColumnName = "id")
    private PaymentDetailsEntity paymentDetailsEntity;

    @OneToOne
    @JoinColumn(name = "order_status_id", referencedColumnName = "id")
    private OrderStatusEntity orderStatusEntity;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipment_id", referencedColumnName = "id")
    private List<ShipmentEntity> shipmentEntities;

}
