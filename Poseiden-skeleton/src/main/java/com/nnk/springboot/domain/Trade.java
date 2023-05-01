package com.nnk.springboot.domain;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;


@Entity
@Data
@Validated
@Table(name = "trade")
public class Trade  implements Serializable, CrudEntity<Trade>  {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @NotBlank(message = "account is mandatory")
    private String account;
    @NotBlank(message = "type is mandatory")
    private String type;
    @NotNull(message = "buyQuantity is mandatory")
    private Double buyQuantity;
    private Double sellQuantity;
    private Double buyPrice;
    private Double sellPrice;
    private String benchmark;

    private Timestamp tradeDate;
    private String security;
    private String status;
    private String trader;
    private String book;
    private String creationName;

    private Timestamp creationDate;
    private String revisionName;

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


    public Trade(String account, String type, double buyQuantity) {
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
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
