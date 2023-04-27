package com.nnk.springboot.service.Impl;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.ResourceAlreadyExistException;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.AbstractCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service("UserCrudServiceImpl")
public class UserCrudServiceImpl extends AbstractCrudService<User,UserRepository> {


    public UserCrudServiceImpl(UserRepository repository){
        super(repository);

    }

    @Override
    public User create(User entity) throws ResourceAlreadyExistException{
        Optional<User> userExists = repository.findByUsername(entity.getUsername());
        if(userExists.isPresent()){
            throw new ResourceAlreadyExistException(entity.getUsername());
        }
        User newUser = new User(entity);
        return super.create(newUser);
    }
 @Override
    public void update(User entity) throws ResourceAlreadyExistException{
        Optional<User> userExists = repository.findByUsername(entity.getUsername());
        if(userExists.isPresent() && userExists.get().getId()!= entity.getId()){
            throw new ResourceAlreadyExistException(entity.getUsername());
        }
        User updatedEntity =  getById(entity.getId())
                .update(entity);
        this.repository.save(updatedEntity );
    }


}
