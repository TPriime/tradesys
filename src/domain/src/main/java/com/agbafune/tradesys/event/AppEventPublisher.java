package com.agbafune.tradesys.event;

import org.springframework.stereotype.Component;

@Component
public interface AppEventPublisher {

    public void publishTradeEvent(Long userId, Long tradeId);

    public void publishUserRewardedEvent(Long userId, Integer awardedGems, Integer userGems);
}
