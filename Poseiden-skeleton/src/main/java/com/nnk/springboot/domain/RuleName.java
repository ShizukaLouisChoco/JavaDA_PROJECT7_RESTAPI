package com.nnk.springboot.domain;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "name is mandatory")
    private String name;
    @NotBlank(message = "description is mandatory")
    private String description;
    @NotBlank(message = "json is mandatory")
    private String json;
    @NotBlank(message = "template is mandatory")
    private String template;
    @NotBlank(message = "sqlStr is mandatory")
    private String sqlStr;
    @NotBlank(message = "sqlPart is mandatory")
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

    public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;

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
