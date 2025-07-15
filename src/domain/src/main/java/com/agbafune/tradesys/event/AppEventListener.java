package com.agbafune.tradesys.event;

import java.util.function.Consumer;

public interface AppEventListener {

    public void onTradeEvent(Consumer<TradeEvent> handler);

    public void onUserRewardedEvent(Consumer<UserRewardedEvent> handler);
}
