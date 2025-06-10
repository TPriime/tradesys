package com.agbafune.tradesys.domain.service;

import com.agbafune.tradesys.domain.model.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserService {

    User create(String username);

    User getUserById(Long userId);

    Optional<User> findUserByUsername(String username);

    void decreaseUserFunds(Long userId, BigDecimal amount);

    void increaseUserFunds(Long userId, BigDecimal amount);

    User increaseUserGems(Long userId, Integer amount);

    List<User> getAllUsers();
}
