package com.agbafune.tradesys.domain.service;


import com.agbafune.tradesys.domain.model.User;
import com.agbafune.tradesys.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final Long INITIAL_FUNDS = 1000L;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(String username) {
        return userRepository.save(
                new User(null, username, 0, Integer.MAX_VALUE, BigDecimal.valueOf(INITIAL_FUNDS)));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
