package com.agbafune.tradesys.inmem;

import com.agbafune.tradesys.domain.model.User;
import com.agbafune.tradesys.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

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
}
