package com.crud.simple.service;

import com.crud.simple.model.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();

    User saveUser(User user);

    void deleteUser(Long id);

    User getUser(Long id);
}
