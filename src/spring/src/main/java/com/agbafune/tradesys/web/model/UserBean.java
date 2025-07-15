package com.agbafune.tradesys.web.model;

import com.agbafune.tradesys.model.User;

import java.math.BigDecimal;

public record UserBean(
        Long id,
        String username,
        Integer gemCount,
        BigDecimal funds
) {
    public UserBean(User user) {
        this(user.id(), user.username(), user.gemCount(), user.funds());
    }
}

