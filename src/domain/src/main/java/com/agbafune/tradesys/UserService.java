package com.agbafune.tradesys;


import com.agbafune.tradesys.exception.ConflictException;
import com.agbafune.tradesys.exception.InsufficientFundsException;
import com.agbafune.tradesys.model.User;
import com.agbafune.tradesys.repository.UserRepository;
import com.agbafune.tradesys.api.UserProfileCreator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService implements UserProfileCreator {
    private final UserRepository userRepository;

    private final Long INITIAL_FUNDS = 1000L;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new ConflictException("Username already exists: " + username);
        }
        return userRepository.save(
                new User(null, username, 0, BigDecimal.valueOf(INITIAL_FUNDS)));
    }

    public User getUserById(Long userId) {
        return userRepository.getById(userId);
    }

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

    public void increaseUserFunds(Long userId, BigDecimal amount) {
        User user = getUserById(userId);
        BigDecimal newFunds = user.funds().add(amount);
        userRepository.save(new User.Builder(user)
                .funds(newFunds)
                .build());
    }

    public User increaseUserGems(Long userId, Integer amount) {
        User user = getUserById(userId);
        Integer newGemCount = user.gemCount() + amount;
        return userRepository.save(new User.Builder(user)
                .gemCount(newGemCount)
                .build());
    }
}
