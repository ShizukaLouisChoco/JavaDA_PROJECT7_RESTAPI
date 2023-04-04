package com.nnk.springboot.service;

import com.nnk.springboot.domain.CrudEntity;
import com.nnk.springboot.exception.NoResourceException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class AbstractCrudService<M extends CrudEntity<M>> implements CrudService<M> {

    protected final JpaRepository<M, Integer> repository;


    public AbstractCrudService(JpaRepository<M, Integer> repository){
        this.repository = repository;
    }

    @Override
    public M create(M entity){
        return this.repository.save(entity);
    }


    @Override
    public M getById(Integer id){
        return this.repository.findById(id)
                .orElseThrow(()-> new NoResourceException(id));
    }


    @Override
    public List<M> getAll(){
        return this.repository.findAll();
    }


    @Override
    public void update(M entity){
        M updatedEntity =  getById(entity.getId())
                .update(entity);
        this.repository.save(updatedEntity );

    }

    @Override
    public void delete(Integer id){
        this.repository.deleteById(id);
    }

}
