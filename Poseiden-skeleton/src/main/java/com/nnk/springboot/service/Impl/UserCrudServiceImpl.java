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
    /*
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return crudService.findAll();
    }

    @Transactional
    @Override
    public void createUser(User user) {
        //varification if username is already used or not
        Optional<User> userExists = userRepository.findByUsername(user.getUsername());
        if(userExists.isPresent()){
            throw new UsernameAlreadyExistException("This username is already used : " + user.getUsername() );
        }
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setFullname(user.getFullname());
        newUser.setRole(user.getRole());
        userRepository.save(newUser);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Transactional
    @Override
    public void save(User user) {
        userRepository.save(user);
    }


}
