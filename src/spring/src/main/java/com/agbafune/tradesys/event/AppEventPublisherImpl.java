package com.agbafune.tradesys.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class AppEventPublisherImpl implements AppEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final Logger logger = LoggerFactory.getLogger(AppEventPublisherImpl.class);

    public AppEventPublisherImpl(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publishTradeEvent(Long userId, Long tradeId) {
        logger.info("Publishing Trade event: userID: {}, assetID: {}", userId, tradeId);
        applicationEventPublisher.publishEvent(new TradeEvent(userId, tradeId));
    }

    @Override
    public void publishUserRewardedEvent(Long userId, Integer awardedGems, Integer userGems) {
        logger.info("Publishing User Reward event: userID: {}, awardedGems: {}, totalUserGems: {}",
                userId, awardedGems, userGems);
        applicationEventPublisher.publishEvent(new UserRewardedEvent(userId, awardedGems, userGems));
    }
}
