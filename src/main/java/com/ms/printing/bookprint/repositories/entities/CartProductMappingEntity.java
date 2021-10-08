package com.ms.printing.bookprint.repositories.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_product_mapping", schema = "book")
@Entity(name = "cart_product_mapping")
public class CartProductMappingEntity extends AuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private CartEntity cartEntity;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductEntity productEntity;

    @Column(name = "quantity")
    private int quantity;

}
