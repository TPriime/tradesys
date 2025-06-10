package com.agbafune.tradesys.domain.repository;

import com.agbafune.tradesys.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long userId);
    Optional<User> findByUsername(String username);
    List<User> findAll();
    User save(User user);
}
