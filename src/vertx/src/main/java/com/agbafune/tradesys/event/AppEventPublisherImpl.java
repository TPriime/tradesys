package com.agbafune.tradesys.event;

import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppEventPublisherImpl implements AppEventPublisher {
    private final Logger logger = LoggerFactory.getLogger(AppEventPublisherImpl.class);
    private final EventBus eb;

    public AppEventPublisherImpl(EventBus eventBus) {
        eb = eventBus;
    }

    @Override
    public void publishTradeEvent(Long userId, Long tradeId) {
        logger.info("Publishing Trade event: userID: {}, assetID: {}", userId, tradeId);
        TradeEvent event = new TradeEvent(userId, tradeId);
        eb.publish(Event.Trade.toString(), JsonObject.mapFrom(event));
    }

    @Override
    public void publishUserRewardedEvent(Long userId, Integer awardedGems, Integer userGems) {
        logger.info("Publishing User Reward event: userID: {}, awardedGems: {}, totalUserGems: {}",
                userId, awardedGems, userGems);
        UserRewardedEvent event = new UserRewardedEvent(userId, awardedGems, userGems);
        eb.publish(Event.UserRewarded.toString(), JsonObject.mapFrom(event));
    }
}
