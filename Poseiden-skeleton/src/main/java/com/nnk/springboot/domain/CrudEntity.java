package com.nnk.springboot.domain;

public interface CrudEntity<E> {

    Integer getId();

    E update(E entity);
}
