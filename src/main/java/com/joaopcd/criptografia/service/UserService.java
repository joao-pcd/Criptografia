package com.joaopcd.criptografia.service;

import com.joaopcd.criptografia.domain.model.User;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User create(User user) throws NoSuchAlgorithmException;

    User update(Long id, User user) throws NoSuchAlgorithmException;

    void delete(Long id);
}
