package com.ms.printing.bookprint.repositories.entities;

import com.ms.printing.bookprint.repositories.converters.JsonConverter;
import lombok.*;

import javax.json.JsonObject;
import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart", schema = "book")
@Entity(name = "cart")
public class CartEntity extends AuditEntity {

    @Id
    @Column(name = "id")
    @org.hibernate.annotations.Type(type="pg-uuid")
    private UUID id;

    @Column(name = "total", columnDefinition = "numeric")
    private double price;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private CustomerEntity customerEntity;

    @OneToMany(mappedBy = "cartEntity")
    private List<CartProductMappingEntity> cartProductMappingEntities;

    @OneToMany(mappedBy = "cartEntity")
    private List<OrderEntity> orderEntities;
}
