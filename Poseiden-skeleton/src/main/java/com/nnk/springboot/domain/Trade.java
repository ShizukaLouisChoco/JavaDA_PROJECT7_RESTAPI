package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


@CrudEntity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
@Table(name = "trade")
public class Trade  implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String account;
    private String type;
    private Double buyQuantity;
    private Double sellQuantity;
    private Double buyPrice;
    private Double sellPrice;
    private String benchmark;

    @Column(name = "trade_date", nullable = false, updatable = false)
    private Timestamp tradeDate;
    private String security;
    private String status;
    private String trader;
    private String book;
    private String creationName;

    @Column(name = "creation_date", nullable = false, updatable = false)
    private Timestamp creationDate;
    private String revisionName;

    @Column(name = "revision_date", nullable = false, updatable = false)
    private Timestamp revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;
}
