package com.agbafune.tradesys.event;

public enum Event {
    Trade("event.trade"),
    UserRewarded("event.user.rewarded"),
    ;

    private final String name;

    Event(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
