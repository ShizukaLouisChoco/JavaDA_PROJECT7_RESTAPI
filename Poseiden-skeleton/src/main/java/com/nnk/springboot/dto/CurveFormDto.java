package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurveFormDto implements Serializable {

    @NotNull(message = "must not be null")
    private Integer curveId;
    private Double term;
    private Double value;

}
