package com.agbafune.tradesys.exception;

public class PortfolioNotFoundException extends RuntimeException {

    public PortfolioNotFoundException(Long userId) {
        super("Portfolio not found for user ID: " + userId + ". Buy an asset to create one.");
    }
}
