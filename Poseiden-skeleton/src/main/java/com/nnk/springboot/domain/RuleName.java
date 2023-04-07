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
@Table(name = "rulename")
public class RuleName implements Serializable , CrudEntity<RuleName> {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String name;
    private String description;
    private String json;
    private String template;
    private String sqlStr;
    private String sqlPart;

    public RuleName(RuleName entity) {
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.json = entity.getJson();
        this.template = entity.getTemplate();
        this.sqlStr = entity.getSqlStr();
        this.sqlPart = entity.getSqlPart();
    }

    public RuleName() {

    }

    @Override
    public RuleName update(RuleName entity) {
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.json = entity.getJson();
        this.template = entity.getTemplate();
        this.sqlStr = entity.getSqlStr();
        this.sqlPart = entity.getSqlPart();
        return this;
    }
}
