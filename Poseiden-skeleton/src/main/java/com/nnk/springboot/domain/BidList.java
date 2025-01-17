package com.nnk.springboot.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@Validated
@Table(name = "bidlist")
public class BidList implements Serializable, CrudEntity<BidList> {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Account is mandatory")
    private String account;

    @NotBlank(message = "Type is mandatory")
    private String type;

    @Column(nullable = false)
    @NotNull(message = "bidQuantity is mandatory")
    private Double bidQuantity;

    private Double askQuantity;
    private Double bid;
    private Double ask;
    private String benchmark;

    @Column(name = "bidlist_date",updatable = false)
    private Timestamp bidListDate;
    private String commentary;
    private String security;
    private String status;
    private String trader;
    private String book;
    private String creationName;
    @Column(name = "creation_date", updatable = false)
    private Timestamp creationDate;
    private String revisionName;
    @Column(name = "revision_date", updatable = false)
    private Timestamp revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;

    public BidList (){

    }


    public BidList(BidList entity){
        this.account = entity.getAccount();
        this.type = entity.getType();
        this.bidQuantity = entity.getBidQuantity();
        this.revisionDate = entity.getRevisionDate();
    }


    public BidList (String account, String type, Double bidQuantity){
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;

    }


    public BidList update(BidList entity) {
        this.account = entity.getAccount();
        this.type = entity.getType();
        this.bidQuantity = entity.getBidQuantity();
        this.revisionDate = entity.getRevisionDate();
        return this;
    }


}
