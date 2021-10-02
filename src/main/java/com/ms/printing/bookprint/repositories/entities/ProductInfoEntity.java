package com.ms.printing.bookprint.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_info", schema = "book")
@Entity(name = "product_info")
public class ProductInfoEntity {
    @Id
    @Column(name = "consumerid")
    private String consumerId;

    @Column(name = "emailid")
    private String email;

    @Column(name = "webid")
    private String webId;

    @Column(name = "userid")
    private String userId;

    @Column(name = "oktaid")
    private String oktaId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_logged_in")
    private Date lastLoggedIn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createtime")
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modifiedtime")
    private Date modifiedDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String modifiedby;

}
