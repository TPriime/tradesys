package com.agbafune.tradesys.infra;

public interface EventPublisher {
    void publishTradeEvent(Long userId, Long tradeId);
    void publishUserRewardedEvent(Long userId, Integer awardedGems, Integer userGems);
}
