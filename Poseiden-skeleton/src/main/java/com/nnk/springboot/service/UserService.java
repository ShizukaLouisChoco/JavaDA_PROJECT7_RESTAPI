package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    void createUser(User user);

    Optional<User> findById(Integer id);

    void save(User user);

    void delete(Integer id);

    User update(User user);
}
