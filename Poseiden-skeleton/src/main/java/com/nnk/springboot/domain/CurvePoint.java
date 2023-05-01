package com.nnk.springboot.domain;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;


@Entity
@Data
@Validated
@Table(name = "curvepoint")
public class CurvePoint implements Serializable, CrudEntity<CurvePoint>  {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;


    @NotNull(message = "CurveId is mandatory")
    @Column(nullable = false)
    private Integer curveId;

    @Column(name = "as_of_date")
    private Timestamp asOfDate;

    @NotNull(message = "term is mandatory")
    private Double term;

    @NotNull(message = "value is mandatory")
    private Double value;

    @Column(name = "creation_date", updatable = false)
    private Timestamp creationDate;



    //for update bid
    public CurvePoint ( Integer curveId, Double term, Double value){
        this.curveId = curveId;
        this.term = term;
        this.value = value;

    }

    public CurvePoint() {

    }

    public CurvePoint(CurvePoint entity) {
        this.curveId = entity.getCurveId();
        this.term = entity.getTerm();
        this.value = entity.getValue();
        this.asOfDate = entity.getAsOfDate();
    }

    @Override
    public CurvePoint update(CurvePoint entity) {
        this.curveId = entity.getCurveId();
        this.term = entity.getTerm();
        this.value = entity.getValue();
        this.asOfDate = entity.getAsOfDate();
        return this;
    }

}
