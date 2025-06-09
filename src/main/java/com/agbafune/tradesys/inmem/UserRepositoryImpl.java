package com.agbafune.tradesys.inmem;

import com.agbafune.tradesys.domain.model.User;
import com.agbafune.tradesys.domain.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class UserRepositoryImpl implements UserRepository {

    private final Map<Long, User> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    @Override
    public Optional<User> findByUsername(String username) {
        // Implementation for finding a user by username
        return Optional.empty(); // Placeholder
    }

    public Optional<User> findById(Long id) {
        if (store.containsKey(id)) {
            return Optional.of(store.get(id));
        }
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        User newUser = user;

        if (newUser.id() == null) {
            newUser = new User.Builder(user)
                    .id(idGen.getAndIncrement())
                    .build();
        }

        store.put(newUser.id(), newUser);
        return newUser;
    }

    @Override
    public List<User> findAll() {
        return store.values().stream().toList();
    }
}
