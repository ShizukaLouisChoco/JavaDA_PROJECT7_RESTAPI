package com.nnk.springboot.domain;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Validated
@Table(name = "rating")
public class Rating implements Serializable, CrudEntity<Rating>  {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String moodysRating;
    private String sandPRating;
    private String fitchRating;
    private Integer orderNumber;

    public Rating(Rating entity) {
       this.moodysRating = entity.getMoodysRating();
       this.sandPRating = entity.getSandPRating();
       this.fitchRating = entity.getFitchRating();
       this.orderNumber = entity.getOrderNumber();
    }

    public Rating(Integer id, String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.id= id;
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
