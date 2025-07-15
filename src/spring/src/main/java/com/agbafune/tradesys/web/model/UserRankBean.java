package com.agbafune.tradesys.web.model;

import com.agbafune.tradesys.model.User;

public record UserRankBean(
        Long userId,
        String username,
        Integer gemCount,
        Integer rank
) {
    public UserRankBean(User user, Integer rank) {
        this(user.id(), user.username(), user.gemCount(), rank);
    }
}
