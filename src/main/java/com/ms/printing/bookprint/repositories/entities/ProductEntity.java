package com.ms.printing.bookprint.repositories.entities;

import com.ms.printing.bookprint.repositories.converters.JsonConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Convert;
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
public class ProductEntity extends AuditEntity {

    @Id
    @Column(name = "id")
    @org.hibernate.annotations.Type(type="pg-uuid")
    private UUID id;

    @Column(name = "price", columnDefinition = "numeric")
    private double price;

    @Column(name = "details_json", nullable = false, columnDefinition = "jsonb")
    @Convert(converter = JsonConverter.class)
    private JsonObject detailsJson;

    @OneToOne
    @JoinColumn(name = "product_info_id", referencedColumnName = "id")
    private ProductInfoEntity productInfoEntity;

}
