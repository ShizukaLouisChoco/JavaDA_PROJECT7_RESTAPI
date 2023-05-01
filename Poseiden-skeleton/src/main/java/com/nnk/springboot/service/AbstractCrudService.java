package com.nnk.springboot.service;

import com.nnk.springboot.domain.CrudEntity;
import com.nnk.springboot.exception.NoResourceException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class AbstractCrudService<M extends CrudEntity<M>, R extends JpaRepository<M, Integer>> implements CrudService<M> {

    protected final R repository;


    public AbstractCrudService(R repository){
        this.repository = repository;
    }


    @Transactional
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


    @Transactional
    @Override
    public void update(M entity){
        M updatedEntity =  getById(entity.getId())
                .update(entity);
        this.repository.save(updatedEntity );

    }

    @Transactional
    @Override
    public void delete(Integer id){
        this.repository.deleteById(id);
    }

}
