package com.agbafune.tradesys.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
public class AppEventListenerImpl implements AppEventListener {

    private final Logger logger = LoggerFactory.getLogger(AppEventListenerImpl.class);

    private final List<Consumer<TradeEvent>> tradeEventConsumers = new ArrayList<>();
    private final List<Consumer<UserRewardedEvent>> rewardEventConsumers = new ArrayList<>();

    @EventListener
    public void handleTradeEvent(TradeEvent event) {
        logger.info("Received trade event: userID: {}, assetID: {}",
                event.userId(), event.assetId());
        tradeEventConsumers.forEach(c -> c.accept(event));
    }

    @EventListener
    public void handleUserRewardedEvent(UserRewardedEvent event) {
        logger.info("Received user reward event: userID: {}, awardedGems: {}, totalUserGems: {}",
                event.userId(), event.awardedGems(), event.userGems());
        rewardEventConsumers.forEach(c -> c.accept(event));
    }

    @Override
    public void onTradeEvent(Consumer<TradeEvent> handler) {
        tradeEventConsumers.add(handler);
    }

    @Override
    public void onUserRewardedEvent(Consumer<UserRewardedEvent> handler) {
        rewardEventConsumers.add(handler);
    }
}
