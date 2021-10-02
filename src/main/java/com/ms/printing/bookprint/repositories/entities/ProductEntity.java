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
