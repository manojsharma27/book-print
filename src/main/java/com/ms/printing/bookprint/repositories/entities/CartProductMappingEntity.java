package com.ms.printing.bookprint.repositories.entities;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_product_mapping", schema = "book",
        uniqueConstraints = {@UniqueConstraint(columnNames={"cart_id", "product_id"})})
@Entity(name = "cart_product_mapping")
public class CartProductMappingEntity extends AuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private CartEntity cartEntity;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductEntity productEntity;

    @Column(name = "quantity")
    private int quantity;

}
