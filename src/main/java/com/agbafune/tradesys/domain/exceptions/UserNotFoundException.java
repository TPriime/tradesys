package com.agbafune.tradesys.domain.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long userId) {
        super("User with ID " + userId + " not found.");
    }

}
