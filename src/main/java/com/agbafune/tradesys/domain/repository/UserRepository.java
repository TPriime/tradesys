package com.agbafune.tradesys.domain.repository;

import com.agbafune.tradesys.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    User save(User user);
    List<User> findAll();
}
