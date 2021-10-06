package com.ms.printing.bookprint.repositories.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

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
@Table(name = "product", schema = "book")
@Entity(name = "product")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class ProductEntity extends AuditEntity {

    @Id
    @Column(name = "id")
    @org.hibernate.annotations.Type(type="pg-uuid")
    private UUID id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "price", columnDefinition = "numeric")
    private double price;

    @JsonIgnore
    @Column(name = "details_json", nullable = false, columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private String detailsJson;

    @OneToOne
    @JoinColumn(name = "product_info_id", referencedColumnName = "id", nullable = false)
    private ProductInfoEntity productInfoEntity;

}
