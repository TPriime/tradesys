package com.agbafune.tradesys.domain.exceptions;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException() {
        super("Insufficient funds to trade asset.");
    }

    public InsufficientFundsException(String message) {
        super(message);
    }
}
