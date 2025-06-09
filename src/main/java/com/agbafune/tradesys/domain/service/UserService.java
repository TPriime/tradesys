package com.agbafune.tradesys.domain.service;

import com.agbafune.tradesys.domain.model.User;

import java.util.List;

public interface UserService {

    User create(String username);

    List<User> getAllUsers();
}
