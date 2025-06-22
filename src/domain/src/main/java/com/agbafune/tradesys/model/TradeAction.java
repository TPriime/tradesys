package com.agbafune.tradesys.model;

public enum TradeAction {
    BUY("Buy"),
    SELL("Sell");

    private final String action;

    TradeAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
