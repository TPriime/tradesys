package com.agbafune.tradesys.api;

import com.agbafune.tradesys.model.User;

public interface UserProfileCreator {
    User create(String username);
}
