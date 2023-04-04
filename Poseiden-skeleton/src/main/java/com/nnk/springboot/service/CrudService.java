package com.nnk.springboot.service;

import java.util.List;


public interface CrudService<M> {

    M create(M entity);

    M getById(Integer id);

    List<M> getAll();

    void update(M entity);

    void delete(Integer id);

}
