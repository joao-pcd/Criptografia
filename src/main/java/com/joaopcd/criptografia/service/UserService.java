package com.joaopcd.criptografia.service;

import com.joaopcd.criptografia.domain.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    void create(User user);

    void update(Long id, User user);

    void delete(Long id);
}
