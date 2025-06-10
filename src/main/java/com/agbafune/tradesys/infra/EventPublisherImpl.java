package com.agbafune.tradesys.infra;

import com.agbafune.tradesys.domain.events.TradeEvent;
import com.agbafune.tradesys.domain.events.UserRewardEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventPublisherImpl implements EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final Logger logger = LoggerFactory.getLogger(EventPublisherImpl.class);

    public EventPublisherImpl(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publishTradeEvent(Long userId, Long tradeId) {
        logger.info("Publishing Trade event for user ID: " + userId + ", asset ID: " + tradeId);
        applicationEventPublisher.publishEvent(new TradeEvent(userId, tradeId));
    }

    @Override
    public void publishUserRewardedEvent(Long userId, Integer awardedGems, Integer userGems) {
        logger.info("Publishing User Reward event for user ID: {}, Awarded Gems: {}, TotalGems: {}",
                userId, awardedGems, userGems);
        applicationEventPublisher.publishEvent(new UserRewardEvent(userId, awardedGems, userGems));
    }
}
