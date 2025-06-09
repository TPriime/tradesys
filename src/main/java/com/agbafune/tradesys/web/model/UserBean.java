package com.agbafune.tradesys.web.model;

import com.agbafune.tradesys.domain.model.User;

public record UserBean(
        Long id,
        String username,
        Integer gemCount,
        Integer rank
) {
    public UserBean(User user) {
        this(user.id(), user.username(), user.gemCount(), user.rank());
    }
}

