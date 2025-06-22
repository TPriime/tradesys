package com.agbafune.tradesys.repository;

import com.agbafune.tradesys.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRepositoryImpl extends BaseInmemRepository<User> implements UserRepository {

    @Override
    public Optional<User> findByUsername(String username) {
        return getValuesStream()
                .filter(user -> user.username().equals(username))
                .findFirst();
    }

    @Override
    public Long getId(User user) {
        return user.id();
    }

    @Override
    public User setId(User user, Long id) {
        return new User.Builder(user).id(id).build();
    }

    @Override
    public String getName() {
        return "User";
    }
}
