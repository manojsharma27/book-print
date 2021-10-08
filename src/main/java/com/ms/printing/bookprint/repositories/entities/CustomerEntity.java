package com.ms.printing.bookprint.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer", schema = "book")
@Entity(name = "customer")
public class CustomerEntity extends AuditEntity {

    @Id
    @Column(name = "id")
    @org.hibernate.annotations.Type(type="pg-uuid")
    private UUID id;

    @Column(name = "firstname", length = 50)
    private String firstname;

    @Column(name = "lastname", length = 50)
    private String lastname;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "pincode")
    private int pincode;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

}
