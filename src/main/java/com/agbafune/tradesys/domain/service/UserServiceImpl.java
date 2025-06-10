package com.agbafune.tradesys.domain.service;


import com.agbafune.tradesys.domain.exceptions.ConflictException;
import com.agbafune.tradesys.domain.exceptions.InsufficientFundsException;
import com.agbafune.tradesys.domain.exceptions.UserNotFoundException;
import com.agbafune.tradesys.domain.model.User;
import com.agbafune.tradesys.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final Long INITIAL_FUNDS = 1000L;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(String username) {
        if(userRepository.findByUsername(username).isPresent()) {
            throw new ConflictException("Username already exists: " + username);
        }
        return userRepository.save(
                new User(null, username, 0, BigDecimal.valueOf(INITIAL_FUNDS)));
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void decreaseUserFunds(Long userId, BigDecimal amount) {
        User user = getUserById(userId);
        BigDecimal newFunds = user.funds().subtract(amount);
        if (newFunds.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundsException("Insufficient funds for user ID: " + userId);
        }
        userRepository.save(new User.Builder(user)
                .funds(newFunds)
                .build());
    }

    @Override
    public void increaseUserFunds(Long userId, BigDecimal amount) {
        User user = getUserById(userId);
        BigDecimal newFunds = user.funds().add(amount);
        userRepository.save(new User.Builder(user)
                .funds(newFunds)
                .build());
    }

    @Override
    public User increaseUserGems(Long userId, Integer amount) {
        User user = getUserById(userId);
        Integer newGemCount = user.gemCount() + amount;
        return userRepository.save(new User.Builder(user)
                .gemCount(newGemCount)
                .build());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
