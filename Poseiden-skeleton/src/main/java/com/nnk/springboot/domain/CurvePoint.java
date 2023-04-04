package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


@CrudEntity
@Data
//@NoArgsConstructor
@AllArgsConstructor
@Validated
@Table(name = "curvepoint")
public class CurvePoint implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    private Integer curveId;

    @Column(name = "as_of_date")
    private Timestamp asOfDate;
    private Double term;
    private Double value;

    @Column(name = "creation_date", nullable = false, updatable = false)
    private Timestamp creationDate;

    //for create CurvePoint
    public CurvePoint(Integer curveId, Double term, Double value, Timestamp creationDate) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
        this.asOfDate = creationDate;
        this.creationDate = creationDate;
    }

    //for update bid
    public CurvePoint ( Timestamp asOfDate,Integer curveId, Double term, Double value){
        this.curveId = curveId;
        this.term = term;
        this.value = value;
        this.asOfDate = asOfDate;

    }

    public CurvePoint() {

    }
    //public CurvePoint(@NotBlank(message = "Account is mandatory") String account, @NotBlank(message = "Type is mandatory") String type, Double bidQuantity) {

    //}
}
