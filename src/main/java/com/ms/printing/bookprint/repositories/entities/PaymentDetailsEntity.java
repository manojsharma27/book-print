package com.ms.printing.bookprint.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_details", schema = "book")
@Entity(name = "payment_details")
public class PaymentDetailsEntity extends AuditEntity {

    @Id
    @Column(name = "id")
    @org.hibernate.annotations.Type(type = "pg-uuid")
    private UUID id;

    @Column(name = "transaction_id", length = 50)
    private String transaction_id;

    @Column(name = "amount", columnDefinition = "numeric")
    private double amount;

    @Column(name = "payment_method", length = 50)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "payment_status")
    //@Type( type = "pgsql_enum" )
    private PaymentStatus paymentStatus;

}
