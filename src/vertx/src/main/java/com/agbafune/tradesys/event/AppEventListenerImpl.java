package com.agbafune.tradesys.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AppEventListenerImpl implements AppEventListener {

    private final Logger logger = LoggerFactory.getLogger(AppEventListenerImpl.class);

    private final List<Consumer<TradeEvent>> tradeEventConsumers = new ArrayList<>();
    private final List<Consumer<UserRewardedEvent>> rewardEventConsumers = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();

    public AppEventListenerImpl(EventBus eventBus)  {
        eventBus.consumer(Event.Trade.toString(), msg -> {
            try {
                TradeEvent event = mapper.readValue(msg.body().toString(), TradeEvent.class);
                logger.info("Received trade event: userID: {}, assetID: {}",
                        event.userId(), event.assetId());

                tradeEventConsumers.forEach( c -> c.accept(event));
            } catch (JsonProcessingException e) {
                logger.error("Failed to process event: {}", msg.body());
            }
        });

        eventBus.consumer(Event.UserRewarded.toString(), msg -> {
            try {
                UserRewardedEvent event = mapper.readValue(msg.body().toString(), UserRewardedEvent.class);
                logger.info("Received user reward event: userID: {}, awardedGems: {}, totalUserGems: {}",
                        event.userId(), event.awardedGems(), event.userGems());

                rewardEventConsumers.forEach(c -> c.accept(event));
            } catch (JsonProcessingException e) {
                logger.error("Failed to process UserRewarded event: {}", msg.body());
            }
        });
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
