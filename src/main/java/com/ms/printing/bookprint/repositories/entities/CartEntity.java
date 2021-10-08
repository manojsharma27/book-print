package com.ms.printing.bookprint.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table(name = "cart", schema = "book")
@Entity(name = "cart")
public class CartEntity extends AuditEntity {

    @Id
    @Column(name = "id")
    @org.hibernate.annotations.Type(type="pg-uuid")
    private UUID id;

    @Column(name = "total", columnDefinition = "numeric")
    private double price;

    @Column(name = "checked_out", columnDefinition = "boolean")
    private boolean checkedOut;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private CustomerEntity customerEntity;

    @OneToMany(mappedBy = "cartEntity")
    private List<CartProductMappingEntity> cartProductMappingEntities;

    @OneToMany(mappedBy = "cartEntity")
    private List<OrderEntity> orderEntities;
}
