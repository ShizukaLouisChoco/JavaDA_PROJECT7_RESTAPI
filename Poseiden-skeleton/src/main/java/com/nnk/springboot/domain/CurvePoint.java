package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
@Table(name = "curvepoint")
public class CurvePoint implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    private Integer curveId;

    @Column(name = "as_of_date", nullable = false, updatable = false)
    private Timestamp asOfDate;
    private Double term;
    private Double value;

    @Column(name = "creation_date", nullable = false, updatable = false)
    private Timestamp creationDate;
}
