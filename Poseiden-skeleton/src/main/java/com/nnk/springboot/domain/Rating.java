package com.nnk.springboot.domain;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@Validated
@Table(name = "rating")
public class Rating implements Serializable, CrudEntity<Rating>  {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NotBlank(message="moodysRating is mandatory")
    private String moodysRating;
    @NotBlank(message="sandPRating is mandatory")
    private String sandPRating;
    @NotBlank(message="fitchRating is mandatory")
    private String fitchRating;
    @NotNull(message="orderNumber is mandatory")
    private Integer orderNumber;

    public Rating(Rating entity) {
       this.moodysRating = entity.getMoodysRating();
       this.sandPRating = entity.getSandPRating();
       this.fitchRating = entity.getFitchRating();
       this.orderNumber = entity.getOrderNumber();
    }

    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }


    public Rating() {

    }

    @Override
    public Rating update(Rating entity) {
        this.moodysRating = entity.getMoodysRating();
        this.sandPRating = entity.getSandPRating();
        this.fitchRating = entity.getFitchRating();
        this.orderNumber = entity.getOrderNumber();
        return this;
    }
}
