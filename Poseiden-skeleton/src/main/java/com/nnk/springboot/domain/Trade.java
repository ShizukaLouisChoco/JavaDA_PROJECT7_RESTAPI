package com.nnk.springboot.domain;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


@Entity
@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Validated
@Table(name = "trade")
public class Trade  implements Serializable, CrudEntity<Trade>  {
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

    //@Column(name = "trade_date", nullable = false, updatable = false)
    private Timestamp tradeDate;
    private String security;
    private String status;
    private String trader;
    private String book;
    private String creationName;

    //@Column(name = "creation_date", nullable = false, updatable = false)
    private Timestamp creationDate;
    private String revisionName;

    //@Column(name = "revision_date", nullable = false, updatable = false)
    private Timestamp revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;

    public Trade(Trade entity) {
        this.account = entity.getAccount();
        this.type = entity.getType();
        this.buyQuantity = entity.getBuyQuantity();
        this.sellQuantity = entity.getSellQuantity();
        this.buyPrice = entity.getBuyPrice();
        this.sellPrice = entity.getSellPrice();
        this.benchmark = entity.getBenchmark();
        this.tradeDate = entity.getTradeDate();
        this.security = entity.getSecurity();
        this.status = entity.getStatus();
        this.trader = entity.getTrader();
        this.book = entity.getBook();
        this.creationName = entity.getCreationName();
        this.creationDate = entity.getCreationDate();
        this.revisionName = entity.getRevisionName();
        this.revisionDate = entity.getRevisionDate();
        this.dealName = entity.getDealName();
        this.dealType = entity.getDealType();
        this.sourceListId = entity.getSourceListId();
        this.side = entity.getSide();
    }

    public Trade() {

    }

    @Override
    public Trade update(Trade entity) {
        this.account = entity.getAccount();
        this.type = entity.getType();
        this.buyQuantity = entity.getBuyQuantity();
        this.sellQuantity = entity.getSellQuantity();
        this.buyPrice = entity.getBuyPrice();
        this.sellPrice = entity.getSellPrice();
        this.benchmark = entity.getBenchmark();
        this.tradeDate = entity.getTradeDate();
        this.security = entity.getSecurity();
        this.status = entity.getStatus();
        this.trader = entity.getTrader();
        this.book = entity.getBook();
        this.creationName = entity.getCreationName();
        this.creationDate = entity.getCreationDate();
        this.revisionName = entity.getRevisionName();
        this.revisionDate = entity.getRevisionDate();
        this.dealName = entity.getDealName();
        this.dealType = entity.getDealType();
        this.sourceListId = entity.getSourceListId();
        this.side = entity.getSide();
        return this;
    }
}
