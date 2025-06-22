package com.agbafune.tradesys.exception;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException() {
        super("Insufficient funds to trade asset.");
    }

    public InsufficientFundsException(String message) {
        super(message);
    }
}
