package com.agbafune.tradesys.repository;

import com.agbafune.tradesys.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {
    Optional<User> findByUsername(String username);
}
